# Descrição Técnica do Sistema de Gerenciamento Escolar

## 1 Introdução

O presente projeto consiste no desenvolvimento de um **Sistema de Gerenciamento Escolar**, implementado na linguagem **Java**, com utilização da biblioteca **Swing** para construção da interface gráfica e do banco de dados **SQLite** para persistência das informações. O sistema foi desenvolvido com finalidade acadêmica, no contexto da disciplina de **Paradigmas de Programação**, visando à aplicação prática de conceitos fundamentais da **Programação Orientada a Objetos**, bem como à organização do código em camadas com responsabilidades bem definidas.

O sistema permite o gerenciamento de informações relacionadas a **alunos, professores, turmas e notas**, oferecendo uma interface gráfica que facilita a interação do usuário com os dados armazenados no banco de dados.

---

## 2 Arquitetura e Organização do Projeto

A arquitetura do sistema foi estruturada de forma modular, com separação do código em pacotes, garantindo **manutenibilidade, legibilidade e reutilização**. Essa organização segue o princípio da separação de responsabilidades, fundamental no desenvolvimento de software orientado a objetos.

---

## 3 Camada Utilitária

### 3.1 Pacote `util`

O pacote `util` contém classes de apoio ao funcionamento geral do sistema.

#### 3.1.1 Classe `Conexao`

A classe `Conexao` é responsável por estabelecer e gerenciar a conexão com o banco de dados SQLite por meio da API **JDBC (Java Database Connectivity)**. Essa abordagem centraliza a lógica de acesso ao banco, evitando duplicação de código e facilitando a manutenção do sistema.

As principais vantagens dessa abordagem incluem:
- Padronização do acesso ao banco de dados;
- Redução do acoplamento entre as camadas;
- Facilidade para alterações futuras no sistema gerenciador de banco de dados.

---

## 4 Inicialização do Banco de Dados

### 4.1 Pacote `setup`

O pacote `setup` é responsável pela configuração inicial do banco de dados.

#### 4.1.1 Classe `DatabaseSetup`

A classe `DatabaseSetup` tem como finalidade verificar a existência das tabelas necessárias ao funcionamento do sistema e criá-las automaticamente, caso ainda não existam. Dessa forma, garante-se que o sistema possa ser executado corretamente desde a primeira inicialização, sem a necessidade de intervenção manual.

As tabelas representam as principais entidades do sistema, tais como:
- Aluno;
- Professor;
- Turma;
- Nota.

---

## 5 Camada de Interface Gráfica

### 5.1 Pacote `view`

O pacote `view` concentra todas as classes responsáveis pela **interface gráfica do usuário**, desenvolvida com Java Swing. Cada classe corresponde a uma tela específica do sistema, seguindo uma abordagem orientada a eventos.

### 5.1.1 Classe `Main`

A classe `Main` representa o ponto de entrada da aplicação. Suas responsabilidades incluem:
- Inicialização do banco de dados;
- Configuração inicial do sistema;
- Exibição da interface principal ao usuário.

---

### 5.1.2 Classe `MenuPrincipal`

Responsável pela navegação principal do sistema, permitindo o acesso às funcionalidades de cadastro e listagem.

---

### 5.1.3 Classe `TelaHome`

Tela inicial do sistema, responsável por apresentar uma interface de boas-vindas e direcionar o usuário às demais funcionalidades.

---

### 5.1.4 Classe `TelaAluno`

Responsável pelo cadastro e edição dos dados dos alunos, permitindo a inserção e atualização das informações persistidas no banco de dados.

---

### 5.1.5 Classe `TelaListagemAluno`

Responsável pela exibição dos alunos cadastrados, facilitando a visualização e o gerenciamento dos registros.

---

### 5.1.6 Classe `TelaProfessor`

Responsável pelo cadastro e gerenciamento das informações dos professores.

---

### 5.1.7 Classe `TelaTurma`

Responsável pelo cadastro das turmas e pela associação destas com os professores.

---

### 5.1.8 Classe `TelaListagemTurma`

Responsável pela visualização geral das turmas cadastradas no sistema.

---

### 5.1.9 Classe `TelaCadastroNota`

Responsável pelo lançamento das notas dos alunos, estabelecendo a relação entre aluno, turma e professor.

---

### 5.1.10 Classe `TelaSelecaoAlunoVinculo`

Tela auxiliar utilizada para seleção de alunos durante o processo de vínculo com turmas.

---

## 6 Banco de Dados

O sistema utiliza o **SQLite** como sistema gerenciador de banco de dados, armazenado localmente no arquivo `database.db`. A comunicação entre a aplicação e o banco é realizada por meio do JDBC, garantindo a persistência e integridade dos dados.

---

## 7 Observação Importante sobre a Inicialização do Banco de Dados

A presente observação mostra-se pertinente em razão dos **testes realizados a partir dos arquivos compactados (.zip) do projeto**, nos quais foram identificadas falhas relacionadas à inicialização e ao acesso ao banco de dados SQLite. Durante esses testes, o banco de dados apresentou erros associados à criação, conexão ou localização do arquivo `database.db`, o que resultou na interrupção da execução da aplicação e na geração de exceções em tempo de execução.

Diante desse cenário, o projeto disponibiliza um arquivo específico para a configuração manual da camada de persistência. O arquivo referido nesta seção corresponde à classe Java **`DatabaseSetup.java`**, localizada no pacote **`setup`**. Essa classe é responsável por realizar a **inicialização do banco de dados SQLite**, executando instruções SQL que criam o arquivo do banco e definem todas as tabelas necessárias ao funcionamento do sistema, conforme o modelo de dados adotado.

A execução da classe `DatabaseSetup.java` garante a criação adequada das estruturas de persistência, incluindo entidades, chaves primárias e relacionamentos, mitigando os erros observados durante os testes com os arquivos compactados. Recomenda-se, portanto, sua utilização sempre que ocorrer falha na criação automática do banco, exclusão acidental do arquivo `database.db` ou problemas na primeira inicialização da aplicação.

## 8 Considerações Finais

O desenvolvimento deste Sistema de Gerenciamento Escolar possibilitou a aplicação prática dos conceitos de **Programação Orientada a Objetos**, bem como o uso integrado de **Java Swing** e **SQLite** para construção de aplicações desktop. A estrutura modular adotada contribui para a clareza, organização e manutenção do código, atendendo aos objetivos propostos pela disciplina de **Paradigmas de Programação**.

