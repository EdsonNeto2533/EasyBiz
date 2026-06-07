# EasyBiz — Contratos de API (Backend)

**Base URL:** `https://api.easybiz.app.br`  
**Swagger:** `https://api.easybiz.app.br/swagger-ui/index.html`  
**Auth:** `Authorization: Bearer {accessToken}` em todas as rotas autenticadas

---

## Auth

| Método | Rota | Auth | Body | Response |
|---|---|---|---|---|
| POST | `/auth/login` | ❌ | `{ email, password }` | `{ accessToken, refreshToken, ... }` |
| POST | `/usuarios` | ❌ | `{ email, password, name, registerToken }` | `{ accessToken, ... }` |
| POST | `/auth/logout` | ✅ | `{ refreshToken }` | 200 |
| DELETE | `/usuarios/me` | ✅ | — | 204 |
| POST | `/auth/esqueci-senha` | ❌ | `{ email }` | 200 |
| POST | `/auth/redefinir-senha` | ❌ | `{ email, code, password }` | 200 |
| POST | `/auth/verificar-codigo-cadastro` | ❌ | `{ email, code }` | `{ accessToken, ... }` |
| POST | `/auth/enviar-codigo-cadastro` | ❌ | `{ email }` | 200 |
| POST | `/auth/verificar-email` | ❌ | `{ email, code }` | 200 |
| POST | `/auth/reenviar-verificacao` | ❌ | `{ email }` | 200 |

---

## Usuário

| Método | Rota | Auth | Body | Response |
|---|---|---|---|---|
| PUT | `/usuarios/me/fcm-token` | ✅ | `{ fcmToken }` | 204 |

---

## Negócios (Business)

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| GET | `/negocios` | ✅ | Buscar prestadores (com lat, lng, filtros) |
| GET | `/negocios/{id}` | ✅ | Detalhes do negócio |
| POST | `/negocios` | ✅ | Cadastrar negócio |
| PUT | `/negocios/{id}` | ✅ | Atualizar negócio |
| GET | `/negocios/me` | ✅ | Meu negócio |
| POST | `/negocios/{id}/favoritar` | ✅ | Favoritar negócio |
| DELETE | `/negocios/{id}/favoritar` | ✅ | Desfavoritar negócio |
| GET | `/negocios/favoritos` | ✅ | Meus favoritos |
| GET | `/negocios/{id}/midias` | ✅ | Portfólio de mídia |
| POST | `/negocios/{id}/midias` | ✅ | Upload de mídia (multipart) |
| DELETE | `/negocios/{id}/midias/{midiaId}` | ✅ | Remover mídia |

---

## Pedidos (Orders)

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/pedidos` | ✅ | Criar pedido |
| GET | `/pedidos` | ✅ | Listar pedidos (paper: CLIENTE/PRESTADOR) |
| PUT | `/pedidos/{id}/aceitar` | ✅ | Aceitar pedido |
| PUT | `/pedidos/{id}/recusar` | ✅ | Recusar pedido |
| PUT | `/pedidos/{id}/cancelar` | ✅ | Cancelar pedido |
| PUT | `/pedidos/{id}/concluir` | ✅ | Concluir pedido |

---

## Chat (WebSocket STOMP)

Conexão via STOMP sobre WebSocket (`SOCKET_HOST`).

| Tópico | Direção | Descrição |
|---|---|---|
| `/app/chat/{orderId}` | enviar | Enviar mensagem |
| `/topic/chat/{orderId}` | receber | Receber mensagens |
| `/app/chat/{orderId}/lido` | enviar | Marcar como lido |
| `/app/chat/{orderId}/digitando` | enviar | Status digitando |

---

## Avaliações

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/avaliacoes` | ✅ | Criar avaliação |
| GET | `/avaliacoes/{orderId}` | ✅ | Buscar avaliações do pedido |