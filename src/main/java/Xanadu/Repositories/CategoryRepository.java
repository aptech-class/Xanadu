package Xanadu.Repositories;

import Xanadu.Entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByTitle(String title);

    List<Category> findByStatus(Boolean status);
}
