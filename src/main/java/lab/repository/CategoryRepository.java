
package lab.repository;

import lab.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Repository
@CrossOrigin
public interface CategoryRepository extends JpaRepository<Category, Long> {
  List<Category> findByName(String name);
}
