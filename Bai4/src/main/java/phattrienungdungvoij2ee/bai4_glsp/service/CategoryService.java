package phattrienungdungvoij2ee.bai4_glsp.service;

import org.springframework.stereotype.Service;
import phattrienungdungvoij2ee.bai4_glsp.model.Category;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    private List<Category> listCategory = new ArrayList<>();
    private int nextId = 1;

    public CategoryService() {
        // Initialize with default categories
        listCategory.add(new Category(nextId++, "Điện thoại"));
        listCategory.add(new Category(nextId++, "Laptop"));
        listCategory.add(new Category(nextId++, "Tablet"));
    }

    public List<Category> getAll() {
        return listCategory;
    }

    public Category get(int id) {
        return listCategory.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Category newCategory) {
        newCategory.setId(nextId++);
        listCategory.add(newCategory);
    }

    public void update(Category editCategory) {
        Category find = get(editCategory.getId());
        if (find != null) {
            find.setName(editCategory.getName());
        }
    }

    public void delete(int id) {
        listCategory.removeIf(c -> c.getId() == id);
    }
}
