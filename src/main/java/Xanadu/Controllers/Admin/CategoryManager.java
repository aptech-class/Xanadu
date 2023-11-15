package Xanadu.Controllers.Admin;

import Xanadu.Entities.Category;
import Xanadu.Entities.Collection;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.CategoryService;
import Xanadu.Services.CollectionService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/admin/categories")
@Controller
@Slf4j
public class CategoryManager extends AbstractManager {
    @Autowired
    CategoryService categoryService;
    @Autowired
    private CollectionService collectionService;

    @GetMapping("/index.html")
    public String getCategories(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            Model model
    ) {

        Page<Category> categories = categoryService.findAll(pageNumber - 1, pageSize);
        model.addAttribute("categories", categories);
        setMenu(model, "categories.list");

        setPageOption(model, pageSize, pageNumber, "/admin/customers/index.html");

        return "/admin/categories";
    }

    @GetMapping("/{id}.html")
    public  String getCategory( @PathVariable("id") Long id , Model model){
        Category category = categoryService.findByIdWithCollections(id);
        setMenu(model,"categories");
        setObjectRelateToModel(model);
        model.addAttribute("category",category);
        return "/admin/category.view";
    }

    @GetMapping("/create.html")
    public String create(
            Model model
    ) {
        setMenu(model, "categories.create");
        setObjectRelateToModel(model);
        model.addAttribute("category", new Category());

        return "/admin/category.create";
    }

    @PostMapping("/create")
    public String create (
            @Valid @ModelAttribute Category category,
            BindingResult bindingResult,
            Model model
    ){
        if (bindingResult.hasErrors()) {
            setMenu(model, "categories");
            return "/admin/category.create";
        }
        Category categorySaved = categoryService.save(category);
        return "redirect:/admin/categories/" + categorySaved.getId() + ".html";
    }

    @GetMapping("/{id}/edit.html")
    public String edit(
            Model model,
            @PathVariable("id") Long id
    ) {
        setObjectRelateToModel(model);
        setMenu(model, "categories");
        Category category = categoryService.findByIdWithCollections(id);
        model.addAttribute("category", category);

        return "/admin/category.edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute Category category,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            setMenu(model, "categories");
            return "/admin/category.edit";
        }
        Category categorySaved = categoryService.save(category);
        return "redirect:/admin/categories/" + categorySaved.getId() + ".html";

    }

    @Override
    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model, active, "/admin/categories/index.html");
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active, String url) {
        setDefaultMenu(model, active, url);
    }

    @Override
    protected void setObjectRelateToModel(Model model) {
        List<Collection> collections = collectionService.findAll();
        model.addAttribute("collections", collections);
    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber, String url) {
        List<Integer> sizeOptions = Arrays.asList(20, 30, 50);
        PageOption pageOption = new PageOption(sizeOptions, pageSize, pageNumber, url);
        model.addAttribute("pageOption", pageOption);
    }
}
