package lab.controllers;

import lab.entity.Product;
import lab.service.JDBCProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
class ProductController {
    @Autowired
    private JDBCProductDAO repository;

    /* get all */
    @GetMapping("/products")
    List<Product> getAll() {
        return repository.getAll();
    }

    /* find by id */
    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id) {
        return repository.getById(id);
    }

    /* find by name */
    @GetMapping(value = "/products", params = {"name"})
    ArrayList<Product> findByName(@RequestParam("name") String name) {
        List<Product> all = repository.getAll();
        ArrayList<Product> selected = new ArrayList<>();
        for (Product p : all) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                selected.add(p);
            }
        }
        return selected;
    }

    @PutMapping("/products/{id}")
    Product updateProduct(@RequestBody Product newProduct) {
        repository.update(newProduct);
        return repository.getById(newProduct.getId());
    }

    @PostMapping(value = "/products", params = {"name", "cost"})
    Product newProduct(@RequestParam("name") String name, @RequestParam("cost") Integer cost) {
        Product product = new Product();
        product.setId((long) (Math.random() * 100 * LocalTime.now().getNano() * LocalTime.now().getHour() * LocalTime.now().getSecond()));
        product.setCost(cost);
        product.setName(name);
        repository.create(product);
        return repository.getById(product.getId());
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.delete(repository.getById(id));
    }

}