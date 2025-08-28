package com.upwork.newsports.controller;

import com.upwork.newsports.model.Subscription;
import com.upwork.newsports.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable String id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        if (subscription != null) {
            return ResponseEntity.ok(subscription);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByUserId(userId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByStatus(status));
    }

    @GetMapping("/plan/{plan}")
    public ResponseEntity<List<Subscription>> getSubscriptionsByPlan(@PathVariable String plan) {
        return ResponseEntity.ok(subscriptionService.getSubscriptionsByPlan(plan));
    }

    @GetMapping("/expired")
    public ResponseEntity<List<Subscription>> getExpiredSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getExpiredSubscriptions());
    }

    @GetMapping("/active")
    public ResponseEntity<List<Subscription>> getActiveSubscriptions() {
        return ResponseEntity.ok(subscriptionService.getActiveSubscriptions());
    }

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        if (subscription.getStartDate() == null) {
            subscription.setStartDate(LocalDate.now());
        }

        if (subscription.getStatus() == null) {
            subscription.setStatus("ACTIVE");
        }

        return ResponseEntity.ok(subscriptionService.saveSubscription(subscription));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable String id, @RequestBody Subscription subscription) {
        Subscription existingSubscription = subscriptionService.getSubscriptionById(id);
        if (existingSubscription != null) {
            subscription.setId(id);
            return ResponseEntity.ok(subscriptionService.saveSubscription(subscription));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String id) {
        Subscription subscription = subscriptionService.getSubscriptionById(id);
        if (subscription != null) {
            subscriptionService.deleteSubscription(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}