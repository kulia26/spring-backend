package lab.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity

@Data
public class User implements UserDetails, OAuth2User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonSerialize
  private Long id;


  private String phone;

  private String password;

  private String name;


  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
  @JsonSerialize
  private List<OrderItem> orderItems;

  @Transient
  @JsonIgnore
  private Collection<? extends GrantedAuthority> authorities;

  @Lob
  @Column(columnDefinition = "mediumblob")
  @JsonIgnore //
  private byte[] image;

  private String imageUrl;

  @CreationTimestamp
  @JsonIgnore
  private Date createdAt;

  @UpdateTimestamp
  @JsonIgnore
  private Date updatedAt;

  @NotNull
  @Enumerated(EnumType.STRING)
  @Column(columnDefinition = "default 'local'")
  private AuthProvider provider;

  private String providerId;

  @Transient
  private Map<String, Object> attributes;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<Role> roles = new HashSet<>();

  public User(Long id, String name, String password, String phone, byte[] image, Set<Role> roles) {
    this.id = id;
    this.phone = phone;
    this.password = password;
    this.name = name;
    this.image = image;
    this.roles = roles;
    this.authorities = roles.stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toList());

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

  public Collection<? extends GrantedAuthority> getAuthorities() {
    authorities = roles.stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toList());
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

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "User: {" + "id:" + id +
        ", phone:'" + phone +
        "', password:'" + password +
        "', name:'" + name + "' }";
  }

  public String getProviderId() {
    return providerId;
  }

  public void setProviderId(String providerId) {
    this.providerId = providerId;
  }

  public AuthProvider getProvider() {
    return provider;
  }

  public void setProvider(AuthProvider provider) {
    this.provider = provider;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
