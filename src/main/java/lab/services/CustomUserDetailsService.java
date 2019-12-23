package lab.services;

import lab.entity.User;
import lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public User loadUserByUsername(String phone)
      throws UsernameNotFoundException {
    try {
      Optional<User> users = userRepository.findByPhone(phone);
      return users.get();
    } catch (UsernameNotFoundException e) {
      new UsernameNotFoundException("User not found with phone : " + phone);
      return null;
    }
  }

  // This method is used by JWTAuthenticationFilter
  @Transactional
  public UserDetails loadUserById(Long id) {
    User user = userRepository.findById(id).orElseThrow(
        () -> new UsernameNotFoundException("User not found with id : " + id)
    );
    return user;
  }
}