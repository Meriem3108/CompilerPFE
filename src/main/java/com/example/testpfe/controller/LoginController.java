//package com.example.testpfe.controller;
//
//import com.example.testpfe.Entity.User;
//import com.example.testpfe.Service.UserService;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//public class LoginController {
//    @Autowired
//    private UserService userService;
//   @RequestMapping("/login")
//    public String loginPage(){
//        return "auth-login";
//    }
//
////    @RequestMapping("/home")
////    public String loginSubmit(){
////        return "landing_page";
////    }
//@RequestMapping("/home")
//public String loginSubmit(Model model){
//    List<User> users = userService.findAllUsers();
//    model.addAttribute("users", users); // Ajouter les utilisateurs au modèle
//    return "landing_page";
//}
//
//    @GetMapping("/users")
//    public String getUsersPage(Model model) {
//        model.addAttribute("users", userService.findAllUsers());
//        return "BD";
//    }
//
//    @GetMapping("/home1")
//    public String hh(){
//
//       return "/pages/Traduire";
//    }
////    @GetMapping("/home")
////    public String hhh(){
////
////        return "landing_page";
////    }
//    @GetMapping("/traduction")
//    public String traduction(){
//
//        return "translate_template";
//    }
//    @GetMapping("/traductionscenario")
//    public String traductionsc(){
//
//        return "translate_scenario";
//    }
//
//    @GetMapping("/guide")
//    public String guide(){
//
//        return "guide";
//    }
//
//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("user", new User());
//        return "register";
//    }
//
//  /*  @PostMapping("/register")
//    public String registerUser(@ModelAttribute User user) {
//
//        userService.save(user);
//        return "redirect:/login";
//    }
//    */
//  @PostMapping("/register")
//  public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
//      if (result.hasErrors()) {
//          return "register"; // Renvoie vers le formulaire si des erreurs sont présentes
//      }
//      userService.save(user);
//      return "redirect:/login";
//  }
//
////    @GetMapping("/registration")
////    public String registration(Model model) {
////        model.addAttribute("user", new User());
////        return "register";
////    }
////
////    @PostMapping("/registration")
////    public String registration(User user) {
////        userService.save(user);
////        return "redirect:/";
////    }
//
//
//    @GetMapping("/dashboard")
//    public String getDashboard(Model model) {
//        List<User> users = userService.findAllUsers();
//        model.addAttribute("users", users); // Ajouter les utilisateurs au modèle
//        return "pages/landing_page"; // Nom de votre fichier HTML sans l'extension
//    }
//}
package com.example.testpfe.controller;

import com.example.testpfe.Entity.User;
import com.example.testpfe.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginPage() {
        return "auth-login";
    }
/*
    @RequestMapping("/home")
    public String loginSubmit(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users); // Ajouter les utilisateurs au modèle
        return "landing_page";
    }
*/
@RequestMapping("/home")
public String loginSubmit(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String currentUsername = authentication.getName();  // Le nom de l'utilisateur connecté

    // Ajouter le nom d'utilisateur au modèle
    model.addAttribute("username", currentUsername);

    // Retourner la vue
    return "landing_page";


}
    @GetMapping("/users")
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "BD";
    }

    @GetMapping("/home1")
    public String home1() {
        return "/pages/Traduire";
    }

    @GetMapping("/traduction")
    public String traduction() {
        return "translate_template";
    }

    @GetMapping("/traductionscenario")
    public String traductionScenario() {
        return "translate_scenario";
    }


    @GetMapping("/guide")
    public String guide(){

        return "guide";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register"; // Renvoie vers le formulaire si des erreurs sont présentes
        }
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users); // Ajouter les utilisateurs au modèle
        return "pages/landing_page";
    }
    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        // Ajouter le nom d'utilisateur au modèle
        model.addAttribute("username", principal.getName());

        // Retourner le nom de la vue (le fichier HTML)
        return "profile";  // profile.html sera dans /src/main/resources/templates
    }
}
