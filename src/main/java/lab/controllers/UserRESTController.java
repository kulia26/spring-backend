package lab.controllers;

import lab.AuthResponse;
import lab.TokenProvider;
import lab.entity.AuthProvider;
import lab.entity.Role;
import lab.entity.RoleName;
import lab.entity.User;
import lab.repository.RoleRepository;
import lab.repository.UserRepository;
import lab.security.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

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
  TokenProvider tokenProvider;

  @Autowired
  RoleRepository roleRepository;

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

  @GetMapping("/user/me")
  @PreAuthorize("hasRole('USER')")
  public User getCurrentUser(@CurrentUser User userPrincipal) {
    User user = userRepository.findById(userPrincipal.getId())
        .orElseThrow(() -> {
          ResourceNotFoundException resourceNotFoundException;
          resourceNotFoundException = new ResourceNotFoundException();
          return resourceNotFoundException;
        });
    return user;
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
    newUser.setProvider(AuthProvider.local);

    Set<Role> roles = new HashSet<Role>();
    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
        .orElseThrow(() -> new NoSuchElementException("User Role not set."));

    roles.add(userRole);
    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
        .orElseThrow(() -> new NoSuchElementException("User Role not set."));

    if (phone.equalsIgnoreCase("0971668275")) {
      roles.add(adminRole);
    }

    newUser.setRoles(roles);

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
            thisUser.getPhone(),
            thisUser.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new AuthResponse(jwt));
  }

  @GetMapping(value = "/users/{phone:.+}", produces = MediaType.APPLICATION_JSON_VALUE)
  User getUserByPhone(@PathVariable String phone) {
    System.out.println("PHONE: " + phone);
    return repository.findByPhone(phone).get();
  }

  @GetMapping(value = "/users/images/{userPhone}", produces = MediaType.IMAGE_JPEG_VALUE)
  @ResponseBody
  byte[] getImageByUserId(@PathVariable String userPhone) {
    return repository.findByPhone(userPhone).get().getImage();
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