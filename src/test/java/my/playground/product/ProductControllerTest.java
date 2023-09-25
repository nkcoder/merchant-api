package my.playground.product;

import com.google.common.collect.Lists;
import io.restassured.http.ContentType;
import my.playground.IntegrationBaseTest;
import my.playground.persistence.ProductRepository;
import my.playground.persistence.entity.ProductEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static my.playground.product.ProductMockFactory.newProductEntityForSave;
import static org.hamcrest.Matchers.equalTo;

public class ProductControllerTest extends IntegrationBaseTest {

  @Autowired
  private ProductRepository productRepository;

  private final List<Long> productIdsCreated = new ArrayList<>();

  @AfterEach
  public void teardown() {
    productRepository.deleteAllById(productIdsCreated);
    userRepository.deleteById(userId);
  }

  @Test
  public void getAllProducts_ShouldReturnListOfProducts() {
    Iterable<ProductEntity> productsSaved = productRepository.saveAll(List.of(
        newProductEntityForSave("product1", "description1"),
        newProductEntityForSave("product2", "description2")
    ));
    productIdsCreated.addAll(Lists.newArrayList(productsSaved).stream().map(ProductEntity::getId).toList());

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
    productIdsCreated.add(productId);

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
