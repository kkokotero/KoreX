package io.github.kkokotero.korex.android

object Permissions {
    private fun permission(name: String, rationale: String? = null): Permission = Permission(name, rationale)

    // ACCESS
    val AccessBackgroundLocation = permission("android.permission.ACCESS_BACKGROUND_LOCATION")
    val AccessBiometricSensorStrengths = permission("android.permission.ACCESS_BIOMETRIC_SENSOR_STRENGTHS")
    val AccessBlobsAcrossUsers = permission("android.permission.ACCESS_BLOBS_ACROSS_USERS")
    val AccessCheckinProperties = permission("android.permission.ACCESS_CHECKIN_PROPERTIES")
    val AccessCoarseLocation = permission("android.permission.ACCESS_COARSE_LOCATION")
    val AccessFineLocation = permission("android.permission.ACCESS_FINE_LOCATION")
    val AccessHiddenProfiles = permission("android.permission.ACCESS_HIDDEN_PROFILES")
    val AccessLauncherData = permission("android.permission.ACCESS_LAUNCHER_DATA")
    val AccessLocalNetwork = permission("android.permission.ACCESS_LOCAL_NETWORK")
    val AccessLocationExtraCommands = permission("android.permission.ACCESS_LOCATION_EXTRA_COMMANDS")
    val AccessMediaLocation = permission("android.permission.ACCESS_MEDIA_LOCATION")
    val AccessNetworkState = permission("android.permission.ACCESS_NETWORK_STATE")
    val AccessNotificationPolicy = permission("android.permission.ACCESS_NOTIFICATION_POLICY")
    val AccessWifiState = permission("android.permission.ACCESS_WIFI_STATE")

    // ACCOUNT
    val AccountManager = permission("android.permission.ACCOUNT_MANAGER")

    // ACTIVITY
    val ActivityRecognition = permission("android.permission.ACTIVITY_RECOGNITION")

    // ADD
    val AddVoicemail = permission("android.permission.ADD_VOICEMAIL")

    // APPLY
    val ApplyPictureProfile = permission("android.permission.APPLY_PICTURE_PROFILE")

    // BATTERY
    val BatteryStats = permission("android.permission.BATTERY_STATS")

    // BIND
    val BindAccessibilityService = permission("android.permission.BIND_ACCESSIBILITY_SERVICE")
    val BindAlternativeMessageTransportService = permission("android.permission.BIND_ALTERNATIVE_MESSAGE_TRANSPORT_SERVICE")
    val BindAppwidget = permission("android.permission.BIND_APPWIDGET")
    val BindAppFunctionService = permission("android.permission.BIND_APP_FUNCTION_SERVICE")
    val BindAutofillService = permission("android.permission.BIND_AUTOFILL_SERVICE")
    val BindCallRedirectionService = permission("android.permission.BIND_CALL_REDIRECTION_SERVICE")
    val BindCarrierMessagingClientService = permission("android.permission.BIND_CARRIER_MESSAGING_CLIENT_SERVICE")
    val BindCarrierMessagingService = permission("android.permission.BIND_CARRIER_MESSAGING_SERVICE")
    val BindCarrierServices = permission("android.permission.BIND_CARRIER_SERVICES")
    val BindChooserTargetService = permission("android.permission.BIND_CHOOSER_TARGET_SERVICE")
    val BindCompanionDeviceService = permission("android.permission.BIND_COMPANION_DEVICE_SERVICE")
    val BindConditionProviderService = permission("android.permission.BIND_CONDITION_PROVIDER_SERVICE")
    val BindControls = permission("android.permission.BIND_CONTROLS")
    val BindCredentialProviderService = permission("android.permission.BIND_CREDENTIAL_PROVIDER_SERVICE")
    val BindDataMigrationForPrivatecompute = permission("android.permission.BIND_DATA_MIGRATION_FOR_PRIVATECOMPUTE")
    val BindDeviceAdmin = permission("android.permission.BIND_DEVICE_ADMIN")
    val BindDreamService = permission("android.permission.BIND_DREAM_SERVICE")
    val BindIncallService = permission("android.permission.BIND_INCALL_SERVICE")
    val BindInputMethod = permission("android.permission.BIND_INPUT_METHOD")
    val BindMidiDeviceService = permission("android.permission.BIND_MIDI_DEVICE_SERVICE")
    val BindNfcService = permission("android.permission.BIND_NFC_SERVICE")
    val BindNotificationListenerService = permission("android.permission.BIND_NOTIFICATION_LISTENER_SERVICE")
    val BindPrintService = permission("android.permission.BIND_PRINT_SERVICE")
    val BindQuickAccessWalletService = permission("android.permission.BIND_QUICK_ACCESS_WALLET_SERVICE")
    val BindQuickSettingsTile = permission("android.permission.BIND_QUICK_SETTINGS_TILE")
    val BindRemoteviews = permission("android.permission.BIND_REMOTEVIEWS")
    val BindScreeningService = permission("android.permission.BIND_SCREENING_SERVICE")
    val BindTelecomConnectionService = permission("android.permission.BIND_TELECOM_CONNECTION_SERVICE")
    val BindTextService = permission("android.permission.BIND_TEXT_SERVICE")
    val BindTvAdService = permission("android.permission.BIND_TV_AD_SERVICE")
    val BindTvInput = permission("android.permission.BIND_TV_INPUT")
    val BindTvInteractiveApp = permission("android.permission.BIND_TV_INTERACTIVE_APP")
    val BindVisualVoicemailService = permission("android.permission.BIND_VISUAL_VOICEMAIL_SERVICE")
    val BindVoiceInteraction = permission("android.permission.BIND_VOICE_INTERACTION")
    val BindVpnService = permission("android.permission.BIND_VPN_SERVICE")
    val BindVrListenerService = permission("android.permission.BIND_VR_LISTENER_SERVICE")
    val BindWallpaper = permission("android.permission.BIND_WALLPAPER")

