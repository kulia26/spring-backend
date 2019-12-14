package lab.controllers;

import lab.entity.Product;
import lab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductRESTController {
  @Autowired
  private ProductRepository repository;

  public ProductRepository getRepository() {
    return repository;
  }

  public void setRepository(ProductRepository repository) {
    this.repository = repository;
  }

  @GetMapping(value = "/products")
  public List<Product> getAllProducts() {
    return repository.findAll();
  }

  @PostMapping("/products")
  Product createOrSaveProduct(@RequestBody Product newProduct) {
    return repository.save(newProduct);
  }

  @GetMapping("/products/{id}")
  Product getProductById(@PathVariable Long id) {
    return repository.findById(id).get();
  }

  @PutMapping("/products/{id}")
  Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {

    return repository.findById(id).map(product -> {
      product.setName(newProduct.getName());
      product.setCost(newProduct.getCost());
      return repository.save(product);
    }).orElseGet(() -> {
      newProduct.setId(id);
      return repository.save(newProduct);
    });
  }

  @DeleteMapping("/products/{id}")
  void deleteProduct(@PathVariable Long id) {
    repository.deleteById(id);
  }

}