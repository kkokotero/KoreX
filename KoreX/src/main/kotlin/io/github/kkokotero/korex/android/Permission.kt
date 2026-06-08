package io.github.kkokotero.korex.android

import android.content.Context
import androidx.core.content.ContextCompat

data class Permission(val name: String, val rationale: String? = null)

fun interface PermissionChecker {
    fun isGranted(permission: Permission): Boolean
}

fun interface PermissionRequester {
    fun request(permission: Permission, onResult: (Boolean) -> Unit)
}

fun interface PermissionRationaleChecker {
    fun shouldShowRationale(permission: Permission): Boolean
}

class PermissionRequestScope internal constructor() {
    private val grantedCallbacks = mutableListOf<(Permission) -> Unit>()
    private val deniedCallbacks = mutableListOf<(Permission) -> Unit>()
    private val permanentlyDeniedCallbacks = mutableListOf<(Permission) -> Unit>()

    fun granted(block: (Permission) -> Unit) {
        grantedCallbacks += block
    }

    fun denied(block: (Permission) -> Unit) {
        deniedCallbacks += block
    }

    fun permanentlyDenied(block: (Permission) -> Unit) {
        permanentlyDeniedCallbacks += block
    }

    internal fun onGranted(permission: Permission) = grantedCallbacks.forEach { it(permission) }
    internal fun onDenied(permission: Permission) = deniedCallbacks.forEach { it(permission) }
    internal fun onPermanentlyDenied(permission: Permission) = permanentlyDeniedCallbacks.forEach { it(permission) }
}

class PermissionManager(
    private val checker: PermissionChecker,
    private val requester: PermissionRequester,
    private val rationaleChecker: PermissionRationaleChecker = PermissionRationaleChecker { false },
    private val permanentlyDeniedChecker: (Permission) -> Boolean = { false },
) {
    fun request(
        vararg permissions: Permission,
        onGranted: (Permission) -> Unit = {},
        onDenied: (Permission) -> Unit = {},
    ) {
        permissions.forEach { permission ->
            if (checker.isGranted(permission)) {
                onGranted(permission)
            } else {
                requester.request(permission) { granted ->
                    if (granted) onGranted(permission) else onDenied(permission)
                }
            }
        }
    }

    fun request(
        permissions: Iterable<Permission>,
        block: PermissionRequestScope.() -> Unit,
    ) {
        val scope = PermissionRequestScope().apply(block)
        permissions.forEach { permission ->
            if (checker.isGranted(permission)) {
                scope.onGranted(permission)
            } else {
                requester.request(permission) { granted ->
                    when {
                        granted -> scope.onGranted(permission)
                        permanentlyDeniedChecker(permission) || !rationaleChecker.shouldShowRationale(permission) -> scope.onPermanentlyDenied(permission)
                        else -> scope.onDenied(permission)
                    }
                }
            }
        }
    }

    fun request(
        vararg permissions: Permission,
        block: PermissionRequestScope.() -> Unit,
    ) {
        request(permissions.asList(), block)
    }
}

class PermissionsRequestApi internal constructor(
    private val manager: PermissionManager,
) {
    fun request(
        permissions: Iterable<Permission>,
        block: PermissionRequestScope.() -> Unit,
    ) {
        manager.request(permissions, block)
    }

    fun request(
        vararg permissions: Permission,
        block: PermissionRequestScope.() -> Unit,
    ) {
        manager.request(*permissions, block = block)
    }
}

fun permissions(permissionManager: PermissionManager): PermissionsRequestApi = PermissionsRequestApi(permissionManager)

fun permissionManager(
    context: Context,
    requester: PermissionRequester,
    rationaleChecker: PermissionRationaleChecker = PermissionRationaleChecker { false },
): PermissionManager =
    PermissionManager(
        checker = PermissionChecker { permission ->
            ContextCompat.checkSelfPermission(context, permission.name) == android.content.pm.PackageManager.PERMISSION_GRANTED
        },
        requester = requester,
        rationaleChecker = rationaleChecker,
    )
