package my.playground.merchantapi.product;

import static my.playground.merchantapi.product.ProductMockFactory.newProductEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import my.playground.merchantapi.entity.ProductEntity;
import my.playground.merchantapi.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock
  private ProductRepository productRepository;

  @InjectMocks
  private ProductService productService;

  @Test
  public void shouldReturnListOfProducts() {
    List<ProductEntity> productEntities = List.of(newProductEntity("product1", "description1"),
        newProductEntity("product2", "description2"));
    when(productRepository.findAll()).thenReturn(productEntities);

    List<Product> products = productService.getAllProducts();
    assertEquals(2, products.size());
  }

  @Test
  public void shouldReturnProduct() {
    Long productId = 3L;
    when(productRepository.findById(productId)).thenReturn(
        Optional.of(newProductEntity("product1", "description1")));

    Optional<Product> maybeProduct = productService.getProductById(productId);
    assertTrue(maybeProduct.isPresent());
    assertEquals("product1", maybeProduct.get().productName());
  }

  @Test
  public void shouldThrowExceptionWhenProductNotFound() {
    Long productId = 3L;
    when(productRepository.findById(productId)).thenReturn(Optional.empty());

    Optional<Product> maybeProduct = productService.getProductById(productId);
    assertTrue(maybeProduct.isEmpty());
  }

  @Test
  public void shouldAddProduct() {
    AddProductRequest addProductRequest = new AddProductRequest(1L, 2L, "Product1", "best ever!",
        BigDecimal.valueOf(100.0), 100, "test.jpg");
    when(productRepository.save(any(ProductEntity.class))).thenReturn(
        newProductEntity("Product1", "description"));

    Product product = productService.addProduct(addProductRequest);
    assertNotNull(product);
    assertEquals("Product1", product.productName());
  }
}
