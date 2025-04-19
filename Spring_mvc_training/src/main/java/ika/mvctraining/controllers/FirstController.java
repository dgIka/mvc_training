package ika.mvctraining.controllers;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/first")
public class FirstController {
    @GetMapping("/hello")
    public String helloPage(@RequestParam(value = "name", defaultValue = "nothing") String name,         // @RequestParam("name", requierd = false)  позволит передавать Get запрос без параметров, иначе выдаст ошибку
                            @RequestParam(value = "surname", defaultValue = "happened") String surname,
                            Model model) {
//        System.out.println("Hello " + name + " " + surname);
        model.addAttribute("message", "Hello, " + name + " " + surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodByePage() {
        return "first/goodbye";
    }


    @RequestMapping("/calculator")
    public String calculator(@RequestParam(value = "a", defaultValue = "0") String a,
                             @RequestParam(value = "b", defaultValue = "1")String b,
                             @RequestParam(value = "action", defaultValue = "0")String action, Model model) {
        double result;
        int d = Integer.parseInt(a);
        int e = Integer.parseInt(b);
        switch (action) {
            case "multiplication": result = d * e;
            break;
            case "addition": result = d + e;
            break;
            case "subtraction": result = d - e;
            break;
            case "division" : result = (double) d / e;
            break;
            default: result = 0;
        }
        model.addAttribute("result", result);
        return "first/calculator";
    }
}
