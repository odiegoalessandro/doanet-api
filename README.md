# Banco de Alimentos - Sistema de Doações Solidárias

## 📌 Visão Geral

Protótipo de um sistema online que conecta doadores (pessoas físicas ou jurídicas), pontos de coleta e ONGs, promovendo o combate à fome e à insegurança alimentar por meio de uma rede digital eficiente, confiável e acessível.

---

## 🎯 Objetivo

Reduzir o desperdício de alimentos e combater a fome usando tecnologia para conectar:

- Doadores → Pessoas ou empresas
- Pontos solidários → Escolas/universidades que recebem doações
- ONGs → Distribuem os alimentos a famílias vulneráveis

---

## 🧩 Principais Funcionalidades

- Cadastro de doadores (CPF/CNPJ)
- Cadastro de pontos solidários (nome, local, responsável)
- Cadastro de ONGs receptoras (documentação válida)
- Registro de doações (tipo, quantidade, destino)
- Mapa interativo com pontos de coleta
- Notificações de estoque mínimo/máximo
- Relatórios de movimentações mensais

---

## 🧠 Requisitos

### Funcionais

- Cadastro de usuários (doador, ONG, ponto)
- Visualização de pontos próximos (geolocalização)
- Estoque visível para ONGs
- Solicitação de retirada de alimentos
- Painel de controle para gestores

### Não-funcionais

- Interface web/mobile (responsiva)
- Tempo de resposta < 2s
- Acessibilidade (WCAG 2.1)
- Criptografia em trânsito e repouso
- Suporte a 500+ usuários simultâneos
- Logs de auditoria

---

## 👥 Stakeholders

- Cidadãos e empresas doadoras
- Escolas e universidades (pontos)
- ONGs receptoras
- Administradores do sistema
- Comunidade atendida

---

## 🛠 Tecnologias

### Backend
- **Java + Spring Boot**
    - APIs RESTful
    - Autenticação (JWT)
    - Integração com Spring Security
    - Validação de identidade

### Banco de Dados
- **PostgreSQL**
- ORM: **Hibernate**