    // BLUETOOTH
    val Bluetooth = permission("android.permission.BLUETOOTH")
    val BluetoothAdmin = permission("android.permission.BLUETOOTH_ADMIN")
    val BluetoothAdvertise = permission("android.permission.BLUETOOTH_ADVERTISE")
    val BluetoothConnect = permission("android.permission.BLUETOOTH_CONNECT")
    val BluetoothPrivileged = permission("android.permission.BLUETOOTH_PRIVILEGED")
    val BluetoothScan = permission("android.permission.BLUETOOTH_SCAN")

    // BODY
    val BodySensors = permission("android.permission.BODY_SENSORS")
    val BodySensorsBackground = permission("android.permission.BODY_SENSORS_BACKGROUND")

    // BROADCAST
    val BroadcastPackageRemoved = permission("android.permission.BROADCAST_PACKAGE_REMOVED")
    val BroadcastSms = permission("android.permission.BROADCAST_SMS")
    val BroadcastSticky = permission("android.permission.BROADCAST_STICKY")
    val BroadcastWapPush = permission("android.permission.BROADCAST_WAP_PUSH")

    // CALL
    val CallCompanionApp = permission("android.permission.CALL_COMPANION_APP")
    val CallPhone = permission("android.permission.CALL_PHONE")
    val CallPrivileged = permission("android.permission.CALL_PRIVILEGED")

    // CAMERA
    val Camera = permission("android.permission.CAMERA")

    // CAPTURE
    val CaptureAudioOutput = permission("android.permission.CAPTURE_AUDIO_OUTPUT")
    val CaptureKeyboard = permission("android.permission.CAPTURE_KEYBOARD")

    // CHANGE
    val ChangeComponentEnabledState = permission("android.permission.CHANGE_COMPONENT_ENABLED_STATE")
    val ChangeConfiguration = permission("android.permission.CHANGE_CONFIGURATION")
    val ChangeNetworkState = permission("android.permission.CHANGE_NETWORK_STATE")
    val ChangeWifiMulticastState = permission("android.permission.CHANGE_WIFI_MULTICAST_STATE")
    val ChangeWifiState = permission("android.permission.CHANGE_WIFI_STATE")

    // CLEAR
    val ClearAppCache = permission("android.permission.CLEAR_APP_CACHE")

    // CONFIGURE
    val ConfigureWifiDisplay = permission("android.permission.CONFIGURE_WIFI_DISPLAY")

    // CONTROL
    val ControlLocationUpdates = permission("android.permission.CONTROL_LOCATION_UPDATES")

    // CREDENTIAL
    val CredentialManagerQueryCandidateCredentials = permission("android.permission.CREDENTIAL_MANAGER_QUERY_CANDIDATE_CREDENTIALS")
    val CredentialManagerSetAllowedProviders = permission("android.permission.CREDENTIAL_MANAGER_SET_ALLOWED_PROVIDERS")
    val CredentialManagerSetOrigin = permission("android.permission.CREDENTIAL_MANAGER_SET_ORIGIN")

    // DELETE
    val DeleteCacheFiles = permission("android.permission.DELETE_CACHE_FILES")
    val DeletePackages = permission("android.permission.DELETE_PACKAGES")

    // DELIVER
    val DeliverCompanionMessages = permission("android.permission.DELIVER_COMPANION_MESSAGES")

    // DETECT
    val DetectScreenCapture = permission("android.permission.DETECT_SCREEN_CAPTURE")
    val DetectScreenRecording = permission("android.permission.DETECT_SCREEN_RECORDING")

    // DIAGNOSTIC
    val Diagnostic = permission("android.permission.DIAGNOSTIC")

    // DISABLE
    val DisableKeyguard = permission("android.permission.DISABLE_KEYGUARD")

    // DUMP
    val Dump = permission("android.permission.DUMP")

    // ENFORCE
    val EnforceUpdateOwnership = permission("android.permission.ENFORCE_UPDATE_OWNERSHIP")

