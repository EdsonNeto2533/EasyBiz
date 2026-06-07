# EasyBiz — Navegação

## Destinations

Arquivo: `composeApp/src/commonMain/kotlin/com/mctable/easybiz/core/navigation/Destination.kt`

### Área Pública (`isLoggedArea = false`)

| Destination | Parâmetros | Como navegar |
|---|---|---|
| `Login` | — | `Destination.Login` |
| `VerifyEmail` | email, name, password | `Destination.VerifyEmail(email, name, password)` |
| `ForgetPassword` | — | `Destination.ForgetPassword` |
| `ResetPassword` | email | `Destination.ResetPassword(email)` |
| `ConfirmEmail` | email | `Destination.ConfirmEmail(email)` |

### Área Logada (`isLoggedArea = true`)

| Destination | Parâmetros | Como navegar |
|---|---|---|
| `SearchBusiness` | — | `Destination.SearchBusiness` |
| `BusinessDetails` | id: String | `Destination.BusinessDetails(id)` |
| `RegisterBusiness` | — | `Destination.RegisterBusiness` |
| `UpdateBusiness` | id: String | `Destination.UpdateBusiness(id)` |
| `BusinessMedia` | id: String | `Destination.BusinessMedia(id)` |
| `MyBusiness` | — | `Destination.MyBusiness` |
| `MyFavorites` | — | `Destination.MyFavorites` |
| `MyOrders` | paper: String?, businessId: String? | `Destination.MyOrders(paper, businessId)` |
| `Chat` | orderId: String | `Destination.Chat(orderId)` |
| `Reviews` | orderId: String, businessId: String | `Destination.Reviews(orderId, businessId)` |
| `Profile` | — | `Destination.Profile` |

## Navigator

Interface: `core/navigation/Navigator.kt`  
Implementação: `NavigatorImpl` — singleton no Koin

```kotlin
navigator.navigate(destination)                          // navegar
navigator.navigate(destination, clearBackStack = true)   // limpar pilha
navigator.pop()                                          // voltar
navigator.popTo(destination, inclusive = false)          // voltar até destino
navigator.popUpTo(destination, inclusive = true)         // pop + navegar (limpa tudo)
```

> O NavController é anexado via `SideEffect { navigator.attach(navController) }` no `AppNavHost`.  
> Navegação chamada antes do attach é ignorada silenciosamente — usar `PendingDeeplinkHolder` para notificações.

## Drawer (NavDrawer)

Destinations visíveis no menu lateral (`drawerDestinations`):
- SearchBusiness, RegisterBusiness, MyBusiness, MyFavorites, MyOrders, Profile

O drawer fecha automaticamente quando `currentDestination.isLoggedArea = false`.

## Deep Links por Notificação

Ver `docs/notifications.md` para o mapeamento completo de `type` → `Destination`.