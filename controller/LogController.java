package com.upwork.newsports.controller;

import com.upwork.newsports.model.Log;
import com.upwork.newsports.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class LogController {

    private final LogService logService;

    @GetMapping
    public ResponseEntity<List<Log>> getAllLogs() {
        return ResponseEntity.ok(logService.getAllLogs());
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<Log>> getLogsByType(@PathVariable String type) {
        return ResponseEntity.ok(logService.getLogsByType(type));
    }

    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<Log>> getLogsByActorId(@PathVariable String actorId) {
        return ResponseEntity.ok(logService.getLogsByActorId(actorId));
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Log>> getLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant end) {
        return ResponseEntity.ok(logService.getLogsByDateRange(start, end));
    }

    @PostMapping
    public ResponseEntity<Log> createLog(@RequestBody Log log) {
        return ResponseEntity.ok(logService.saveLog(log));
    }
}