    // EXECUTE
    val ExecuteAppAction = permission("android.permission.EXECUTE_APP_ACTION")
    val ExecuteAppFunctions = permission("android.permission.EXECUTE_APP_FUNCTIONS")

    // EXPAND
    val ExpandStatusBar = permission("android.permission.EXPAND_STATUS_BAR")

    // FACTORY
    val FactoryTest = permission("android.permission.FACTORY_TEST")

    // FOREGROUND
    val ForegroundService = permission("android.permission.FOREGROUND_SERVICE")
    val ForegroundServiceCamera = permission("android.permission.FOREGROUND_SERVICE_CAMERA")
    val ForegroundServiceConnectedDevice = permission("android.permission.FOREGROUND_SERVICE_CONNECTED_DEVICE")
    val ForegroundServiceDataSync = permission("android.permission.FOREGROUND_SERVICE_DATA_SYNC")
    val ForegroundServiceHealth = permission("android.permission.FOREGROUND_SERVICE_HEALTH")
    val ForegroundServiceLocation = permission("android.permission.FOREGROUND_SERVICE_LOCATION")
    val ForegroundServiceMediaPlayback = permission("android.permission.FOREGROUND_SERVICE_MEDIA_PLAYBACK")
    val ForegroundServiceMediaProcessing = permission("android.permission.FOREGROUND_SERVICE_MEDIA_PROCESSING")
    val ForegroundServiceMediaProjection = permission("android.permission.FOREGROUND_SERVICE_MEDIA_PROJECTION")
    val ForegroundServiceMicrophone = permission("android.permission.FOREGROUND_SERVICE_MICROPHONE")
    val ForegroundServicePhoneCall = permission("android.permission.FOREGROUND_SERVICE_PHONE_CALL")
    val ForegroundServiceRemoteMessaging = permission("android.permission.FOREGROUND_SERVICE_REMOTE_MESSAGING")
    val ForegroundServiceSpecialUse = permission("android.permission.FOREGROUND_SERVICE_SPECIAL_USE")
    val ForegroundServiceSystemExempted = permission("android.permission.FOREGROUND_SERVICE_SYSTEM_EXEMPTED")

    // GET
    val GetAccounts = permission("android.permission.GET_ACCOUNTS")
    val GetAccountsPrivileged = permission("android.permission.GET_ACCOUNTS_PRIVILEGED")
    val GetPackageSize = permission("android.permission.GET_PACKAGE_SIZE")
    val GetTasks = permission("android.permission.GET_TASKS")

    // GLOBAL
    val GlobalSearch = permission("android.permission.GLOBAL_SEARCH")

    // HIDE
    val HideOverlayWindows = permission("android.permission.HIDE_OVERLAY_WINDOWS")

    // HIGH
    val HighSamplingRateSensors = permission("android.permission.HIGH_SAMPLING_RATE_SENSORS")

    // INSTALL
    val InstallLocationProvider = permission("android.permission.INSTALL_LOCATION_PROVIDER")
    val InstallPackages = permission("android.permission.INSTALL_PACKAGES")
    val InstallShortcut = permission("android.permission.INSTALL_SHORTCUT")

    // INSTANT
    val InstantAppForegroundService = permission("android.permission.INSTANT_APP_FOREGROUND_SERVICE")

    // INTERACT
    val InteractAcrossProfiles = permission("android.permission.INTERACT_ACROSS_PROFILES")

    // INTERNET
    val Internet = permission("android.permission.INTERNET")

    // KILL
    val KillBackgroundProcesses = permission("android.permission.KILL_BACKGROUND_PROCESSES")

    // LAUNCH
    val LaunchCaptureContentActivityForNote = permission("android.permission.LAUNCH_CAPTURE_CONTENT_ACTIVITY_FOR_NOTE")
    val LaunchMultiPaneSettingsDeepLink = permission("android.permission.LAUNCH_MULTI_PANE_SETTINGS_DEEP_LINK")

    // LOADER
    val LoaderUsageStats = permission("android.permission.LOADER_USAGE_STATS")

    // LOCATION
    val LocationHardware = permission("android.permission.LOCATION_HARDWARE")

