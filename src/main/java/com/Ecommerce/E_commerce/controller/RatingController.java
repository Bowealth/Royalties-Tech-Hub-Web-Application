package com.Ecommerce.E_commerce.controller;

import com.Ecommerce.E_commerce.entity.Product;
import com.Ecommerce.E_commerce.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class RatingController {

    private final ProductService productService;

    public RatingController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/rate")
    public String rateProduct(
            @RequestParam Long productId,
            @RequestParam Double stars) {

        Product product =
                productService.getProductById(productId);

        product.setRating(stars);

        productService.saveProduct(product);

        return "redirect:/";
    }
}