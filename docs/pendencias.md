# Pendências para o fluxo estar funcional

> Análise feita em **2026-09-10**, a partir do estado do repositório na branch `master` (commit `b7cbb7a`).
> Documento para consulta futura — as evidências citam `arquivo:linha` para conferência.
>
> **Atualização (2026-09-10):** [#10](https://github.com/odiegoalessandro/doanet-api/issues/10) Logs de
> auditoria implementados (seções 1 e 3 revisadas).

Este documento cruza o que o `README.md` promete com o que existe de fato no código, e registra os
pontos que impedem o fluxo principal (cadastrar doador/ponto/ONG → geolocalizar → doar → solicitar retirada)
de funcionar de ponta a ponta.

## Issues abertas (rastreamento)

| Issue | Tema |
| --- | --- |
| [#3](https://github.com/odiegoalessandro/doanet-api/issues/3) | Autenticação (Spring Security + JWT) e hash de senha |
| [#4](https://github.com/odiegoalessandro/doanet-api/issues/4) | Ligar a geolocalização ao fluxo de cadastro |
| [#5](https://github.com/odiegoalessandro/doanet-api/issues/5) | Corrigir coordenadas erradas do OpenCage |
| [#6](https://github.com/odiegoalessandro/doanet-api/issues/6) | Expor lat/lng e endpoint de pontos próximos |
| [#7](https://github.com/odiegoalessandro/doanet-api/issues/7) | Estoque visível para ONGs + notificações mín/máx |
| [#8](https://github.com/odiegoalessandro/doanet-api/issues/8) | Relatórios de movimentações mensais |
| [#9](https://github.com/odiegoalessandro/doanet-api/issues/9) | Painel de controle para gestores |
| [#10](https://github.com/odiegoalessandro/doanet-api/issues/10) | Logs de auditoria |
| [#11](https://github.com/odiegoalessandro/doanet-api/issues/11) | Criptografia em trânsito e repouso |
| [#12](https://github.com/odiegoalessandro/doanet-api/issues/12) | TODOs: CPF/CNPJ e validade por lote |
| [#13](https://github.com/odiegoalessandro/doanet-api/issues/13) | Testes não exercitam as migrations (Flyway off) |

---

## 1. Autenticação e autorização — **não começou**

O README promete "Autenticação (JWT)", "Integração com Spring Security" e "Validação de identidade"
(`README.md:67-69`), mas não há nada disso no projeto.

**Evidências:**

- `pom.xml:32-99` — não há `spring-boot-starter-security`, nem nenhuma lib de JWT (jjwt / nimbus / java-jwt).
- Não existe `SecurityConfig`, filtro de autenticação, `UserDetailsService` ou endpoint de login/token.
- Não existe `UserController` — a pasta `infra/controller` tem controllers para Donor, Ong, DonationPoint,
  Item, Donation e Request, mas nenhum para User/autenticação.
- **Senha é gravada em texto puro**, apesar do nome da coluna ser `password_hash`:
  - `infra/persistence/UserEntity.java:29-31` mapeia o campo `password` direto para a coluna `password_hash`.
  - `infra/gateways/UserEntityMapper.java:14` e `:34` apenas repassam a string, sem hash.
  - `CreateDonorUseCase.java:22`, `CreateOngUseCase.java:22` e `CreateDonationPointUseCase.java:22` passam
    `command.password()` cru para o domínio.
  - Não há bean de `PasswordEncoder` em lugar nenhum.
  - A única validação é `User.validatePassword` (`domain/entities/user/User.java:157-159`), que só exige
    tamanho mínimo de 6 caracteres.

**Consequência:** qualquer usuário pode chamar todos os endpoints; não há noção de "quem está logado",
nem de dono do recurso. As rotas de `PATCH`/`disable` estão abertas.

**Impacto nos logs de auditoria ([#10](https://github.com/odiegoalessandro/doanet-api/issues/10)):**
a auditoria registra ação, entidade, timestamp e valores antes/depois, mas o `author_id` ainda permanece
nulo. A autenticação já existe ([#3](https://github.com/odiegoalessandro/doanet-api/issues/3), PR #16),
porém o `RecordAuditUseCase` ainda não lê o usuário do contexto de segurança — falta essa ligação
(ver `TODO (#3)` em `application/usecases/audit/RecordAuditUseCase.java`).

---

## 2. Geolocalização — **implementada, mas desconectada do fluxo + com bug**

Esta é a parte que o README chama de "Visualização de pontos próximos (geolocalização)" (`README.md:36`).

### 2.1 A integração existe, mas nunca é chamada

- A única implementação é `infra/gateways/OpenCageGetCoordinatesByAddressImpl.java`, que chama a API
  externa OpenCage (`:39-43`).
- O único ponto que usa essa integração é `CreateUserUseCase.execute` (`application/usecases/user/CreateUserUseCase.java:17-24`),
  que pega o endereço, busca as coordenadas e salva lat/lng.
- **Porém nenhum fluxo de cadastro chama o `CreateUserUseCase`:**
  - `CreateDonorUseCase.java:5` importa `CreateUserUseCase` mas nunca o utiliza; monta o `User` com
    `latitude=null` e `longitude=null` (`:30-31`).
  - `CreateOngUseCase.java:5,30-31` — mesmo caso.
  - `CreateDonationPointUseCase.java:5,30-31` — mesmo caso.
  - `config/DonorConfig.java:16` injeta `CreateUserUseCase` e constrói o use case sem ele (`:17`).
  - `config/OngConfig.java:16-17` — mesmo padrão.
  - `config/DonationPointConfig.java:15` sequer injeta o `CreateUserUseCase`.
  - `config/UserConfig.java:15-20` cria o bean, mas nada chama `execute` (não há `UserController`).

**Consequência:** todo usuário cadastrado (doador, ONG, ponto) fica com `latitude` e `longitude` nulos.
A geolocalização, na prática, não roda.

### 2.2 Por que a geolocalização sai errada

Mesmo que o `CreateUserUseCase` fosse ligado, a chamada ao OpenCage tem problemas que explicam coordenadas
incorretas:

- **Falta restringir o país.** A URL (`OpenCageGetCoordinatesByAddressImpl.java:39-43`) manda só `q` e `key`.
  Não usa `countrycode=br`, nem `bounds`, nem `language`. O OpenCage resolve nomes de rua
  livremente pelo mundo — uma rua "das Flores" pode cair em Portugal ou nos EUA.
- **Ordem do endereço fora do padrão de geocoder.** `UserEntity.buildAddress()` (`:73-83`) monta
  `rua, número, bairro, cidade, estado, Brazil, CEP`. O esperado por geocoders é o inverso
  (país primeiro ou campos estruturados). Isso aumenta a chance de match ruim.
- **Falha silenciosa vira (0,0).** `parseCoordinates` (`:53-69`) usa `geometry.path("lat").asDouble()` e
  `path("lng").asDouble()` (`:62-63`). Se o campo não vier, o Jackson devolve `0.0` sem erro — o
  cadastro grava latitude/longitude `0,0` (Golfo da Guiné) em vez de falhar.
- **Não usa os sinais de confiança do OpenCage.** O retorno traz `confidence` e `components`
  (país/estado/cidade), que não são lidos. Resultados de baixa confiança passam como se fossem bons.
- **Sem timeout.** `config/RestTemplateConfig.java:11-13` cria um `RestTemplate` sem timeouts de conexão/leitura.
  Uma chamada travada no OpenCage pode segurar a requisição de cadastro indefinidamente.
- **Dependência de API externa com limite de uso.** Plano free do OpenCage tem rate limit e a chave vem de
  `OPENCAGE_API_KEY` (`application.properties`); em teste é `dummy-key-for-tests`
  (`application-test.properties`).

### 2.3 As coordenadas nem sequer são expostas

- `infra/controller/UserDto.java:6-18` não tem `latitude` nem `longitude`. Mesmo que fossem calculadas,
  o cliente não as receberia.
- Não existe endpoint de "pontos próximos": não há cálculo de distância, raio ou ordenação por proximidade
  em nenhum repositório/use case.

---

## 3. Funcionalidades prometidas no README x estado real

| Promessa (README) | Status | Observação |
| --- | --- | --- |
| Cadastro de doadores / pontos / ONGs (`:21-23`) | **Parcial** | CRUD existe, mas sem geolocalização, sem senha com hash e sem auth. |
| Registro de doações (tipo, quantidade, destino) (`:24`) | **Parcial** | Endpoints de donation/request existem; não há visão de estoque. |
| Mapa interativo com pontos de coleta (`:25`) | **Não começou** | Backend only; não há endpoint geoespacial. |
| Notificações de estoque mínimo/máximo (`:26`) | **Não começou** | Grep por `minimum`/`maximum`/`notif`/`estoque` em `src/main` não retorna nada. |
| Relatórios de movimentações mensais (`:27`) | **Não começou** | Nenhum código de relatório/agregação. |
| Visualização de pontos próximos (`:36`) | **Não começou** | Depende de 2.1/2.3. |
| Estoque visível para ONGs (`:37`) | **Não começou** | Não há entidade/consulta de estoque consolidado. |
| Solicitação de retirada de alimentos (`:38`) | **Parcial** | Rota `request` existe, mas sem dono/autenticação. |
| Painel de controle para gestores (`:39`) | **Não começou** | Nenhum endpoint de painel/perfil de gestor. |
| Interface web/mobile responsiva (`:44`) | **Não começou** | Este repositório é só a API. |
| Tempo de resposta < 2s (`:45`) | **Não verificado** | Sem testes de carga/métrica. |
| Acessibilidade WCAG 2.1 (`:46`) | **N/A** | Requisito de front-end. |
| Criptografia em trânsito e repouso (`:47`) | **Não começou** | Sem config de TLS; senha em texto puro (ver seção 1). |
| Suporte a 500+ usuários simultâneos (`:48`) | **Não verificado** | Sem teste de concorrência. |
| Logs de auditoria (`:49`) | **Parcial** | Implementado na [#10](https://github.com/odiegoalessandro/doanet-api/issues/10): tabela `audit_log` (`V10`) + log estruturado para cadastros, mudanças de status de doação/solicitação e desativações. Autor fica nulo até o `RecordAuditUseCase` ser ligado ao usuário autenticado ([#3](https://github.com/odiegoalessandro/doanet-api/issues/3)); política de retenção ainda não definida. |

---

## 4. Outros débitos técnicos registrados no código

- `domain/entities/donor/Donor.java:9` — `// TODO: no futuro será necessario criar uma abordagem melhor para os documentos(CPF, CNPJ)`.
- `domain/entities/item/Item.java:11` — `// TODO: devemos mover essas informações para o DonationItem, assim teremos controle da validade pelo lote`
  (validade/estoque por lote ainda não existe).
- **Testes não cobrem integração real:** o perfil de teste (`application-test.properties`) usa H2 e
  **desabilita Flyway**, então as migrations não são exercitadas nos testes.
- **Migrations com histórico confuso:** `V3` cria tabelas de item/donation mas está nomeada
  "create-order-table"; `V7` criou a coluna `stater` por engano em `request`, corrigida em `V8`.
  Vale revisar antes de qualquer deploy novo.

---

## 5. Resumo sugerido de prioridade

1. **Autenticação** (Spring Security + hash de senha + JWT + proteger rotas) — bloqueia "quem é o dono"
   de qualquer recurso e a validação de identidade.
2. **Ligar e corrigir a geolocalização** — chamar o `CreateUserUseCase` nos cadastros, restringir
   `countrycode=br`, tratar resultado ausente/baixa confiança em vez de virar `(0,0)`, adicionar timeout,
   expor lat/lng no DTO e criar o endpoint de pontos próximos.
3. **Estoque e notificações** (mín/máx) e **relatórios mensais**.
4. **Painel de gestores**. *(Logs de auditoria implementados na [#10](https://github.com/odiegoalessandro/doanet-api/issues/10).)*
5. **Front-end** (mapa interativo, responsividade, WCAG) — fora do escopo deste repositório.
