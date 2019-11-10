package lab.controllers;

import lab.entity.Product;
import lab.service.JDBCProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class ProductController {
    @Autowired
    private JDBCProductDAO repository;

    @GetMapping("/products")
    List<Product> all() {
        return repository.getAll();
    }


    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id) {

        return repository.getById(id);
    }

    // put raw json in request body like
    //    {
    //        "id": 111,
    //            "phone": "09716682755555",
    //            "address": "Poltava",
    //            "name": "Bohdan"
    //    }

    @PutMapping("/products/{id}")
    Product updateProduct(@RequestBody Product newProduct) {
        repository.update(newProduct);
        return repository.getById(newProduct.getId());
    }

    @PostMapping("/products")
    Product newProduct(@RequestBody Product newProduct) {
        repository.create(newProduct);
        return repository.getById(newProduct.getId());
    }

    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id) {
        repository.delete(repository.getById(id));
    }

}