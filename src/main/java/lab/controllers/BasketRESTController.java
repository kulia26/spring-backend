package lab.controllers;

import lab.entity.Basket;
import lab.repository.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/", produces = {MediaType.APPLICATION_JSON_VALUE})
public class BasketRESTController {
  @Autowired
  private BasketRepository repository;

  public BasketRepository getRepository() {
    return repository;
  }

  public void setRepository(BasketRepository repository) {
    this.repository = repository;
  }

  @GetMapping(value = "/baskets")
  public List<Basket> getAllBaskets() {
    return repository.findAll();
  }

  @PostMapping("/baskets")
  Basket createOrSaveBasket(@RequestBody Basket newBasket) {
    return repository.save(newBasket);
  }

  @GetMapping("/baskets/{id}")
  Basket getBasketById(@PathVariable Long id) {
    return repository.findById(id).get();
  }

  @PutMapping("/baskets/{id}")
  Basket updateBasket(@RequestBody Basket newBasket, @PathVariable Long id) {

    return repository.findById(id).map(Basket -> {
      Basket.setUser(newBasket.getUser());
      return repository.save(Basket);
    }).orElseGet(() -> {
      newBasket.setId(id);
      return repository.save(newBasket);
    });
  }

  @DeleteMapping("/baskets/{id}")
  void deleteBasket(@PathVariable Long id) {
    repository.deleteById(id);
  }

}