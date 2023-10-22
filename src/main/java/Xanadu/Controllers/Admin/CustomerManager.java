package Xanadu.Controllers.Admin;

import Xanadu.Entities.Customer;
import Xanadu.Models.Admin.PageOption;
import Xanadu.Services.CustomerService;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/customers")
public class CustomerManager extends AbstractManager{

    @Autowired
    CustomerService customerService;
    @GetMapping("/index.html")
    public String getProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "30") Integer pageSize,
            Model model
    ){
        Page<Customer> customers = customerService.findAll(pageNumber-1,pageSize);
        model.addAttribute("customers",customers);

        setMenu(model,"customers.list");
        setPageOption(model,pageSize,pageNumber,"/admin/customers/index.html");
        return "admin/customers";
    }

    @GetMapping("/create.html")
    public String createCustomer(Model model){
        setMenu(model,"customers.create");
        model.addAttribute("customer",new Customer());
        return "/admin/customer.create";
    }

    @PostMapping("/create")
    public String createCustomer(
            @Valid @ModelAttribute Customer customer,
            BindingResult bindingResult,
            Model model
    ){
        if(bindingResult.hasErrors()){
            setMenu(model,"customers.create");
            return "/admin/customer.create";
        }
        Customer customerSaved = customerService.save(customer);
        return "redirect:/admin/customers/"+customerSaved.getId() +".html";
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active) {
        setDefaultMenu(model,active,"/admin/customers/index.html");
    }

    @Override
    protected void setMenu(Model model, @Nonnull String active, String url) {
        setDefaultMenu(model,active,url);
    }

    @Override
    protected void setObjectRelateToModel(Model model) {

    }

    @Override
    protected void setPageOption(Model model, Integer pageSize, Integer pageNumber, String url) {
        List<Integer> sizeOptions = Arrays.asList(20, 30, 50);
        PageOption pageOption = new PageOption(sizeOptions, pageSize, pageNumber, url);
        model.addAttribute("pageOption", pageOption);
    }
}
