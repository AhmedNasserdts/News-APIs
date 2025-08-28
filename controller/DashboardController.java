package com.upwork.newsports.controller;

import com.upwork.newsports.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final NewsService newsService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final LogService logService;

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();

        // News count
        stats.put("newsCount", newsService.getAllNews().size());

        // API requests count (assuming API requests are logged with type "API_PULL")
        stats.put("apiRequestsCount", logService.countLogsByType("API_PULL"));

        // Subscribers count (active subscriptions)
        stats.put("subscribersCount", subscriptionService.getActiveSubscriptions().size());

        // Login count (assuming login events are logged with type "LOGIN")
        stats.put("loginCount", logService.countLogsByType("LOGIN"));

        // Active users count
        stats.put("activeUsersCount", userService.getActiveUsers().size());

        // Recent news (last 10)
        stats.put("recentNews", newsService.getLatestNews().stream().limit(10).toList());

        return ResponseEntity.ok(stats);
    }
}