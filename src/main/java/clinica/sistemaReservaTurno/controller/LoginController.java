package clinica.sistemaReservaTurno.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

   /*@GetMapping("/home") //all users
    public String handleWelcome() {
        return "home";
    }*/ //nombre de archivo html

   /* @GetMapping("/admin/home") // admin
    public String handleAdminHome() {
        return "home_admin";
    } //nombre de archivo html

    @GetMapping("/user/home") // user
    public String handleUserHome() {
        return "home_user";
    } //nombre de archivo html
*/
    /*@GetMapping("/login")
    public String handleLogin() {
        return "custom_login";
    }*/

}
