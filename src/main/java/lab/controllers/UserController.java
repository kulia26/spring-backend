package lab.controllers;

import lab.entity.User;
import lab.service.JDBCUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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

    @PutMapping("/users/{id}")
    User updateUser(@RequestBody User newUser) {
        repository.update(newUser);
        return repository.getById(newUser.getId());
    }

    @PostMapping(value = "/users", params = {"name", "address", "phone"})
    User newUser(@RequestParam("name") String name, @RequestParam("address") String address, @RequestParam("phone") String phone) {
        User user = new User();
        user.setId((long) (Math.random() * 100 * LocalTime.now().getNano() * LocalTime.now().getHour() * LocalTime.now().getSecond()));
        user.setPhone(phone);
        user.setAddress(address);
        user.setName(name);
        repository.create(user);
        return repository.getById(user.getId());
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.delete(repository.getById(id));
    }

}