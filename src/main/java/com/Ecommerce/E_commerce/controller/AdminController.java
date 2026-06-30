package com.Ecommerce.E_commerce.controller;


import com.Ecommerce.E_commerce.entity.Product;
import com.Ecommerce.E_commerce.service.ProductService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductService service;

    public AdminController(ProductService service) {
        this.service = service;
    }

    // ADMIN PAGE
    @GetMapping
    public String adminPage(Model model) {

        model.addAttribute("products",
                service.getAllProducts());

        model.addAttribute("product",
                new Product());

        return "admin";
    }

    // SAVE PRODUCT
    @PostMapping("/save")
    public String saveProduct(
            @ModelAttribute Product product,
            @RequestParam("imageFile") MultipartFile file
    ) throws IOException {

        // Upload folder
        String uploadDir = System.getProperty("user.dir")
                + "/src/main/resources/static/images/";

        // Create folder if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // File name
        String fileName = file.getOriginalFilename();

        // Save image
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),
                filePath,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Save image name in database
        product.setImageName(fileName);

        service.saveProduct(product);

        return "redirect:/admin";
    }

    // EDIT PAGE
    @GetMapping("/edit/{id}")
    public String editProduct(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "product",
                service.getProductById(id));

        return "edit-product";
    }

    // UPDATE PRODUCT
    @PostMapping("/update")
    public String updateProduct(
            @ModelAttribute Product product,
            @RequestParam("imageFile")
            MultipartFile file
    ) throws IOException {

        if (!file.isEmpty()) {

            String uploadDir =
                    "src/main/resources/static/images/";

            String fileName =
                    file.getOriginalFilename();

            Path path =
                    Paths.get(uploadDir + fileName);

            Files.write(path, file.getBytes());

            product.setImageName(fileName);
        }

        service.saveProduct(product);

        return "redirect:/admin";
    }

    // DELETE PRODUCT
    @GetMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable Long id) {

        service.deleteProduct(id);

        return "redirect:/admin";
    }
}