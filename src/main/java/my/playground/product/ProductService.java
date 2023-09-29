package my.playground.product;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import my.playground.persistence.ProductRepository;
import my.playground.persistence.entity.ProductEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> getAllProducts(int pageNumber, int pageSize) {
    return Lists.newArrayList(productRepository.findAll(PageRequest.of(pageNumber, pageSize)))
        .stream().map(
            pe -> new Product(pe.getId(), pe.getSellerId(), pe.getProductName(),
                pe.getDescription(),
                pe.getPrice(), pe.getQuantity())).toList();
  }

  public Optional<Product> getProductById(Long productId) {
    return productRepository.findById(productId).map(
        pe -> new Product(pe.getId(), pe.getSellerId(), pe.getProductName(), pe.getDescription(),
            pe.getPrice(), pe.getQuantity()));
  }

  public List<Product> getProducts(List<Long> productIds) {
    return Lists.newArrayList(productRepository.findAllById(productIds)).stream().map(
        pe -> new Product(pe.getId(), pe.getSellerId(), pe.getProductName(), pe.getDescription(),
            pe.getPrice(), pe.getQuantity())).collect(Collectors.toList());
  }

  public Product addProduct(AddProductRequest addProductRequest) {
    ProductEntity newProduct = ProductEntity.builder().sellerId(addProductRequest.sellerId())
        .productName(addProductRequest.productName()).description(addProductRequest.description())
        .price(addProductRequest.price()).quantity(addProductRequest.quantity()).build();
    ProductEntity savedProduct = productRepository.save(newProduct);
    return new Product(savedProduct.getId(), savedProduct.getSellerId(),
        savedProduct.getProductName(), savedProduct.getDescription(), savedProduct.getPrice(),
        savedProduct.getQuantity());
  }

  public Product updateProduct(Long productId, Product product) {
    ProductEntity updatedProduct = productRepository.findById(productId).map(
        pe -> ProductEntity.builder().id(productId).sellerId(product.sellerId())
            .productName(product.productName()).description(product.description())
            .price(product.price()).quantity(product.quantity()).build()).orElseThrow(
        () -> AppException.from(HttpStatus.NOT_FOUND, "Product not found: " + productId));

    ProductEntity savedProduct = productRepository.save(updatedProduct);
    return new Product(savedProduct.getId(), savedProduct.getSellerId(),
        savedProduct.getProductName(), savedProduct.getDescription(), savedProduct.getPrice(),
        savedProduct.getQuantity());
  }
}
