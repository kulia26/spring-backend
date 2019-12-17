package lab.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity

@Data
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String phone;

  private String password;

  private String name;

  @Transient
  private Collection<? extends GrantedAuthority> authorities;

  @Lob
  @JsonIgnore //
  @Transient  // for testing
  private byte[] image;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @JsonBackReference
  private List<Basket> baskets;

  @CreationTimestamp
  @JsonIgnore
  private Date createdAt;

  @UpdateTimestamp
  @JsonIgnore
  private Date updatedAt;

  public User(Long id, String name, String password, String phone, byte[] image) {
    this.id = id;
    this.phone = phone;
    this.password = password;
    this.name = name;
    this.image = image;

    Collection<GrantedAuthority> authorities = null;
    authorities.add(new SimpleGrantedAuthority("ADMIN"));

    this.authorities = authorities;
  }

  public User() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  @Transient
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return name;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return "User: {" + "id:" + id + ", phone:'" + phone + "', password:'" + password + "', name:'" + name + '}';
  }
}
