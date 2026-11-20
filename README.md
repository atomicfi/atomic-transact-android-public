# Atomic Transact Android SDK — Public Helper Repo

This is a public helper repository for the Atomic Transact Android SDK. It holds the public release notes and a demo app that shows how to integrate and run the Transact experience on Android devices. The SDK source isn't developed here.

To add the SDK to your app, use the `financial.atomic:transact` artifact on [Maven Central](https://central.sonatype.com/artifact/financial.atomic/transact).

## Quick demo (run locally)

Prerequisites:

- Android Studio or an Android toolchain with SDK + emulator/device
- JDK 11+
- Ensure `local.properties` points to your Android SDK (the repo already contains a `local.properties` file placeholder).

Run the demo app from the project root:

```bash
# build the demo app
./gradlew :app:assembleDebug

# install to a connected device or running emulator
./gradlew :app:installDebug
```

You can also open the project in Android Studio and run the `app` module normally via the Run configuration.

## Configuration

- Edit `app/src/main/res/values/strings.xml` or the app's settings to provide any required public token or environment configuration used by the demo.
- For testing against local Services, point the demo's environment configuration to your local Transact/Services endpoints.
