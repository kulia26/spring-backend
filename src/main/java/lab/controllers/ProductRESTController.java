package lab.controllers;

import lab.entity.Category;
import lab.entity.Product;
import lab.repository.CategoryRepository;
import lab.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProductRESTController {
  @Autowired
  private ProductRepository repository;

  @Autowired
  private CategoryRepository categoryRepository;

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

  //@PostMapping("/products")
  //Product createOrSaveProduct(@RequestBody Product newProduct) {
  // return repository.save(newProduct);
  //}

  @Secured("ROLE_ADMIN")
  @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  Product registerUser(@RequestParam("name") String name,
                       @RequestParam("cost") String cost,
                       @RequestParam("description") String desc,
                       @RequestParam("category") String category,
                       @RequestParam("image") MultipartFile image) {
    Product newProduct = new Product();
    newProduct.setName(name);
    newProduct.setCost(Integer.parseInt(cost));
    newProduct.setDescription(desc);
    System.out.println("category1: " + category);
    Category cat = categoryRepository.findByName(category).get(0);

    System.out.println("category: " + cat.toString());
    newProduct.setCategory(cat);
    try {
      byte[] imageBytes = image.getBytes();
      newProduct.setImage(imageBytes);
    } catch (Exception e) {
      return null;
    }
    return repository.save(newProduct);
  }

  @GetMapping(value = "/products/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  byte[] getImageByProductId(@PathVariable Long id) {
    return repository.findById(id).get().getImage();
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