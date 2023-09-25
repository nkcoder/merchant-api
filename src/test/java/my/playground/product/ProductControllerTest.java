package my.playground.product;

import io.restassured.http.ContentType;
import my.playground.IntegrationBaseTest;
import my.playground.persistence.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;

import static io.restassured.RestAssured.given;
import static my.playground.product.ProductMockFactory.newProductEntityForSave;
import static org.hamcrest.Matchers.equalTo;

public class ProductControllerTest extends IntegrationBaseTest {

  @Autowired
  private ProductRepository productRepository;

  @Test
  public void getAllProducts_ShouldReturnListOfProducts() {
    productRepository.saveAll(List.of(
        newProductEntityForSave("product1", "description1"),
        newProductEntityForSave("product2", "description2")
    ));

    given()
        .header("Authorization", generateJwtToken())
        .when()
        .get("/products")
        .then()
        .statusCode(HttpStatus.OK.value());
  }

  @Test
  public void getProductById_ShouldReturnProduct() {
    Long productId = productRepository.save(newProductEntityForSave("product1", "description1"))
        .getId();

    given()
        .header("Authorization", generateJwtToken())
        .when()
        .get("/products/" + productId)
        .then()
        .statusCode(HttpStatus.OK.value())
        .body("productName", equalTo("product1"));
  }

  @Test
  public void addProduct_ShouldReturnProduct() {
    AddProductRequest addProductRequest = new AddProductRequest(
        1L, "Product1", "best ever!", BigDecimal.valueOf(100.0), 100
    );

    given()
        .header("Authorization", generateJwtToken())
        .body(addProductRequest)
        .contentType(ContentType.JSON)
        .post("/products")
        .then()
        .statusCode(HttpStatus.CREATED.value())
        .body("productName", equalTo("Product1"))
        .body("price", equalTo(100.0F));
  }

  @Test
  public void addProduct_ShouldThrowBadRequestWhenNameIsInvalid() {
    AddProductRequest addProductRequest = new AddProductRequest(
        1L, "p1", "best ever!", BigDecimal.valueOf(100.0), 100);

    given()
        .header("Authorization", generateJwtToken())
        .body(addProductRequest)
        .contentType(ContentType.JSON)
        .post("/products")
        .then()
        .statusCode(HttpStatus.BAD_REQUEST.value());
  }

}
