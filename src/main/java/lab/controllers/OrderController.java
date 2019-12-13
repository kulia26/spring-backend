package lab.controllers;

import lab.entity.Order;
import lab.repository.JDBCOrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
class OrderController {
    @Autowired
    private JDBCOrderDAO repository;

    /* get all */
    @GetMapping("/orders")
    List<Order> getAll() {
        return repository.getAll();
    }

    /* find by id */
    @GetMapping("/orders/{id}")
    Order one(@PathVariable Long id) {
        return repository.getById(id);
    }

    /* find by user.id */
    @GetMapping(value = "/orders", params = {"user"})
    ArrayList<Order> findByName(@RequestParam("user") String USER_ID) {
        List<Order> all = repository.getAll();
        ArrayList<Order> selected = new ArrayList<>();
        for (Order p : all) {
            if (p.getUserId().toString().equalsIgnoreCase(USER_ID)) {
                selected.add(p);
            }
        }
        return selected;
    }

    // put raw json in request body like
    //    {
    //        "id": 21312,
    //        "USER_ID": 1,
    //        "PRODUCT_ID": 1
    //    }

    @PutMapping("/orders/{id}")
    Order updateOrder(@RequestBody Order newOrder) {
        repository.update(newOrder);
        return repository.getById(newOrder.getId());
    }

    @PostMapping("/orders")
    Order newOrder(@RequestBody Order newOrder) {
        repository.create(newOrder);
        return repository.getById(newOrder.getId());
    }

    @PostMapping(value = "/orders", params = {"user", "product"})
    Order newOrder(@RequestParam("user") Long USER_ID, @RequestParam("product") Long PRODUCT_ID) {
        Order order = new Order();
        order.setId((long) (Math.random() * 100 * LocalTime.now().getNano() * LocalTime.now().getHour() * LocalTime.now().getSecond()));
        order.setProductId(PRODUCT_ID);
        order.setUserId(USER_ID);
        repository.create(order);
        return repository.getById(order.getId());
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        repository.delete(repository.getById(id));
    }

}