    // MANAGE
    val ManageDeviceLockState = permission("android.permission.MANAGE_DEVICE_LOCK_STATE")
    val ManageDevicePolicyAccessibility = permission("android.permission.MANAGE_DEVICE_POLICY_ACCESSIBILITY")
    val ManageDevicePolicyAccountManagement = permission("android.permission.MANAGE_DEVICE_POLICY_ACCOUNT_MANAGEMENT")
    val ManageDevicePolicyAcrossUsers = permission("android.permission.MANAGE_DEVICE_POLICY_ACROSS_USERS")
    val ManageDevicePolicyAcrossUsersFull = permission("android.permission.MANAGE_DEVICE_POLICY_ACROSS_USERS_FULL")
    val ManageDevicePolicyAcrossUsersSecurityCritical = permission("android.permission.MANAGE_DEVICE_POLICY_ACROSS_USERS_SECURITY_CRITICAL")
    val ManageDevicePolicyAirplaneMode = permission("android.permission.MANAGE_DEVICE_POLICY_AIRPLANE_MODE")
    val ManageDevicePolicyAppsControl = permission("android.permission.MANAGE_DEVICE_POLICY_APPS_CONTROL")
    val ManageDevicePolicyAppFunctions = permission("android.permission.MANAGE_DEVICE_POLICY_APP_FUNCTIONS")
    val ManageDevicePolicyAppRestrictions = permission("android.permission.MANAGE_DEVICE_POLICY_APP_RESTRICTIONS")
    val ManageDevicePolicyAppUserData = permission("android.permission.MANAGE_DEVICE_POLICY_APP_USER_DATA")
    val ManageDevicePolicyAssistContent = permission("android.permission.MANAGE_DEVICE_POLICY_ASSIST_CONTENT")
    val ManageDevicePolicyAudioOutput = permission("android.permission.MANAGE_DEVICE_POLICY_AUDIO_OUTPUT")
    val ManageDevicePolicyAutofill = permission("android.permission.MANAGE_DEVICE_POLICY_AUTOFILL")
    val ManageDevicePolicyBackupService = permission("android.permission.MANAGE_DEVICE_POLICY_BACKUP_SERVICE")
    val ManageDevicePolicyBlockUninstall = permission("android.permission.MANAGE_DEVICE_POLICY_BLOCK_UNINSTALL")
    val ManageDevicePolicyBluetooth = permission("android.permission.MANAGE_DEVICE_POLICY_BLUETOOTH")
    val ManageDevicePolicyBugreport = permission("android.permission.MANAGE_DEVICE_POLICY_BUGREPORT")
    val ManageDevicePolicyCalls = permission("android.permission.MANAGE_DEVICE_POLICY_CALLS")
    val ManageDevicePolicyCamera = permission("android.permission.MANAGE_DEVICE_POLICY_CAMERA")
    val ManageDevicePolicyCameraToggle = permission("android.permission.MANAGE_DEVICE_POLICY_CAMERA_TOGGLE")
    val ManageDevicePolicyCertificates = permission("android.permission.MANAGE_DEVICE_POLICY_CERTIFICATES")
    val ManageDevicePolicyCommonCriteriaMode = permission("android.permission.MANAGE_DEVICE_POLICY_COMMON_CRITERIA_MODE")
    val ManageDevicePolicyContentProtection = permission("android.permission.MANAGE_DEVICE_POLICY_CONTENT_PROTECTION")
    val ManageDevicePolicyDebuggingFeatures = permission("android.permission.MANAGE_DEVICE_POLICY_DEBUGGING_FEATURES")
    val ManageDevicePolicyDefaultSms = permission("android.permission.MANAGE_DEVICE_POLICY_DEFAULT_SMS")
    val ManageDevicePolicyDeviceIdentifiers = permission("android.permission.MANAGE_DEVICE_POLICY_DEVICE_IDENTIFIERS")
    val ManageDevicePolicyDisplay = permission("android.permission.MANAGE_DEVICE_POLICY_DISPLAY")
    val ManageDevicePolicyFactoryReset = permission("android.permission.MANAGE_DEVICE_POLICY_FACTORY_RESET")
    val ManageDevicePolicyFun = permission("android.permission.MANAGE_DEVICE_POLICY_FUN")
    val ManageDevicePolicyInputMethods = permission("android.permission.MANAGE_DEVICE_POLICY_INPUT_METHODS")
    val ManageDevicePolicyInstallUnknownSources = permission("android.permission.MANAGE_DEVICE_POLICY_INSTALL_UNKNOWN_SOURCES")
    val ManageDevicePolicyKeepUninstalledPackages = permission("android.permission.MANAGE_DEVICE_POLICY_KEEP_UNINSTALLED_PACKAGES")
    val ManageDevicePolicyKeyguard = permission("android.permission.MANAGE_DEVICE_POLICY_KEYGUARD")
    val ManageDevicePolicyLocale = permission("android.permission.MANAGE_DEVICE_POLICY_LOCALE")
    val ManageDevicePolicyLocation = permission("android.permission.MANAGE_DEVICE_POLICY_LOCATION")
    val ManageDevicePolicyLock = permission("android.permission.MANAGE_DEVICE_POLICY_LOCK")
    val ManageDevicePolicyLockCredentials = permission("android.permission.MANAGE_DEVICE_POLICY_LOCK_CREDENTIALS")
    val ManageDevicePolicyLockTask = permission("android.permission.MANAGE_DEVICE_POLICY_LOCK_TASK")
    val ManageDevicePolicyManagedSubscriptions = permission("android.permission.MANAGE_DEVICE_POLICY_MANAGED_SUBSCRIPTIONS")
    val ManageDevicePolicyMeteredData = permission("android.permission.MANAGE_DEVICE_POLICY_METERED_DATA")
    val ManageDevicePolicyMicrophone = permission("android.permission.MANAGE_DEVICE_POLICY_MICROPHONE")
    val ManageDevicePolicyMicrophoneToggle = permission("android.permission.MANAGE_DEVICE_POLICY_MICROPHONE_TOGGLE")
    val ManageDevicePolicyMobileNetwork = permission("android.permission.MANAGE_DEVICE_POLICY_MOBILE_NETWORK")
    val ManageDevicePolicyModifyUsers = permission("android.permission.MANAGE_DEVICE_POLICY_MODIFY_USERS")
    val ManageDevicePolicyMte = permission("android.permission.MANAGE_DEVICE_POLICY_MTE")
    val ManageDevicePolicyNearbyCommunication = permission("android.permission.MANAGE_DEVICE_POLICY_NEARBY_COMMUNICATION")
    val ManageDevicePolicyNetworkLogging = permission("android.permission.MANAGE_DEVICE_POLICY_NETWORK_LOGGING")
    val ManageDevicePolicyOrganizationIdentity = permission("android.permission.MANAGE_DEVICE_POLICY_ORGANIZATION_IDENTITY")
    val ManageDevicePolicyOverrideApn = permission("android.permission.MANAGE_DEVICE_POLICY_OVERRIDE_APN")
    val ManageDevicePolicyPackageState = permission("android.permission.MANAGE_DEVICE_POLICY_PACKAGE_STATE")
    val ManageDevicePolicyPhysicalMedia = permission("android.permission.MANAGE_DEVICE_POLICY_PHYSICAL_MEDIA")
    val ManageDevicePolicyPrinting = permission("android.permission.MANAGE_DEVICE_POLICY_PRINTING")
    val ManageDevicePolicyPrivateDns = permission("android.permission.MANAGE_DEVICE_POLICY_PRIVATE_DNS")
    val ManageDevicePolicyProfiles = permission("android.permission.MANAGE_DEVICE_POLICY_PROFILES")
    val ManageDevicePolicyProfileInteraction = permission("android.permission.MANAGE_DEVICE_POLICY_PROFILE_INTERACTION")
    val ManageDevicePolicyProxy = permission("android.permission.MANAGE_DEVICE_POLICY_PROXY")
    val ManageDevicePolicyQuerySystemUpdates = permission("android.permission.MANAGE_DEVICE_POLICY_QUERY_SYSTEM_UPDATES")
    val ManageDevicePolicyResetPassword = permission("android.permission.MANAGE_DEVICE_POLICY_RESET_PASSWORD")
    val ManageDevicePolicyRestrictPrivateDns = permission("android.permission.MANAGE_DEVICE_POLICY_RESTRICT_PRIVATE_DNS")
    val ManageDevicePolicyRuntimePermissions = permission("android.permission.MANAGE_DEVICE_POLICY_RUNTIME_PERMISSIONS")
    val ManageDevicePolicyRunInBackground = permission("android.permission.MANAGE_DEVICE_POLICY_RUN_IN_BACKGROUND")
    val ManageDevicePolicySafeBoot = permission("android.permission.MANAGE_DEVICE_POLICY_SAFE_BOOT")
    val ManageDevicePolicyScreenCapture = permission("android.permission.MANAGE_DEVICE_POLICY_SCREEN_CAPTURE")
    val ManageDevicePolicyScreenContent = permission("android.permission.MANAGE_DEVICE_POLICY_SCREEN_CONTENT")
    val ManageDevicePolicySecurityLogging = permission("android.permission.MANAGE_DEVICE_POLICY_SECURITY_LOGGING")
    val ManageDevicePolicySettings = permission("android.permission.MANAGE_DEVICE_POLICY_SETTINGS")
    val ManageDevicePolicySms = permission("android.permission.MANAGE_DEVICE_POLICY_SMS")
    val ManageDevicePolicyStatusBar = permission("android.permission.MANAGE_DEVICE_POLICY_STATUS_BAR")
    val ManageDevicePolicySupportMessage = permission("android.permission.MANAGE_DEVICE_POLICY_SUPPORT_MESSAGE")
    val ManageDevicePolicySuspendPersonalApps = permission("android.permission.MANAGE_DEVICE_POLICY_SUSPEND_PERSONAL_APPS")
    val ManageDevicePolicySystemApps = permission("android.permission.MANAGE_DEVICE_POLICY_SYSTEM_APPS")
    val ManageDevicePolicySystemDialogs = permission("android.permission.MANAGE_DEVICE_POLICY_SYSTEM_DIALOGS")
    val ManageDevicePolicySystemUpdates = permission("android.permission.MANAGE_DEVICE_POLICY_SYSTEM_UPDATES")
    val ManageDevicePolicyThreadNetwork = permission("android.permission.MANAGE_DEVICE_POLICY_THREAD_NETWORK")
    val ManageDevicePolicyTime = permission("android.permission.MANAGE_DEVICE_POLICY_TIME")
    val ManageDevicePolicyUsbDataSignalling = permission("android.permission.MANAGE_DEVICE_POLICY_USB_DATA_SIGNALLING")
    val ManageDevicePolicyUsbFileTransfer = permission("android.permission.MANAGE_DEVICE_POLICY_USB_FILE_TRANSFER")
    val ManageDevicePolicyUsers = permission("android.permission.MANAGE_DEVICE_POLICY_USERS")
    val ManageDevicePolicyVpn = permission("android.permission.MANAGE_DEVICE_POLICY_VPN")
    val ManageDevicePolicyWallpaper = permission("android.permission.MANAGE_DEVICE_POLICY_WALLPAPER")
    val ManageDevicePolicyWifi = permission("android.permission.MANAGE_DEVICE_POLICY_WIFI")
    val ManageDevicePolicyWindows = permission("android.permission.MANAGE_DEVICE_POLICY_WINDOWS")
    val ManageDevicePolicyWipeData = permission("android.permission.MANAGE_DEVICE_POLICY_WIPE_DATA")
    val ManageDocuments = permission("android.permission.MANAGE_DOCUMENTS")
    val ManageExternalStorage = permission("android.permission.MANAGE_EXTERNAL_STORAGE")
    val ManageMedia = permission("android.permission.MANAGE_MEDIA")
    val ManageOngoingCalls = permission("android.permission.MANAGE_ONGOING_CALLS")
    val ManageOwnCalls = permission("android.permission.MANAGE_OWN_CALLS")
    val ManageWifiInterfaces = permission("android.permission.MANAGE_WIFI_INTERFACES")
    val ManageWifiNetworkSelection = permission("android.permission.MANAGE_WIFI_NETWORK_SELECTION")

