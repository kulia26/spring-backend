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
public class Basket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn
  private User user;

  @OneToMany(mappedBy = "basket", fetch = FetchType.EAGER)
  @JsonBackReference
  private List<OrderItem> orderItems;

  @CreationTimestamp
  @JsonIgnore
  private Date createdAt;

  @UpdateTimestamp
  @JsonIgnore
  private Date updatedAt;

  public Basket(Long id, User user) {
    this.id = id;
    this.user = user;
  }

  public Basket() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "Basket: {" + "id:" + id + ", user:" + this.user.toString() + '}';
  }
}
