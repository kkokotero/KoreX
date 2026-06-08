<p align="center">
  <img src="docs/banner.svg" alt="Banner" width="100%" />
</p>

<p align="center">
  <a href="https://github.com/kkokotero/KoreX">
    <img src="https://img.shields.io/badge/GitHub-kkokotero%2FKoreX-181717?style=for-the-badge&logo=github" alt="GitHub badge" />
  </a>
  <img src="https://img.shields.io/badge/Version-1.0.0-blue?style=for-the-badge" alt="Version badge" />
  <img src="https://img.shields.io/badge/License-MIT-2ea44f?style=for-the-badge" alt="MIT license badge" />
  <img src="https://img.shields.io/badge/Android-API%2027%2B-3ddc84?style=for-the-badge&logo=android" alt="Android badge" />
  <img src="https://img.shields.io/badge/Kotlin-JVM%2017-7f52ff?style=for-the-badge&logo=kotlin" alt="Kotlin badge" />
</p>

KoreX is an Android-first utility library focused on DX, predictable APIs, and reusable building blocks for app development.

It is split into small modules for logic, platform helpers, networking, data coordination, errors, lifecycle helpers, notifications, intents, and testing support.

The repository also includes `SampleApp`, a standalone demo that exercises the public API.

## Quick path

1. Add KoreX as a dependency.
2. Use the public helpers for results, pipelines, validation, config, networking, and platform actions.
3. Add only the Android permissions your app actually needs.
4. Use `SampleApp` as a reference implementation.

## Installation

```kotlin
repositories {
    maven("https://jitpack.io")
    maven("https://maven.pkg.github.com/kkokotero/KoreX")
}
```


```kotlin
dependencies {
    implementation("io.github.kkokotero:korex:1.0.1")
}
```

## What KoreX gives you

| Area | What it helps with |
|---|---|
| `core` | Result handling, pipelines, validation, retry, events, logging, observables |
| `base` | Reusable abstract primitives for app logic and orchestration |
| `android` | Permissions, files, storage, devices, images, security, compatibility helpers |
| `network` | Typed requests, OkHttp client, JSON serialization, retries, offline handling, WebSocket helpers |
| `data` | Local/remote coordination and sync helpers |
| `errors` | Error mapping and user-facing messages |
| `config` | Typed in-memory configuration |
| `notifications` | Local notifications and notification channels |
| `intents` | Open settings, URL, share, email, and dial actions |
| `lifecycle` | Lifecycle-aware tasks and app foreground/background observation |
| `testing` | Fakes and test utilities |

## Basic usage

### Results and pipelines

```kotlin
val result = resultOf {
    "  korex  "
}
    .map { it.trim() }
    .map { it.replaceFirstChar(Char::uppercase) }
    .fold(
        onSuccess = { value -> value },
        onFailure = { error -> error.message.orEmpty() },
    )
```

```kotlin
val output = pipeline<String>()
    .then("trim") { it.trim() }
    .then("titleCase") { it.replaceFirstChar(Char::uppercase) }
    .run("  korex  ")
```

### Validation

```kotlin
val validator = validator<String> {
    required()
    minLength(8)
    contains("KoreX")
    email("Please enter a valid email")
}

val validation = validator.validate("KoreX sample@example.com")
```

You can also validate numbers and collections with the built-in helpers:

```kotlin
val ageValidator = validator<Int> {
    positive()
    inRange(18, 120)
}

val tagsValidator = validator<List<String>> {
    notEmpty()
    sizeBetween(1, 5)
}
```

### Typed config

```kotlin
val appConfig = config(context) {
    string("api_url", "https://example.invalid/api")
    boolean("feature_pipeline", true)
    int("max_retries", 3)
}

val apiUrl: String = appConfig.get("api_url")
appConfig.put("api_url", "https://api.example.com")
```

Use `config(context) { ... }` when you want the values to survive app restarts.  
Use plain `config { ... }` when you only need an in-memory config object.

```kotlin
val runtimeConfig = config {
    string("mode", "preview")
}
```

### Network

```kotlin
@Request
data class UserRequest(val id: String)

@Response
data class UserResponse(val id: String, val name: String)

val http = httpClient(baseUrl = "https://api.example.com")

val response = http.get<UserResponse, UserRequest>("/users") {
    body(UserRequest("123"))
}
```

`httpClient(...)` returns a typed `HttpClient` backed by OkHttp.

If you need lower-level control, the request object is still available:

```kotlin
val request = networkRequest<UserRequest, UserResponse>(
    path = "/users",
    method = HttpMethod.POST,
    body = UserRequest("123"),
)

val response = http.execute(request)
```

### Notifications

```kotlin
val permissionManager = permissionManager(context, requester)
val notificationCenter = notifications(context, permissionManager)
val permissions = permissions(permissionManager)

permissions.request(listOf(Permissions.Camera, Permissions.Notifications)) {
    granted { permission ->
        when (permission) {
            Permissions.Camera -> openCamera()
            Permissions.Notifications -> notificationCenter.channel("sync").show("Sync completed")
        }
    }
    denied { permission ->
        showPermissionMessage(permission)
    }
    permanentlyDenied {
        openAppSettings()
    }
}
```

Add the permission in your app manifest:

```xml
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
```

### Intents

```kotlin
intentLauncher.launch(IntentActions.openSettings(context))
intentLauncher.launch(IntentActions.openUrl("https://github.com/kkokotero/KoreX"))
intentLauncher.launch(IntentActions.shareContent("KoreX", "Built with KoreX"))
```

### Lifecycle helpers

```kotlin
val jobs = LifecycleTaskGroup()
jobs.add(LifecycleTask { scope ->
    scope.launch {
        // background work tied to the app scope
    }
})
jobs.start(autoCancelJob.scope)
```

## SampleApp

`SampleApp` is a separate Android app module that demonstrates:

- state observation
- pipeline execution
- validation
- retry handling
- network requests
- notifications and permissions
- intents
- lifecycle-aware tasks

It is intentionally small and exists only as a live usage reference.

## Contributing

Read [CONTRIBUTING.md](CONTRIBUTING.md) before opening a pull request.

## Code of conduct

Read [CODE_OF_CONDUCT.md](CODE_OF_CONDUCT.md).

## Security

Read [SECURITY.md](SECURITY.md).

## License

MIT. See [LICENSE](LICENSE).