    // MASTER
    val MasterClear = permission("android.permission.MASTER_CLEAR")

    // MEDIA
    val MediaContentControl = permission("android.permission.MEDIA_CONTENT_CONTROL")
    val MediaRoutingControl = permission("android.permission.MEDIA_ROUTING_CONTROL")

    // MODIFY
    val ModifyAudioSettings = permission("android.permission.MODIFY_AUDIO_SETTINGS")
    val ModifyPhoneState = permission("android.permission.MODIFY_PHONE_STATE")

    // MOUNT
    val MountFormatFilesystems = permission("android.permission.MOUNT_FORMAT_FILESYSTEMS")
    val MountUnmountFilesystems = permission("android.permission.MOUNT_UNMOUNT_FILESYSTEMS")

    // NEARBY
    val NearbyWifiDevices = permission("android.permission.NEARBY_WIFI_DEVICES")

    // NFC
    val Nfc = permission("android.permission.NFC")
    val NfcPreferredPaymentInfo = permission("android.permission.NFC_PREFERRED_PAYMENT_INFO")
    val NfcTransactionEvent = permission("android.permission.NFC_TRANSACTION_EVENT")

    // OVERRIDE
    val OverrideMediaSessionOwner = permission("android.permission.OVERRIDE_MEDIA_SESSION_OWNER")
    val OverrideWifiConfig = permission("android.permission.OVERRIDE_WIFI_CONFIG")

