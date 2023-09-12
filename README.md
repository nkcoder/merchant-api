> This is a playground project just for refreshing my knowledge on Java tech stack.

## Online Shop (simplified) backend APIs

## OpenAPI document

- [OpenAPI doc](http://localhost:8080/v3/api-docs)

- [Swagger UI](http://localhost:8080/swagger-ui/index.html)
  - ![API](./swagger-ui.png)
  - missing `/auth/login` in Swagger: login to get JWT token

### Tech Stack

- [x] Java 17
- [x] SpringBoot 3 (Spring 6)
- [x] Spring Security
- [x] Flyway
- [x] JWT
- [x] Dockerise
- [x] OpenAPI 3

### TODO

- [ ] Users API
  - [ ] delete
- [ ] Products
  - [ ] update a product
  - [ ] delete a product

- [ ] Orders
  - [ ] create an order
  - [ ] get an order
  - [ ] get all orders of a user
  - [ ] update order status
  - [ ] cancel an order

- [x] /auto scripts
- [x] dockerise
- [x] CI (Github Actions)
- [ ] Refactor Controller tests from integration tests to unit tests
- [ ] Refactor exception handling
- [ ] introduce VAVR and refactor in a functional programming way
- [ ] encrypt the database secret
- [ ] support GraphQL
- [ ] API versions
- [ ] Transactions, notifications, idempotency, stock management
