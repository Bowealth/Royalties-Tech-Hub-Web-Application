package com.Ecommerce.E_commerce.controller;

import com.Ecommerce.E_commerce.entity.CartItem;
import com.Ecommerce.E_commerce.entity.Order;
import com.Ecommerce.E_commerce.entity.Product;
import com.Ecommerce.E_commerce.service.OrderService;
import com.Ecommerce.E_commerce.service.ProductService;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;
    private final OrderService orderService;

    public CartController(ProductService productService,
                          OrderService orderService) {

        this.productService = productService;
        this.orderService = orderService;
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {

        List<CartItem> cart =
                (List<CartItem>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    @GetMapping("/add/{id}")
    public String addToCart(@PathVariable Long id,
                            HttpSession session) {

        Product product =
                productService.getProductById(id);

        List<CartItem> cart = getCart(session);

        for (CartItem item : cart) {

            if (item.getProduct()
                    .getId()
                    .equals(id)) {

                item.setQuantity(
                        item.getQuantity() + 1);

                return "redirect:/cart";
            }
        }

        cart.add(new CartItem(product));

        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model,
                           HttpSession session) {

        List<CartItem> cart =
                getCart(session);

        double total =
                cart.stream()
                        .mapToDouble(
                                CartItem::getSubtotal)
                        .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "cart";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable Long id,
                         HttpSession session) {

        List<CartItem> cart =
                getCart(session);

        cart.removeIf(item ->
                item.getProduct()
                        .getId()
                        .equals(id));

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model,
                           HttpSession session) {

        List<CartItem> cart =
                getCart(session);

        double total =
                cart.stream()
                        .mapToDouble(
                                CartItem::getSubtotal)
                        .sum();

        model.addAttribute("order",
                new Order());

        model.addAttribute("total",
                total);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String placeOrder(
            @ModelAttribute Order order,
            HttpSession session) {

        List<CartItem> cart =
                getCart(session);

        double total =
                cart.stream()
                        .mapToDouble(
                                CartItem::getSubtotal)
                        .sum();

        order.setTotalAmount(total);

        orderService.save(order);

        session.removeAttribute("cart");

        return "redirect:/cart/success";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