    // PACKAGE
    val PackageUsageStats = permission("android.permission.PACKAGE_USAGE_STATS")

    // PERSISTENT
    val PersistentActivity = permission("android.permission.PERSISTENT_ACTIVITY")

    // POST
    val PostNotifications = permission("android.permission.POST_NOTIFICATIONS")
    val Notifications = PostNotifications
    val PostPromotedNotifications = permission("android.permission.POST_PROMOTED_NOTIFICATIONS")

    // PROCESS
    val ProcessOutgoingCalls = permission("android.permission.PROCESS_OUTGOING_CALLS")

    // PROVIDE
    val ProvideOwnAutofillSuggestions = permission("android.permission.PROVIDE_OWN_AUTOFILL_SUGGESTIONS")
    val ProvidePrivateComputeServices = permission("android.permission.PROVIDE_PRIVATE_COMPUTE_SERVICES")
    val ProvideRemoteCredentials = permission("android.permission.PROVIDE_REMOTE_CREDENTIALS")

    // QUERY
    val QueryAdvancedProtectionMode = permission("android.permission.QUERY_ADVANCED_PROTECTION_MODE")
    val QueryAllPackages = permission("android.permission.QUERY_ALL_PACKAGES")

    // RANGING
    val Ranging = permission("android.permission.RANGING")

