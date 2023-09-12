> This is a playground project just for refreshing my knowledge on Java tech stack.

## Online Shop (simple) backend APIs

## OpenAPI document

- [OpenAPI doc](http://localhost:8080/v3/api-docs)

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
  - ![API](./swagger-ui.png)

### Tech Stack

- [x] Java 17
- [x] SpringBoot 3 (Spring 6)
- [x] Spring Security
- [x] Flyway
- [x] JWT
- [x] Dockerise
- [x] OpenAPI 3


### APIs

- [ ] Users
  - [x] register
  - [x] update
  - [ ] delete

- [ ] Products
  - [x] create a new product
  - [ ] update a product
  - [ ] delete a product
  - [x] find a product
  - [x] find all products

- [ ] Orders
  - [ ] create an order
  - [ ] get an order
  - [ ] update and order

- [ ] Auth
  - [x] login

### TODO

- [x] /auto scripts
- [x] dockerise
- [x] CI (Github Actions)
- [ ] Refactor Controller tests from integration tests to unit tests
- [ ] Refactor exception handling
- [ ] introduce VAVR and refactor in a functional programming way
- [ ] encrypt the database secret
- [ ] support GraphQL
