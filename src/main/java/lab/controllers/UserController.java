package lab.controllers;

import lab.entity.User;
import lab.service.JDBCUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
class UserController {
    @Autowired
    private JDBCUserDAO repository;

    @GetMapping("/users")
    List<User> all() {
        return repository.getAll();
    }


    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {

        return repository.getById(id);
    }

    // put raw json in request body like
    //    {
    //        "id": 111,
    //            "phone": "09716682755555",
    //            "address": "Poltava",
    //            "name": "Bohdan"
    //    }

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser) {
        repository.update(newUser);
        return repository.getById(newUser.getId());
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.delete(repository.getById(id));
    }

}