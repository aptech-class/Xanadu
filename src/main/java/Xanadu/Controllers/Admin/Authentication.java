package Xanadu.Controllers.Admin;

import Xanadu.Entities.Role;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@Slf4j
@RequestMapping("/admin")
public class Authentication {

    @GetMapping("/signin.html")
    public String signin(
            @ModelAttribute("message") String message,
            @AuthenticationPrincipal UserDetails user,
            Model model
    ) {
        if (user == null ||
                (message != null && !message.isEmpty()) ||
                user.getAuthorities().stream().noneMatch(auth -> {
                    String role = auth.getAuthority();
                    return role.equals(Role.ADMIN.name())
                            || role.equals(Role.MIDDLE.name())
                            || role.equals(Role.STAFF.name());
                })
        ) {
            model.addAttribute("message", message);
            return "admin/signin";
        }
        return "redirect:/admin/home.html";

    }

    @RequestMapping("/signin")
    public String signin(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String message = (String) request.getAttribute("message");
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/admin/signin.html";
    }


}
