# EasyBiz

Kotlin Multiplatform (Compose Multiplatform + iOS) project scaffold and roadmap.

This repository contains the starter app and platform entry points. The README below documents the current layout and the intended Clean Architecture organization that will be adopted inside `commonMain` for feature development.

---

Table of contents
- About
- Current project layout (quick)
- Planned Clean Architecture (inside `composeApp/src/commonMain/kotlin/com/mctable/easybiz`)
  - core (infrastructure)
  - features (feature modules)
  - example feature template
- Dependency Injection (Koin)
- Build & run
- Testing
- Conventions & guidelines
- Useful paths
- Next steps / todo

---

About

EasyBiz is a Kotlin Multiplatform project using Compose Multiplatform for shared UI and a native iOS app entry. The project aims to follow Clean Architecture inside the shared `commonMain` sources.

Current project layout (high-level)
- Root: `EasyBiz/`
- Shared Compose module: `composeApp/`
  - `composeApp/src/commonMain/kotlin/` for common code
  - `composeApp/src/androidMain/` for Android-specific code
  - `composeApp/src/iosMain/` for iOS-specific code
- iOS app entry: `iosApp/`

Note: this README assumes future work will place the core and features packages under `composeApp/src/commonMain/kotlin/com/mctable/easybiz` (or the project's base package).

Planned Clean Architecture (recommended layout)

Inside the shared `commonMain` package (recommended root: `com.mctable.easybiz`) the code will be organized by two top-level packages: `core` and `features`.

- core
  - networking — HTTP clients, API definitions, serializers, API result wrappers
  - navigation — multiplatform navigation contracts, routes and platform adapters
  - ds (design system) — shared design system components, UI tokens, themes, shared Compose primitives and common styles
  - di — Koin modules and DI bootstrap
  - utils — shared helpers, extensions, mappers, date/time helpers, constants

- features
  - featureName/
    - data — repository implementations, datasource implementations, network models, mappers
    - domain — domain models, repository interfaces, use-cases / interactors
    - presentation — UI state holders, view models/controllers, Composables (shared UI logic)

Example folder tree (recommended)

composeApp/src/commonMain/kotlin/com/mctable/easybiz/
├─ core/
│  ├─ networking/
│  ├─ navigation/
│  ├─ ds/
│  ├─ di/
│  └─ utils/
└─ features/
   └─ someFeature/
      ├─ data/
      ├─ domain/
      └─ presentation/

Example feature template (minimal contract)
- domain/
  - model/ — domain models
  - repository/ — repository interfaces
  - usecase/ — one or more use-cases that expose domain operations

- data/
  - datasource/ — remote/local datasources
  - repository/ — concrete implementations of domain repository interfaces
  - remote/ — network models and mappers

- presentation/
  - ui/ — Composables and screens
  - viewmodel/ — shared viewmodels or state holders
  - navigation/ — feature-specific navigation helpers

Dependency Injection

A Koin bootstrap is already present in the project. The recommended pattern:
- Keep DI modules in `core/di`.
- Provide one Koin module per feature (located in the feature package) and register them from the central DI bootstrap.

Current DI helper found in this repo:
- `composeApp/src/commonMain/kotlin/com/mctable/easybiz/core/di/KoinApp.kt` — exposes `initProjectKoin(config: KoinAppDeclaration? = null)`
- Android app initializes DI in `composeApp/src/androidMain/kotlin/com/mctable/easybiz/core/application/EasyBizApplication.kt`.

When adding features, export a Koin module from the feature (for example `someFeatureModule`) and include it when calling `initProjectKoin` in the platform entrypoints.

Build & run

From the repo root you can run Gradle tasks. Example commands:

- Build everything:

```bash
./gradlew build
```

- Build the Android app (assemble debug):

```bash
./gradlew :composeApp:assembleDebug
```

- Install the Android debug APK to a connected device/emulator:

```bash
./gradlew :composeApp:installDebug
```

- iOS: open `iosApp/` in Xcode and run the `iosApp` target.

Testing

- Common tests and platform tests live under `composeApp/src/commonTest/`, `composeApp/src/androidTest/`, etc.
- Run unit tests:

```bash
./gradlew test
```

Conventions & guidelines (recommended)

- Keep `core` purely infrastructural and feature-agnostic.
- Features own their `data/domain/presentation` subpackages and do not leak implementation details to other features.
- Domain layer contains pure business logic and abstract repository interfaces.
- Data layer implements the repository interfaces and depends on `core` infra (networking, ds).
- Presentation layer depends only on `domain` and `core/navigation` (for routing), and should expose UI state to platform-specific entrypoints.
- Use interfaces for repository contracts. Prefer immutability for domain models.
- Keep Compose UI for shared screens in `commonMain` when possible; platform specifics go to `androidMain`/`iosMain`.

Useful paths (project-specific)
- DI bootstrap (common): `composeApp/src/commonMain/kotlin/com/mctable/easybiz/core/di/KoinApp.kt`
- Android Application class: `composeApp/src/androidMain/kotlin/com/mctable/easybiz/core/application/EasyBizApplication.kt`
- Compose common sources: `composeApp/src/commonMain/kotlin/`
- Android manifest: `composeApp/src/androidMain/AndroidManifest.xml`
- iOS app entry: `iosApp/iosApp/`

License & contribution
- TODO

---

This README aims to be the canonical starting point for organizing shared, multiplatform code under a Clean Architecture pattern. Update it as the codebase evolves.
