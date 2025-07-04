package com.owpk.subscription.controller.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.owpk.subscription.SubscriptionService;
import com.owpk.subscription.jpa.Subscription;

@RestController
@RequestMapping("/api/sub")
public class SubscriptionRestController {

    private final SubscriptionService service;

    public SubscriptionRestController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Subscription> list(@RequestParam(required = false) String searchId) {
        return service.listAll(searchId);
    }

    @PostMapping
    public Subscription create(@RequestParam("id") String id) {
        return service.create(id);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().body("Ok");
    }
}
