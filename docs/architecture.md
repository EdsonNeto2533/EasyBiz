# EasyBiz — Arquitetura

## Stack

| Camada | Tecnologia |
|---|---|
| Frontend | Kotlin Multiplatform (Android + iOS) |
| UI | Compose Multiplatform 1.10.0 |
| DI | Koin 4.1.1 |
| HTTP | Ktor 3.4.0 |
| WebSocket | Krossbow STOMP 9.3.0 |
| Imagens | Coil 3.3.0 |
| Storage | DataStore (Android) / UserDefaults (iOS) |
| Localização | Moko Geo 0.8.0 |
| Backend | Spring Boot |
| Notificações | Firebase Cloud Messaging (FCM) |

## Padrão de Feature (Clean Architecture)

Toda feature segue exatamente essa estrutura:

```
features/{nome}/
  data/
    datasource/         — interface + impl (usa EasyBizNetworking)
    mapper/             — parse JSON → entidade de domínio
    model/              — response models (@Serializable)
    request/            — request bodies (@Serializable)
    repository/         — implementação do repositório
  domain/
    entity/             — modelos de domínio puros
    repository/         — interface do repositório
    usecase/            — interface + impl (sempre Result<T>)
  presentation/
    event/              — sealed class de eventos do usuário
    state/              — data class de estado da UI
    ui/                 — Composables (page, organisms, molecules)
    view_model/         — ViewModel com koinViewModel
  di/
    {Nome}Module.kt     — módulo Koin da feature
```

Após criar o módulo, registrar em `core/di/KoinApp.kt`.

## EasyBizNetworking — métodos disponíveis

```kotlin
networking.get(host, path, headers, params, responseMapper)
networking.post(host, path, body, headers, params, responseMapper)
networking.put(host, path, body, headers, params, responseMapper)
networking.delete(host, path, headers, params, responseMapper)
networking.patch(host, path, body, headers, params, responseMapper)
```

Usar sempre `appEnv.host` como base URL.

## DI — Módulos registrados (KoinApp.kt)

```
coreModule, notificationModule, authModule, platformModule,
searchBusinessModule, businessDetailsModule, registerBusinessModule,
businessMediaModule, myBusinessModule, myFavoriteModule,
myOrderModule, orderChatModule, userDataModule, reviewModule
```

## Single Activity

O projeto usa Single Activity Pattern (`MainActivity`). Área logada e pública coexistem na mesma Activity, separadas pelo `isLoggedArea` de cada `Destination`.