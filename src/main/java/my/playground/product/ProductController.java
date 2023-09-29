package my.playground.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Products")
@RestController
@RequestMapping("/v1/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("")
  public ResponseEntity<List<Product>> getAllProducts(
      @RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber,
      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    List<Product> allProducts = productService.getAllProducts(pageNumber, pageSize);
    return new ResponseEntity<>(allProducts, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
    Product product = productService.getProductById(id)
        .orElseThrow(() -> AppException.from(HttpStatus.NOT_FOUND, "Product not found: " + id));
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Product> addProduct(
      @RequestBody @Valid AddProductRequest addProductRequest) {
    return new ResponseEntity<>(productService.addProduct(addProductRequest), HttpStatus.CREATED);
  }

}
