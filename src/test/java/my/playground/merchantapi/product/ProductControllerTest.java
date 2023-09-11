package my.playground.merchantapi.product;

import static my.playground.merchantapi.product.ProductMockFactory.newProduct;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import my.playground.merchantapi.infrastructure.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JsonUtil jsonUtil;

  @MockBean
  private ProductService productService;

  @Test
  public void getAllProducts_ShouldReturnListOfProducts() throws Exception {
    List<Product> mockProducts = List.of(
        newProduct("Product1", "Description1"),
        newProduct("Product2", "Description2")
    );
    when(productService.getAllProducts()).thenReturn(mockProducts);

    mockMvc.perform(get("/products"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].productName").value("Product1"))
        .andExpect(jsonPath("$[1].productName").value("Product2"));
  }

  @Test
  public void getProductById_ShouldReturnProduct() throws Exception {
    Product mockProduct = newProduct("Product1", "Description1");
    when(productService.getProductById(1L)).thenReturn(Optional.of(mockProduct));

    mockMvc.perform(get("/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.productName").value("Product1"));
  }

  @Test
  public void getProductById_ShouldThrowBadRequest() throws Exception {
    when(productService.getProductById(1L)).thenReturn(Optional.empty());

    mockMvc.perform(get("/products/1"))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void addProduct_ShouldReturnProduct() throws Exception {
    AddProductRequest addProductRequest = new AddProductRequest(
        1L, 2L, "Product1", "best ever!", BigDecimal.valueOf(100.0), 100, "test.jpg"
    );
    Product mockProduct = newProduct("Product1", "best ever product");
    when(productService.addProduct(any(AddProductRequest.class))).thenReturn(mockProduct);

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUtil.toJson(addProductRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.productName").value("Product1"));
  }

  @Test
  public void addProduct_ShouldThrowBadRequestWhenNameIsInvalid() throws Exception {
    AddProductRequest addProductRequest = new AddProductRequest(
        1L, 2L, "p1", "best ever!", BigDecimal.valueOf(100.0), 100, "test.jpg"
    );
    Product mockProduct = newProduct("Product1", "best ever product");
    when(productService.addProduct(any(AddProductRequest.class))).thenReturn(mockProduct);

    mockMvc.perform(post("/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(jsonUtil.toJson(addProductRequest)))
        .andExpect(status().isBadRequest());
  }

}
