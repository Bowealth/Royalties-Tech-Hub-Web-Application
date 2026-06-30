package com.Ecommerce.E_commerce.controller;


import com.Ecommerce.E_commerce.service.ProductService;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ProductService service;

    public HomeController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
                "products",
                service.getAllProducts());

        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword,
                         Model model) {

        model.addAttribute("products",
                service.searchProducts(keyword));

        model.addAttribute("keyword", keyword);

        return "index";
    }
}
