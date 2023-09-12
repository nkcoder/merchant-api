package my.playground.onlineshop.product;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import my.playground.onlineshop.persistence.ProductRepository;
import my.playground.onlineshop.persistence.entity.ProductEntity;
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
    ProductEntity newProduct = new ProductEntity(addProductRequest.sellerId(),
        addProductRequest.categoryId(), addProductRequest.productName(),
        addProductRequest.description(), addProductRequest.price(),
        addProductRequest.stockQuantity(), addProductRequest.imageURL());
    ProductEntity savedProduct = productRepository.save(newProduct);
    return new Product(savedProduct.getProductId(), savedProduct.getSellerId(),
        savedProduct.getCategoryId(), savedProduct.getProductName(), savedProduct.getDescription(),
        savedProduct.getPrice(), savedProduct.getStockQuantity(), savedProduct.getImageURL());
  }

  public Product updateProduct(Long productId, Product product) {
    ProductEntity updatedProduct = productRepository.findById(productId).map(pe -> {
      ProductEntity newProduct = new ProductEntity(product.sellerId(), product.categoryId(),
          product.productName(), product.description(), product.price(), product.stockQuantity(),
          product.imageURL());
      newProduct.setProductId(pe.getProductId());
      return newProduct;
    }).orElseThrow(() -> new ProductNotFoundException("productId: " + productId));

    ProductEntity savedProduct = productRepository.save(updatedProduct);
    return new Product(savedProduct.getProductId(), savedProduct.getSellerId(),
        savedProduct.getCategoryId(), savedProduct.getProductName(), savedProduct.getDescription(),
        savedProduct.getPrice(), savedProduct.getStockQuantity(), savedProduct.getImageURL());
  }
}