    // READ
    val ReadAssistantAppSearchData = permission("android.permission.READ_ASSISTANT_APP_SEARCH_DATA")
    val ReadAssistStructureScreenContent = permission("android.permission.READ_ASSIST_STRUCTURE_SCREEN_CONTENT")
    val ReadBasicPhoneState = permission("android.permission.READ_BASIC_PHONE_STATE")
    val ReadCalendar = permission("android.permission.READ_CALENDAR")
    val ReadCallLog = permission("android.permission.READ_CALL_LOG")
    val ReadColorZones = permission("android.permission.READ_COLOR_ZONES")
    val ReadContacts = permission("android.permission.READ_CONTACTS")
    val ReadDropboxData = permission("android.permission.READ_DROPBOX_DATA")
    val ReadExternalStorage = permission("android.permission.READ_EXTERNAL_STORAGE")
    val ReadHomeAppSearchData = permission("android.permission.READ_HOME_APP_SEARCH_DATA")
    val ReadInputState = permission("android.permission.READ_INPUT_STATE")
    val ReadLogs = permission("android.permission.READ_LOGS")
    val ReadMediaAudio = permission("android.permission.READ_MEDIA_AUDIO")
    val ReadMediaImages = permission("android.permission.READ_MEDIA_IMAGES")
    val ReadMediaVideo = permission("android.permission.READ_MEDIA_VIDEO")
    val ReadMediaVisualUserSelected = permission("android.permission.READ_MEDIA_VISUAL_USER_SELECTED")
    val ReadNearbyStreamingPolicy = permission("android.permission.READ_NEARBY_STREAMING_POLICY")
    val ReadPhoneNumbers = permission("android.permission.READ_PHONE_NUMBERS")
    val ReadPhoneState = permission("android.permission.READ_PHONE_STATE")
    val ReadPrecisePhoneState = permission("android.permission.READ_PRECISE_PHONE_STATE")
    val ReadSms = permission("android.permission.READ_SMS")
    val ReadSyncSettings = permission("android.permission.READ_SYNC_SETTINGS")
    val ReadSyncStats = permission("android.permission.READ_SYNC_STATS")
    val ReadSystemPreferences = permission("android.permission.READ_SYSTEM_PREFERENCES")
    val ReadVoicemail = permission("android.permission.READ_VOICEMAIL")

    // REBOOT
    val Reboot = permission("android.permission.REBOOT")

    // RECEIVE
    val ReceiveBootCompleted = permission("android.permission.RECEIVE_BOOT_COMPLETED")
    val ReceiveMms = permission("android.permission.RECEIVE_MMS")
    val ReceiveSensitiveNotifications = permission("android.permission.RECEIVE_SENSITIVE_NOTIFICATIONS")
    val ReceiveSms = permission("android.permission.RECEIVE_SMS")
    val ReceiveWapPush = permission("android.permission.RECEIVE_WAP_PUSH")

    // RECORD
    val RecordAudio = permission("android.permission.RECORD_AUDIO")

    // REORDER
    val ReorderTasks = permission("android.permission.REORDER_TASKS")

    // REPOSITION
    val RepositionSelfWindows = permission("android.permission.REPOSITION_SELF_WINDOWS")

    // REQUEST
    val RequestCompanionProfileAppStreaming = permission("android.permission.REQUEST_COMPANION_PROFILE_APP_STREAMING")
    val RequestCompanionProfileAutomotiveProjection = permission("android.permission.REQUEST_COMPANION_PROFILE_AUTOMOTIVE_PROJECTION")
    val RequestCompanionProfileComputer = permission("android.permission.REQUEST_COMPANION_PROFILE_COMPUTER")
    val RequestCompanionProfileGlasses = permission("android.permission.REQUEST_COMPANION_PROFILE_GLASSES")
    val RequestCompanionProfileMedical = permission("android.permission.REQUEST_COMPANION_PROFILE_MEDICAL")
    val RequestCompanionProfileNearbyDeviceStreaming = permission("android.permission.REQUEST_COMPANION_PROFILE_NEARBY_DEVICE_STREAMING")
    val RequestCompanionProfileWatch = permission("android.permission.REQUEST_COMPANION_PROFILE_WATCH")
    val RequestCompanionRunInBackground = permission("android.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND")
    val RequestCompanionSelfManaged = permission("android.permission.REQUEST_COMPANION_SELF_MANAGED")
    val RequestCompanionStartForegroundServicesFromBackground = permission("android.permission.REQUEST_COMPANION_START_FOREGROUND_SERVICES_FROM_BACKGROUND")
    val RequestCompanionUseDataInBackground = permission("android.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND")
    val RequestDeletePackages = permission("android.permission.REQUEST_DELETE_PACKAGES")
    val RequestIgnoreBatteryOptimizations = permission("android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS")
    val RequestInstallPackages = permission("android.permission.REQUEST_INSTALL_PACKAGES")
    val RequestObserveCompanionDevicePresence = permission("android.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE")
    val RequestObserveDeviceUuidPresence = permission("android.permission.REQUEST_OBSERVE_DEVICE_UUID_PRESENCE")
    val RequestPasswordComplexity = permission("android.permission.REQUEST_PASSWORD_COMPLEXITY")

    // RESTART
    val RestartPackages = permission("android.permission.RESTART_PACKAGES")

    // RUN
    val RunUserInitiatedJobs = permission("android.permission.RUN_USER_INITIATED_JOBS")

    // SCHEDULE
    val ScheduleExactAlarm = permission("android.permission.SCHEDULE_EXACT_ALARM")

    // SEND
    val SendRespondViaMessage = permission("android.permission.SEND_RESPOND_VIA_MESSAGE")
    val SendSms = permission("android.permission.SEND_SMS")

