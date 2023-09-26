package my.playground.product;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import my.playground.infrastructure.exception.AppException;
import my.playground.persistence.ProductRepository;
import my.playground.persistence.entity.ProductEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<Product> getAllProducts() {
    return Lists.newArrayList(productRepository.findAll()).stream().map(
        pe -> new Product(pe.getId(), pe.getSellerId(), pe.getProductName(),
            pe.getDescription(), pe.getPrice(), pe.getQuantity())).collect(Collectors.toList());
  }

  public Optional<Product> getProductById(Long productId) {
    return productRepository.findById(productId).map(
        pe -> new Product(pe.getId(), pe.getSellerId(), pe.getProductName(),
            pe.getDescription(), pe.getPrice(), pe.getQuantity()));
  }

  public List<Product> getProducts(List<Long> productIds) {
    return Lists.newArrayList(productRepository.findAllById(productIds)).stream().map(pe ->
        new Product(pe.getId(), pe.getSellerId(), pe.getProductName(),
            pe.getDescription(), pe.getPrice(), pe.getQuantity())
    ).collect(Collectors.toList());
  }

  public Product addProduct(AddProductRequest addProductRequest) {
    ProductEntity newProduct = new ProductEntity(addProductRequest.sellerId(),
        addProductRequest.productName(), addProductRequest.description(), addProductRequest.price(),
        addProductRequest.quantity());
    ProductEntity savedProduct = productRepository.save(newProduct);
    return new Product(savedProduct.getId(), savedProduct.getSellerId(),
        savedProduct.getProductName(), savedProduct.getDescription(), savedProduct.getPrice(),
        savedProduct.getQuantity());
  }

  public Product updateProduct(Long productId, Product product) {
    ProductEntity updatedProduct = productRepository.findById(productId).map(pe -> {
      ProductEntity newProduct = new ProductEntity(product.sellerId(), product.productName(),
          product.description(), product.price(), product.quantity());
      newProduct.setId(pe.getId());
      return newProduct;
    }).orElseThrow(() -> AppException.from(HttpStatus.NOT_FOUND, "Product not found: " + productId));

    ProductEntity savedProduct = productRepository.save(updatedProduct);
    return new Product(savedProduct.getId(), savedProduct.getSellerId(),
        savedProduct.getProductName(), savedProduct.getDescription(), savedProduct.getPrice(),
        savedProduct.getQuantity());
  }
}
