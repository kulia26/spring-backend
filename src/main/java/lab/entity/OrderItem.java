package lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;


@Entity

@Data
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn
  private Product product;

  @ManyToOne
  @JoinColumn
  private Basket basket;

  @CreationTimestamp
  @JsonIgnore
  private Date createdAt;

  @UpdateTimestamp
  @JsonIgnore
  private Date updatedAt;

  public OrderItem(Long id, Basket basket, Product product) {
    this.id = id;
    this.basket = basket;
    this.product = product;
  }

  public OrderItem() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Basket getBasket() {
    return basket;
  }

  public void setBasket(Basket basket) {
    this.basket = basket;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public String toString() {
    return "OrderItem: {" + "id:" + id + ", basket:" + this.basket.toString() + ", product:" + this.product.toString() + '}';
  }
}
