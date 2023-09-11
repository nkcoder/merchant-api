package my.playground.merchantapi.product;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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

}
