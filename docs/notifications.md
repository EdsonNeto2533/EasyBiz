# EasyBiz — Push Notifications (FCM)

## Setup Android

1. Baixar `google-services.json` do Firebase Console
2. Colocar em `composeApp/google-services.json`
3. Package do app: `com.mctable.easybiz`
4. Railway já possui: `FIREBASE_CREDENTIALS_JSON`, `FIREBASE_ENABLED`

## Registro do Token

Chamar após login e sempre que `onNewToken()` for disparado pelo Firebase SDK.

```
PUT /usuarios/me/fcm-token
Authorization: Bearer {accessToken}
Content-Type: application/json

{ "fcmToken": "string" }

→ 204 No Content
```

## Estrutura do Payload FCM

O backend deve enviar no campo **`data`** (não `notification`) para garantir que `onMessageReceived` seja sempre chamado:

```json
{
  "title": "Título da notificação",
  "body": "Corpo da mensagem",
  "type": "<tipo>",
  "<campo_adicional>": "<valor>"
}
```

## Tipos de Deeplink (campo `type`)

| type | campos adicionais | tela de destino |
|---|---|---|
| `search_business` | — | SearchBusiness |
| `business_details` | `id` | BusinessDetails(id) |
| `chat` | `orderId` | Chat(orderId) |
| `my_orders` | `paper` (CLIENTE/PRESTADOR), `businessId` | MyOrders |
| `reviews` | `orderId`, `businessId` | Reviews |
| `profile` | — | Profile |
| `my_business` | — | MyBusiness |
| `my_favorites` | — | MyFavorites |

### Exemplos por evento

**Novo pedido (notifica prestador):**
```json
{ "title": "Novo pedido", "body": "Você recebeu um novo pedido", "type": "chat", "orderId": "uuid" }
```

**Pedido aceito (notifica cliente):**
```json
{ "title": "Pedido aceito", "body": "Seu pedido foi aceito", "type": "my_orders", "paper": "CLIENTE", "businessId": "uuid" }
```

**Pedido recusado (notifica cliente):**
```json
{ "title": "Pedido recusado", "body": "Seu pedido foi recusado", "type": "my_orders", "paper": "CLIENTE", "businessId": "uuid" }
```

**Pedido cancelado (notifica prestador):**
```json
{ "title": "Pedido cancelado", "body": "O cliente cancelou o pedido", "type": "my_orders", "paper": "PRESTADOR", "businessId": "uuid" }
```

## Arquivos da Feature (Frontend)

```
composeApp/src/
  commonMain/core/notification/
    DeeplinkResolver.kt                        — mapeia payload → Destination
    PendingDeeplinkHolder.kt                   — fila StateFlow de navegação pendente
    data/
      request/RegisterFcmTokenRequest.kt       — body { fcmToken }
      datasource/NotificationRemoteDataSource.kt
    domain/
      usecase/RegisterFcmTokenUseCase.kt
    di/
      NotificationModule.kt

  androidMain/core/notification/
    EasyBizFirebaseMessagingService.kt         — recebe FCM, registra token, exibe notificação
```

## Fluxo completo

```
1. Login → Firebase.getToken() → onNewToken() → PUT /usuarios/me/fcm-token
2. Backend evento → Firebase Admin SDK → FCM push
3. App em background: sistema exibe notificação
4. Usuário toca → MainActivity.onCreate(intent) → handleNotificationIntent()
5. DeeplinkResolver.resolve(data) → PendingDeeplinkHolder.set(destination)
6. AppNavHost LaunchedEffect → currentDestination.isLoggedArea → navigator.navigate(destination)
```