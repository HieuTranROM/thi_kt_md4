package com.codegym.thi_md4.controllers;


import com.codegym.thi_md4.dto.OrderDto;
import com.codegym.thi_md4.models.Order;
import com.codegym.thi_md4.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public String listOrders(
            @RequestParam(name = "startDate", required = false) String startDateStr,
            @RequestParam(name = "endDate", required = false) String endDateStr,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "top", required = false, defaultValue = "false") boolean top,
            Model model) {

        List<Order> orders;

        if (top) {
            orders = orderService.getTopOrders(limit);
        } else {
            LocalDateTime startDate = null;
            LocalDateTime endDate = null;

            if (startDateStr != null && !startDateStr.isEmpty()) {
                startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atStartOfDay();
            }

            if (endDateStr != null && !endDateStr.isEmpty()) {
                endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd")).atTime(23, 59, 59);
            }

            orders = orderService.getOrdersByDateRange(startDate, endDate);
        }

        model.addAttribute("orders", orders);
        return "list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Order order = orderService.findById(id);
        if (order != null) {
            model.addAttribute("order", order);
            return "edit_order";
        } else {
            return "redirect:/orders";
        }
    }

    @PostMapping("/edit")
    public String updateOrder(@RequestParam("id") Long id,
                              @RequestParam("productType") String productType,
                              @RequestParam("productName") String productName,
                              @RequestParam("purchaseDate") String purchaseDateStr,
                              @RequestParam("quantity") Integer quantity) {

        Order order = orderService.findById(id);
        if (order != null) {
            order.getProduct().getProductType().setNameType(productType);
            order.getProduct().setNameProduct(productName);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime purchaseDate = LocalDate.parse(purchaseDateStr, formatter).atStartOfDay();
            order.setPurchaseDate(purchaseDate);
            order.setQuantity(quantity);

            orderService.save(order);
        }
        return "redirect:/orders";
    }
}