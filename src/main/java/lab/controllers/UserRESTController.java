package lab.controllers;

import lab.JwtAuthenticationResponse;
import lab.JwtTokenProvider;
import lab.entity.User;
import lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class UserRESTController {
  @Autowired
  private UserRepository repository;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  public UserRepository getRepository() {
    return repository;
  }

  public void setRepository(UserRepository repository) {
    this.repository = repository;
  }

  @GetMapping(value = "/users")
  public List<User> getAllUsers() {
    return repository.findAll();
  }

  @PostMapping("/users")
  User createOrSaveUser(@RequestBody User newUser) {
    return repository.save(newUser);
  }

  @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  User registerUser(@RequestParam("name") String name,
                    @RequestParam("phone") String phone,
                    @RequestParam("password") String password,
                    @RequestParam("image") MultipartFile image) {
    User newUser = new User();
    newUser.setPassword(passwordEncoder.encode(password));
    newUser.setPhone(phone);
    newUser.setName(name);
    try {
      byte[] imageBytes = image.getBytes();
      newUser.setImage(imageBytes);
    } catch (Exception e) {
      return null;
    }

    return repository.save(newUser);
  }

  @PostMapping(value = "/login")
  ResponseEntity<?> loginUser(@RequestBody User thisUser) {
    User user = new User();

    System.out.println("request: " + thisUser.toString());
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            thisUser.getUsername(),
            thisUser.getPassword()
        )
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }

  @GetMapping("/users/{id}")
  User getUserById(@PathVariable Long id) {
    return repository.findById(id).get();
  }

  @PutMapping("/users/{id}")
  User updateUser(@RequestBody User newUser, @PathVariable Long id) {

    return repository.findById(id).map(user -> {
      user.setName(newUser.getName());
      user.setPhone(newUser.getPhone());
      user.setPassword(newUser.getPassword());
      return repository.save(user);
    }).orElseGet(() -> {
      newUser.setId(id);
      return repository.save(newUser);
    });
  }

  @DeleteMapping("/users/{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }

}