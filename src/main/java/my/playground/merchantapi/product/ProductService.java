package my.playground.merchantapi.product;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import my.playground.merchantapi.entity.ProductEntity;
import my.playground.merchantapi.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> getAllProducts() {
    return Lists.newArrayList(productRepository.findAll()).stream().map(
        pe -> new Product(pe.getProductId(), pe.getSellerId(), pe.getCategoryId(),
            pe.getProductName(), pe.getDescription(), pe.getPrice(), pe.getStockQuantity(),
            pe.getImageURL())).collect(Collectors.toList());
  }

  public Optional<Product> getProductById(Long productId) {
    return productRepository.findById(productId).map(
        pe -> new Product(pe.getProductId(), pe.getSellerId(), pe.getCategoryId(),
            pe.getProductName(), pe.getDescription(), pe.getPrice(), pe.getStockQuantity(),
            pe.getImageURL()));
  }

  public Product addProduct(AddProductRequest addProductRequest) {
    ProductEntity newProduct = new ProductEntity(
        addProductRequest.sellerId(),
        addProductRequest.categoryId(),
        addProductRequest.productName(),
        addProductRequest.description(),
        addProductRequest.price(),
        addProductRequest.stockQuantity(),
        addProductRequest.imageUR()
    );
    ProductEntity savedProduct = productRepository.save(newProduct);
    return new Product(
        savedProduct.getProductId(),
        savedProduct.getSellerId(),
        savedProduct.getCategoryId(),
        savedProduct.getProductName(),
        savedProduct.getDescription(),
        savedProduct.getPrice(),
        savedProduct.getStockQuantity(),
        savedProduct.getImageURL()
    );
  }
}