    // SET
    val SetAlarm = permission("android.permission.SET_ALARM")
    val SetAlwaysFinish = permission("android.permission.SET_ALWAYS_FINISH")
    val SetAnimationScale = permission("android.permission.SET_ANIMATION_SCALE")
    val SetBiometricDialogAdvanced = permission("android.permission.SET_BIOMETRIC_DIALOG_ADVANCED")
    val SetDebugApp = permission("android.permission.SET_DEBUG_APP")
    val SetPreferredApplications = permission("android.permission.SET_PREFERRED_APPLICATIONS")
    val SetProcessLimit = permission("android.permission.SET_PROCESS_LIMIT")
    val SetTime = permission("android.permission.SET_TIME")
    val SetTimeZone = permission("android.permission.SET_TIME_ZONE")
    val SetWallpaper = permission("android.permission.SET_WALLPAPER")
    val SetWallpaperHints = permission("android.permission.SET_WALLPAPER_HINTS")

    // SHOW
    val ShowPowerMenu = permission("android.permission.SHOW_POWER_MENU")
    val ShowPowerMenuPrivileged = permission("android.permission.SHOW_POWER_MENU_PRIVILEGED")

    // SIGNAL
    val SignalPersistentProcesses = permission("android.permission.SIGNAL_PERSISTENT_PROCESSES")

    // SMS
    val SmsFinancialTransactions = permission("android.permission.SMS_FINANCIAL_TRANSACTIONS")

    // START
    val StartForegroundServicesFromBackground = permission("android.permission.START_FOREGROUND_SERVICES_FROM_BACKGROUND")
    val StartViewAppFeatures = permission("android.permission.START_VIEW_APP_FEATURES")
    val StartViewPermissionUsage = permission("android.permission.START_VIEW_PERMISSION_USAGE")

    // STATUS
    val StatusBar = permission("android.permission.STATUS_BAR")

    // SUBSCRIBE
    val SubscribeToKeyguardLockedState = permission("android.permission.SUBSCRIBE_TO_KEYGUARD_LOCKED_STATE")

    // SYSTEM
    val SystemAlertWindow = permission("android.permission.SYSTEM_ALERT_WINDOW")

    // TRANSMIT
    val TransmitIr = permission("android.permission.TRANSMIT_IR")

    // TURN
    val TurnScreenOn = permission("android.permission.TURN_SCREEN_ON")

    // TV
    val TvImplicitEnterPip = permission("android.permission.TV_IMPLICIT_ENTER_PIP")

    // UNINSTALL
    val UninstallShortcut = permission("android.permission.UNINSTALL_SHORTCUT")

    // UPDATE
    val UpdateDeviceStats = permission("android.permission.UPDATE_DEVICE_STATS")
    val UpdatePackagesWithoutUserAction = permission("android.permission.UPDATE_PACKAGES_WITHOUT_USER_ACTION")

    // USE
    val UseBiometric = permission("android.permission.USE_BIOMETRIC")
    val UseExactAlarm = permission("android.permission.USE_EXACT_ALARM")
    val UseFingerprint = permission("android.permission.USE_FINGERPRINT")
    val UseFullScreenIntent = permission("android.permission.USE_FULL_SCREEN_INTENT")
    val UseIccAuthWithDeviceIdentifier = permission("android.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER")
    val UseLocationButton = permission("android.permission.USE_LOCATION_BUTTON")
    val UsePinnedWindowingLayer = permission("android.permission.USE_PINNED_WINDOWING_LAYER")
    val UseSip = permission("android.permission.USE_SIP")

    // UWB
    val UwbRanging = permission("android.permission.UWB_RANGING")

    // VIBRATE
    val Vibrate = permission("android.permission.VIBRATE")

    // WAKE
    val WakeLock = permission("android.permission.WAKE_LOCK")

    // WRITE
    val WriteApnSettings = permission("android.permission.WRITE_APN_SETTINGS")
    val WriteCalendar = permission("android.permission.WRITE_CALENDAR")
    val WriteCallLog = permission("android.permission.WRITE_CALL_LOG")
    val WriteContacts = permission("android.permission.WRITE_CONTACTS")
    val WriteExternalStorage = permission("android.permission.WRITE_EXTERNAL_STORAGE")
    val WriteGservices = permission("android.permission.WRITE_GSERVICES")
    val WriteSecureSettings = permission("android.permission.WRITE_SECURE_SETTINGS")
    val WriteSettings = permission("android.permission.WRITE_SETTINGS")
    val WriteSyncSettings = permission("android.permission.WRITE_SYNC_SETTINGS")
    val WriteSystemPreferences = permission("android.permission.WRITE_SYSTEM_PREFERENCES")
    val WriteVoicemail = permission("android.permission.WRITE_VOICEMAIL")

    // ACCEPT
    val AcceptHandover = permission("android.permission.ACCEPT_HANDOVER")

    // ANSWER
    val AnswerPhoneCalls = permission("android.permission.ANSWER_PHONE_CALLS")

}
