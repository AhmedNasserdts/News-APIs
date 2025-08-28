package com.upwork.newsports.controller;

import com.upwork.newsports.model.News;
import com.upwork.newsports.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;


    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> getNewsById(@PathVariable String id) {
        News news = newsService.getNewsById(id);
        if (news != null) {
            return ResponseEntity.ok(news);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<News>> getNewsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(newsService.getNewsByCategory(category));
    }

    @GetMapping("/source/{source}")
    public ResponseEntity<List<News>> getNewsBySource(@PathVariable String source) {
        return ResponseEntity.ok(newsService.getNewsBySource(source));
    }
    @GetMapping("/date-range/{range}")
    public ResponseEntity<List<News>> getNewsByDateRange(@PathVariable String range) {
        // Split by ":"
        String[] parts = range.split(":");
        LocalDate start = LocalDate.parse(parts[0]); // yyyy-MM-dd
        LocalDate end = LocalDate.parse(parts[1]);   // yyyy-MM-dd

        Instant startInstant = start.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant endInstant = end.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        return ResponseEntity.ok(newsService.getNewsByDateRange(startInstant, endInstant));
    }
    @GetMapping("/latest")
    public ResponseEntity<List<News>> getLatestNews() {
        return ResponseEntity.ok(newsService.getLatestNews());
    }

    @PostMapping
    public ResponseEntity<News> createNews(@RequestBody News news) {
        // Check if news already exists by dedupKey
        if (news.getDedupKey() != null && newsService.existsByDedupKey(news.getDedupKey())) {
            return ResponseEntity.badRequest().body(null);
        }

        if (news.getPublishedAt() == null) {
            news.setPublishedAt(Instant.now());
        }

        return ResponseEntity.ok(newsService.saveNews(news));
    }

    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(@PathVariable String id, @RequestBody News news) {
        News existingNews = newsService.getNewsById(id);
        if (existingNews != null) {
            news.setId(id);
            return ResponseEntity.ok(newsService.saveNews(news));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable String id) {
        News news = newsService.getNewsById(id);
        if (news != null) {
            newsService.deleteNews(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}