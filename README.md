# IPN Tycoon (EDU_Tycoon)

IPN Tycoon is a tycoon-style management game set in the Instituto Politécnico Nacional (IPN). The player takes the role of Director General and manages schools, budget, teachers, and reputation.

This repository is a fork of [EmmanuelJuarez14/EDU_Tycoon](https://github.com/EmmanuelJuarez14/EDU_Tycoon).

## Tech stack

- [libGDX](https://libgdx.com/) 1.14.0 (game engine), generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff)
- Kotlin 2.2.10 with [KTX](https://libktx.github.io/) extensions
- AndroidX Room (local SQLite database for saved games)
- [Gradle](https://gradle.org/) with the included Gradle Wrapper

## Modules

- `core`: game logic shared by all platforms. Entry class: `io.moviles.IPN_Tycoon.Main`.
- `android`: Android launcher (`AndroidLauncher`), manifest, Android resources, and Room database. Needs the Android SDK.
- `assets`: game resources, including the isometric Tiled map `Mapa/Mapa_General.tmx`, packaged into the APK.
- `docs`: project documentation.

## Architecture

The project is split into two Gradle modules. `android` is the platform layer: it starts the app, builds the Room database, and injects the save system into the game. `core` holds the game itself, organized in three layers: presentation (screens and windows), logic (`engine/`), and data (`data/`). Everything runs on the device; the app does not use the network.

### Overview

![IPN Tycoon architecture overview](docs/img/arquitectura-general.png)

### App startup

`AndroidLauncher.onCreate()` creates the database, the repository, and `AndroidGameSaveManager`, and passes it to `Main`. This way `core` never depends on Android (dependency injection).

![App startup](docs/img/arquitectura-arranque.png)

### Game cycle

Every 30 seconds, `GameScreen` calls `GameCycleEngine.advanceCycle()`. The cycle notifies its listeners (`EconomyEngine`, `EstudiantesEngine`, `EventEngine`), which update `GameState` (Observer pattern).

![Game cycle](docs/img/arquitectura-ciclo-juego.png)

### Save system

Screens save and load through the `GameSaveManager` interface. On Android, `AndroidGameSaveManager` uses the repositories, DAOs, and entities to store data in a local SQLite database with Room.

![Save system](docs/img/arquitectura-persistencia.png)

## Tested environment

| Tool | Version |
|---|---|
| Operating system | Windows 11 Pro, 64-bit (x64) |
| Android Studio | Quail 3 2026.1.3 (Build #AI-261.26222.65.2613.15948027) |
| Gradle JDK | JDK 21 (Gradle JVM criteria in Android Studio) |
| Gradle distribution | Gradle Wrapper (included in the repository) |
| Android SDK Platform | API 35 (compileSdk / targetSdk) |
| Android Gradle Plugin | 8.9.3 |
| Kotlin | 2.2.10 |
| Test device | Xiaomi 2511FPC34G (physical phone, USB debugging), Android 16 (API 36) |

## Run from a clean clone

### Prerequisites

1. Install **Git**: https://git-scm.com/
2. Install **Android Studio** (a version compatible with Android Gradle Plugin 8.9.3). It includes a JDK, so no separate Java installation is needed. The project requires **JDK 17 or newer**.
3. In Android Studio, open **Tools > SDK Manager** and install **Android SDK Platform 35** (Android 15).

### Steps

1. Clone the repository:

   ```
   git clone https://github.com/JulioCesarCaballero/EDU_Tycoon.git
   ```

2. Open Android Studio, choose **File > Open**, and select the root `EDU_Tycoon` folder (not `android` or `core`).
3. Wait for the **Gradle Sync** to finish. The first sync downloads all dependencies and can take several minutes. Android Studio creates `local.properties` with your SDK path automatically.
4. If the sync fails because of the JDK, go to **File > Settings > Build, Execution, Deployment > Build Tools > Gradle** and set the Gradle JDK to version 17 or newer (the bundled JetBrains Runtime works).
5. Prepare a device:
    - **Emulator:** open **Device Manager**, create a virtual device with API 21 or higher, and start it.
    - **Physical phone:** follow [Running on a physical phone](#running-on-a-physical-phone) and connect it by USB.
6. In the run configuration selector (top toolbar), choose **`android`** and the target device.
7. Press **Run ▶**. The game opens in landscape mode.

### Command line alternative

From the repository root (Windows):

```
gradlew.bat android:installDebug
gradlew.bat android:run
```

On macOS/Linux use `./gradlew` instead of `gradlew.bat`. The `run` task needs either `local.properties` with `sdk.dir` or the `ANDROID_SDK_ROOT` environment variable, and a connected device or running emulator.

Note: `gradle.properties` sets the Gradle log level to `quiet`, so the console shows very little output when a build succeeds.

### Running on a physical phone

To install and run the game on a physical Android phone, enable these options first:

1. **Developer options:** go to **Settings > About phone** and tap **Build number** (on Xiaomi: **OS version**) seven times.
2. **USB debugging:** in **Settings > Developer options**, turn on **USB debugging**.
3. **Authorize the computer:** connect the phone by USB and accept the **"Allow USB debugging?"** prompt on the phone.
4. **Install via USB (Xiaomi / HyperOS / MIUI only):** in **Developer options**, turn on **Install via USB**. Without it, the phone blocks the installation from Android Studio.

After that, the phone appears in the device selector of Android Studio.

## Troubleshooting

No errors or complications were found during the first run of the project. The Gradle Sync and the build completed without changes to the code or configuration.

| Problem | Cause | Solution |
|---|---|---|
| No issues found on first run | Not applicable | Not applicable |

## Useful Gradle tasks

Run them with `gradlew.bat` (Windows) or `./gradlew` (macOS/Linux):

- `android:installDebug`: builds the debug APK and installs it on the connected device.
- `android:lint`: performs Android project validation.
- `build`: builds sources and archives of every project.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `test`: runs unit tests (if any).
- `--offline`: uses cached dependency archives.
- `--refresh-dependencies`: forces validation of all dependencies.

Most tasks can be limited to one module with the `name:` prefix. For example, `core:clean` removes the `build` folder only from the `core` project.

## License

MIT License. See [`LICENSE`](LICENSE) and [`docs/licencias.md`](docs/licencias.md) for third-party dependencies and resource provenance.
