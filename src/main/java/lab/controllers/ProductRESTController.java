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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

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

  @Secured("ROLE_ADMIN")
  @PostMapping(value = "/products", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  Product registerUser(@RequestParam("name") String name,
                       @RequestParam("cost") String cost,
                       @RequestParam("description") String desc,
                       @RequestParam("category") String category,
                       @RequestParam("image") MultipartFile image) {
    Product newProduct = new Product();
    newProduct.setName(name);
    newProduct.setCost(parseInt(cost));
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


  @GetMapping("/findProducts")
  @ResponseBody
  public List<Product> findProducts(@RequestParam String query) {
    List<Product> all = repository.findAll();
    ArrayList<Product> finded = new ArrayList<Product>();
    for (Product p : all) {
      if (p.getName().toLowerCase().contains(query.toLowerCase())) {
        finded.add(p);
      } else {
        if (p.getDescription().toLowerCase().contains(query.toLowerCase())) {
          finded.add(p);
        }
      }
    }
    return finded;
  }


  @Secured("ROLE_ADMIN")
  @PutMapping(value = "/products/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  Product registerUser(@PathVariable Long id,
                       @RequestParam("name") String name,
                       @RequestParam("cost") String cost,
                       @RequestParam("description") String desc,
                       @RequestParam("category") String category,
                       @RequestParam("image") Optional<MultipartFile> image) {

    return repository.findById(id).map(product -> {
      product.setName(name);
      product.setCost(parseInt(cost));
      product.setDescription(desc);
      Category cat = categoryRepository.findByName(category).get(0);
      product.setCategory(cat);
      if (image.isPresent()) {
        try {
          byte[] imageBytes = image.get().getBytes();
          product.setImage(imageBytes);
        } catch (Exception e) {
          return null;
        }
      }
      return repository.save(product);
    }).orElseGet(() -> {
      return null;
    });
  }

  @Secured("ROLE_ADMIN")
  @DeleteMapping("/products/{id}")
  void deleteProduct(@PathVariable Long id) {
    repository.deleteById(id);
  }

  @GetMapping("/products/{id}")
  Optional<Product> findProduct(@PathVariable Long id) {
    return repository.findById(id);
  }

}