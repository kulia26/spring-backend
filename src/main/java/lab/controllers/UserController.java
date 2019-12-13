package lab.controllers;

import lab.entity.User;
import lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
class UserController {
    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    List<User> findAll() {
        return repository.findAll();
    }


    @GetMapping("/users/{id}")
    Optional<User> findById(@PathVariable Long id) {
        return repository.findById(id);
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