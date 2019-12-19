
package lab.repository;

import lab.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findByPasswordAndPhone(String password, String phone);

  List<User> findByPhone(String phone);
}
