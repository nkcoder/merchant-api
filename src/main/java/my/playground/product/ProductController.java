package my.playground.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @GetMapping("")
  public ResponseEntity<List<Product>> getAllProducts() {
    List<Product> allProducts = productService.getAllProducts();
    return new ResponseEntity<>(allProducts, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
    Product product = productService.getProductById(id)
        .orElseThrow(() -> new ProductNotFoundException("product not found: " + id));
    return new ResponseEntity<>(product, HttpStatus.OK);
  }

  @PostMapping("")
  public ResponseEntity<Product> addProduct(
      @RequestBody @Valid AddProductRequest addProductRequest) {
    return new ResponseEntity<>(productService.addProduct(addProductRequest), HttpStatus.CREATED);
  }

}
