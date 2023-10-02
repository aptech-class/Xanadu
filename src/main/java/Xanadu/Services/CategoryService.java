package Xanadu.Services;

import Xanadu.Entities.Category;
import Xanadu.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }
}
