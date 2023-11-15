package Xanadu.Services;

import Xanadu.Entities.Category;
import Xanadu.Entities.Collection;
import Xanadu.Entities.Customer;
import Xanadu.Repositories.CategoryRepository;
import Xanadu.Repositories.CollectionRepository;
import Xanadu.Utils.StringProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {
    private final String wordErrorForHandle = "[#%{}\\\\^~\\[\\]`]";

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CollectionRepository collectionRepository;

    @Transactional
    public Category save(Category category) {
        List<Collection> collectionsSaved = new ArrayList<>();
        if(category.getCollections()!=null){
            category.getCollections().forEach(collection -> {
                if (collection.getId() == null) {
                    Collection collectionExists = collectionRepository.findByTitle(collection.getTitle());
                    collectionsSaved.add(Objects.requireNonNullElseGet(collectionExists, () -> {
                        String handle = collection.getTitle()
                                .trim()
                                .replaceAll(" ", "-")
                                .replaceAll(wordErrorForHandle, "")
                                + "-" + StringProcessor.generateRandomCharacters(10, wordErrorForHandle);

                        collection.setHandle(handle);
                        return collectionRepository.save(collection);
                    }));
                } else {
                    collectionsSaved.add(collection);
                }
            });
        }
        category.setCollections(collectionsSaved);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category findByTitle(String title) {
        return categoryRepository.findByTitle(title);
    }

    @Transactional(readOnly = true)
    public List<Category> findByStatus(boolean status) {
        List<Category> categories = categoryRepository.findByStatus(status);
        categories.forEach(category -> {
            List<Category> list = new ArrayList<>();
            list.add(category);
            List<Collection> collections = collectionRepository.findByCategoriesInAndStatus(list, true);
            category.setCollections(collections);
        });
        return categories;
    }

    @Transactional(readOnly = true)
    public Page<Category> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Category findByIdWithCollections(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        Category category = categoryOptional.orElse(null);
        if (category != null) {
            category.getCollections().forEach(Collection::getId);
        }
        return category;
    }


}
