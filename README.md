# 📚 BIBLIFOR — Sistema de Biblioteca em Microsserviços

> Sistema de gerenciamento de biblioteca universitária desenvolvido com arquitetura de microsserviços, inspirado no funcionamento da biblioteca da Unifor.

---

## 👥 Integrantes do Grupo

| Nome | Responsabilidade |
|------|-----------------|
| Pedro Artur | Serviço de Livros (`servico-livros`) + Interface Web + Banco de Dados |
| Andrei | Serviço de Usuários e Empréstimos (`servico-usuarios`) |

---


> ⚠️ Substituir pelos links reais após criar os repositórios no GitHub.

---

## 📐 Arquitetura do Sistema

```
┌─────────────────────────────────────────────────┐
│              Interface Web (index.html)          │
│         Conecta os dois serviços via JS          │
└──────────────────┬──────────────────┬────────────┘
                   │                  │
       ┌───────────▼──────┐  ┌────────▼──────────┐
       │  servico-livros  │  │ servico-usuarios   │
       │   porta 8081     │  │    porta 8082      │
       │                  │  │                    │
       │  GET  /livros    │◄─┤ (consome livros    │
       │  POST /livros    │  │  via RestTemplate) │
       │  PATCH /livros   │  │                    │
       │                  │  │  GET  /usuarios    │
       │  Banco H2:       │  │  POST /usuarios    │
       │  livros-db       │  │  GET  /emprestimos │
       └──────────────────┘  │  POST /emprestimos │
                             │                    │
                             │  Banco H2:         │
                             │  usuarios-db       │
                             └────────────────────┘
```

**Padrão Provedor/Consumidor:**
- `servico-livros` atua como **provedor** — expõe a API REST de livros
- `servico-usuarios` atua como **consumidor** — chama a API do `servico-livros` ao realizar e devolver empréstimos

---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Uso |
|------------|--------|-----|
| Java | 17 | Linguagem principal |
| Spring Boot | 3.2.0 | Framework REST |
| Spring Data JPA | 3.2.0 | Persistência de dados |
| H2 Database | Embutido | Banco de dados de cada serviço |
| Apache HttpClient5 | — | Suporte ao método HTTP PATCH |
| Maven | 3.9+ | Gerenciador de dependências |
| HTML + JavaScript | — | Interface web |


---

## ✅ Pré-requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- **Java JDK 17+** → [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)
- **Apache Maven 3.9+** → [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)

Para verificar se estão instalados, abra o terminal e execute:

```bash
java -version
mvn -version
```

---

## ▶️ Como Rodar o Projeto

> ⚠️ Os dois serviços precisam rodar **ao mesmo tempo**, cada um em um terminal separado.

### Terminal 1 — Serviço de Livros

```bash
cd biblifor/servico-livros
mvn spring-boot:run
```

Aguarde a mensagem:
```
Started ServicoLivrosApplication on port 8081
```

### Terminal 2 — Serviço de Usuários e Empréstimos

Abra um **novo terminal** (no VS Code: clique no `+` ao lado do terminal) e execute:

```bash
cd biblifor/servico-usuarios
mvn spring-boot:run
```

Aguarde a mensagem:
```
Started ServicoUsuariosApplication on port 8082
```

### Passo 3 — Abrir a Interface Web

Com os dois serviços rodando, abra o arquivo abaixo diretamente no navegador:

```
biblifor/interface-web/index.html
```

> Basta dar duplo clique no arquivo `index.html` — ele abrirá no seu navegador padrão.

---

## 🗄️ Banco de Dados H2

Cada microsserviço possui seu **próprio banco de dados H2** independente, salvo em arquivo (os dados persistem mesmo após reiniciar).

---

### 🔵 Banco do Serviço de Livros

**Acesse o console em:** `http://localhost:8081/h2-console`

| Campo | Valor |
|-------|-------|
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:file:./data/livros-db` |
| User Name | `sa` |
| Password | *(deixe vazio)* |

**Tabelas disponíveis:**
```sql
-- Ver todos os livros
SELECT * FROM LIVROS;

-- Ver livros disponíveis
SELECT * FROM LIVROS WHERE DISPONIVEL = TRUE;

-- Ver livros emprestados
SELECT * FROM LIVROS WHERE DISPONIVEL = FALSE;
```

---

### 🟢 Banco do Serviço de Usuários

**Acesse o console em:** `http://localhost:8082/h2-console`

| Campo | Valor |
|-------|-------|
| Driver Class | `org.h2.Driver` |
| JDBC URL | `jdbc:h2:file:./data/usuarios-db` |
| User Name | `sa` |
| Password | *(deixe vazio)* |

**Tabelas disponíveis:**
```sql
-- Ver todos os usuários
SELECT * FROM USUARIOS;

-- Ver todos os empréstimos
SELECT * FROM EMPRESTIMOS;

-- Ver apenas empréstimos ativos
SELECT * FROM EMPRESTIMOS WHERE ATIVO = TRUE;

-- Ver empréstimos de um usuário específico
SELECT * FROM EMPRESTIMOS WHERE USUARIO_ID = 1;
```

> ℹ️ Os arquivos de banco são criados automaticamente na pasta `data/` dentro de cada serviço na primeira execução.

---

## 📋 Definição dos Serviços

### Serviço de Livros
Responsável pelo gerenciamento do acervo da biblioteca.

**Funcionalidades:**
- Cadastrar livro (título e autor)
- Listar todos os livros com status de disponibilidade
- Buscar livro por ID
- Atualizar disponibilidade (usado internamente pelo serviço de empréstimos)
- Remover livro do acervo

---

### Serviço de Usuários
Responsável pelo gerenciamento dos usuários do sistema.

**Funcionalidades:**
- Cadastrar usuário (nome e e-mail)
- Listar todos os usuários
- Buscar usuário por ID com histórico de empréstimos
- Remover usuário

---

### Serviço de Empréstimos
Integrado ao Serviço de Usuários (porta 8082). Responsável pelo controle de empréstimos.

**Funcionalidades:**
- Realizar empréstimo (valida disponibilidade no serviço de livros)
- Devolver livro (libera o livro no serviço de livros)
- Listar todos os empréstimos com status (ativo/devolvido)
- Data de devolução calculada automaticamente (+7 dias)

---

## 📝 Observações Finais

- Os dados **persistem** entre execuções graças ao banco H2 em arquivo
- A interface web consome os dois serviços diretamente via JavaScript (Fetch API)
- Os serviços são **independentes** — cada um tem seu próprio banco, porta e repositório Git
- O `servico-livros` deve ser iniciado **antes** do `servico-usuarios`
