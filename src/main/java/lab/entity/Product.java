package lab.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity

@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer cost;

  @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
  @JsonBackReference
  private List<OrderItem> orderItems;

  @CreationTimestamp
  @JsonIgnore
  private Date createdAt;

  @UpdateTimestamp
  @JsonIgnore
  private Date updatedAt;

  public Product(Long id, String name, Integer cost) {
    this.id = id;
    this.name = name;
    this.cost = cost;
  }

  public Product() {

  }


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }


  @Override
  public String toString() {
    return "Product: {" + "id:" + id + ", name: " + name + ", cost: " + cost + "}\n";
  }
}
