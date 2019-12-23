
package lab.repository;

import lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Repository
@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByPhone(String phone);

  Boolean existsByPhone(String phone);
}
