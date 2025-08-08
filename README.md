# Atomic Transact Android SDK — Demo

This repository contains the Android SDK artifacts and a demo app that shows how to integrate and run the Transact experience on Android devices.

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

## What this repo's CI does

- The repository contains CI/release pipelines that publish the built SDK artifacts to Maven Central. Those pipelines are for publishing the SDK only — use the demo app to test integrations locally.

## Issues & Contributing

If you find issues with the demo or the SDK, open an issue or PR in this repository with reproduction steps.
