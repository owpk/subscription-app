package com.owpk.subscription.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.owpk.subscription.SubscriptionService;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {
    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    record SubscriptionView(String id, String shortKey, String fullKey, LocalDate expirationDate) {
    }

    @GetMapping
    public String list(@RequestParam(required = false) String searchId, Model model) {
        var list = service.listAll(searchId).stream()
                .map(sub -> new SubscriptionView(
                        sub.getId(),
                        shorten(sub.getKey()),
                        sub.getKey(),
                        sub.getExpirationDate()))
                .toList();

        model.addAttribute("subscriptions", list);
        return "subscriptions";
    }

    private String shorten(String key) {
        if (key == null || key.length() <= 12)
            return key;
        return key.substring(0, 6) + "..." + key.substring(key.length() - 6);
    }

    @PostMapping
    public String create(@RequestParam("id") String id) {
        service.create(id);
        return "redirect:/subscriptions";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        service.delete(id);
        return "redirect:/subscriptions";
    }

}