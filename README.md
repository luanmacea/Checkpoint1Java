# ClinicApp

Aplicacao Spring Boot para gerenciamento de pacientes, medicos e consultas de uma clinica. O projeto fornece uma API REST completa, com persistencia em banco de dados MySQL, documentacao automatizada via Swagger e validacoes de regras de negocio.

## Requisitos

- Java 17+
- Maven 3.9+
- MySQL 8 (ou compativel)

## Configuracao do banco

1. Crie um banco de dados chamado `clinicadb` no MySQL (ou outro nome de sua preferencia).
2. Atualize as credenciais em `src/main/resources/application.properties` ou exporte variaveis de ambiente antes de subir a aplicacao:
   ```bash
   set DB_URL=jdbc:mysql://localhost:3306/clinicadb
   set DB_USERNAME=usuario
   set DB_PASSWORD=senha
   ```
3. As tabelas sao criadas automaticamente gracas a configuracao `spring.jpa.hibernate.ddl-auto=update`.

## Como executar

```bash
mvn spring-boot:run
```

A API fica disponivel em `http://localhost:8080`.

## Documentacao Swagger

Apos iniciar o projeto, acesse `http://localhost:8080/swagger-ui.html` para explorar e testar os endpoints. O JSON da documentacao pode ser baixado em `http://localhost:8080/v3/api-docs`.

## Endpoints principais

### Pacientes

| Metodo | Endpoint              | Descricao                            |
| ------ | --------------------- | ------------------------------------ |
| GET    | `/api/pacientes`      | Lista todos os pacientes             |
| GET    | `/api/pacientes/{id}` | Busca paciente por ID                |
| POST   | `/api/pacientes`      | Cadastra novo paciente               |
| PUT    | `/api/pacientes/{id}` | Atualiza os dados de um paciente     |
| DELETE | `/api/pacientes/{id}` | Remove um paciente                   |

Payload de exemplo (POST/PUT):
```json
{
  "nome": "Maria da Silva",
  "cpf": "12345678901",
  "email": "maria@clinicapp.com",
  "telefone": "11999999999",
  "dataNascimento": "1990-05-12"
}
```

### Medicos

| Metodo | Endpoint            | Descricao                        |
| ------ | ------------------- | -------------------------------- |
| GET    | `/api/medicos`      | Lista todos os medicos           |
| GET    | `/api/medicos/{id}` | Busca medico por ID              |
| POST   | `/api/medicos`      | Cadastra novo medico             |
| PUT    | `/api/medicos/{id}` | Atualiza os dados de um medico   |
| DELETE | `/api/medicos/{id}` | Remove um medico                 |

Payload de exemplo (POST/PUT):
```json
{
  "nome": "Dr. Joao Pereira",
  "crm": "CRM12345",
  "especialidade": "Cardiologia",
  "email": "joao@clinicapp.com",
  "telefone": "11988887777"
}
```

### Consultas

| Metodo | Endpoint               | Descricao                                                                 |
| ------ | ---------------------- | ------------------------------------------------------------------------- |
| GET    | `/api/consultas`       | Lista consultas; aceita `medicoId` e/ou `pacienteId` como filtros         |
| GET    | `/api/consultas/{id}`  | Busca consulta por ID                                                     |
| POST   | `/api/consultas`       | Agenda nova consulta                                                      |
| PUT    | `/api/consultas/{id}`  | Atualiza dados de uma consulta (reagenda, altera status ou motivo)        |
| DELETE | `/api/consultas/{id}`  | Cancela consulta (remove o registro do banco)                             |

Payload de exemplo (POST/PUT):
```json
{
  "pacienteId": 1,
  "medicoId": 2,
  "dataConsulta": "2025-09-22T14:30:00",
  "motivo": "Retorno de avaliacao",
  "status": "AGENDADA"
}
```

Se o campo `status` nao for informado, o valor padrao `AGENDADA` sera utilizado.

### Modelo de erro

```json
{
  "timestamp": "2025-09-22T22:30:04.1234567Z",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "O medico ja possui uma consulta marcada neste horario"
}
```

## Estrutura de pacotes

```
com.luan.clinica.clinicapp
 |- config          # Configuracao do Swagger/OpenAPI
 |- controller      # Camada REST
 |- dto             # Objetos de transporte (entrada e saida)
 |- exception       # Tratamento centralizado de erros
 |- model           # Entidades JPA
 |  \- enums        # Enumeracoes compartilhadas
 |- repository      # Interfaces JPA/Hibernate
 \- service         # Regras de negocio e orquestracao
```

## Evolucoes sugeridas

| Melhoria                                                   | Status        |
| --------------------------------------------------------- | ------------- |
| Validacao de conflito de agenda para medico e paciente    | Implementado  |
| Filtros na listagem de consultas por medico e/ou paciente | Implementado  |
| Autenticacao com JWT para proteger endpoints sensiveis    | Sugerido      |

Ideias adicionais: envio de notificacoes de consulta por e-mail/SMS, auditoria de alteracoes e prontuario historico dos pacientes.

## Testes

Os testes de contexto (`mvn test`) exigem um banco configurado e acessivel. Caso o banco nao esteja disponivel, a inicializacao do Spring Boot falhara. Execute os testes apenas apos configurar as variaveis de ambiente ou editar `application.properties`.

## Proximos passos

- Popular o banco com dados iniciais (data.sql ou scripts Flyway)
- Adicionar autenticacao/autorizacao
- Implementar notificacoes automaticas aos pacientes
