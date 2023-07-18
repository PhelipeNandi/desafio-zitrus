# Desafio Zitrus

Você é o desenvolvedor responsável por um sistema de controle de autorizações de
procedimentos médicos para um plano de saúde. Os critérios para permitir a execução de um
procedimento são idade e sexo, de acordo com a tabela a seguir:

| PROCEDIMENTO  | IDADE | SEXO | PERMITIDO | 
| :------------ |:-----:|-----:|-----------|
| 1234|  10   |    M | NÃO       |
| 4567|  20   |    M | SIM       |
| 6789|  10   |    F | NÃO       |
| 6789|  10   |    M | SIM       |
| 1234|  20   |    M | SIM       |
| 4567|  30   |    F | SIM       |

Procedimentos não listados na tabela devem ser negados no cadastro, com mensagens de
retorno da requisição, justificados.

## Objetivo
Através de um Webapp JSP (executável em servidor de aplicação Jboss -
preferencialmente), crie os registros iniciais das regras de autorização e das solicitações de
autorização, com chamadas via Servlets e utilização de página web para demais cadastros e
consulta de autorizações.

## Tecnologias

- Java 8
- Servlet
- Maven
- Docker (MySQL)
- Liquibase
- Jboss
- Intellij

## Estrutura do Banco de Dados

- Procedimentos: Tabela que armazena os procedimentos médicos
- Regra: Tabela que armazena as regras de autorização dos procedimentos médicos
- Solicitacoes: Tabela que armazena os cadastros de solicitações de procedimentos médicos

## Como Funciona?

Ao cadastar uma solicitação de procedimento médico, o sistema apresenta um formulário onde o paciente deve informar seu nome, procedimento, idade e sexo. Após isso o sistema valida se o procedimento, idade e sexo existe na tabela de regra, caso exista, a solicitação é dada como autorizada.

## Instruções para executar a aplicação localmente

Para executar a aplicação verique antes se você possui instalado o [Docker](https://www.docker.com/), [Maven](https://www.apache.org/), [Intellij](https://www.jetbrains.com/pt-br/idea/) e [JBoss Wildfly](https://download.jboss.org/wildfly/24.0.1.Final/wildfly-24.0.1.Final.zip).

```
# Clone este repositório
$ git clone https://github.com/PhelipeNandi/desafio-zitrus.git
$ git clone git@github.com:PhelipeNandi/desafio-zitrus.git

# Acesse a pasta do projeto no seu terminal/cmd
$ cd desafio-zitrus

# Execute o maven para o download das dependências e build do projeto
$ mvn clean install -U
```

Agora, abra seu Intellij e vá em File > Settings e procure por Build, Execution, Deployment > Application Servers.

Clique no + e depois em JBoss/WildFly Server, especifique o caminho do JBoss/WildFly e clique em OK e a seguir em Apply.

Após isso vá em Run > Edit Configurations e clique em + e adicione um Jboss/Wildfly Server Local.

Na aba Server configure a URL para ser: `http://localhost:8080/desafio-zitrus/solicitacao` e por fim vá na aba Deployment e adicione o Artifact desafio-zitrus:war exploded.

Para a configuração do Docker(MySQL), basta você editar o arquivo docker-compose na linha de volumes, alterando o path `C:\Users\pheli\Documents\Phelipe\Projetos` para uma pasta de seu computador.

Agora basta rodar o JBoss/Wildfly Server que a aplicação irá iniciar.