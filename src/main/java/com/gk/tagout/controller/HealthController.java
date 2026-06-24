package com.gk.tagout.controller;

import java.time.Instant;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Lightweight application-level health endpoint, separate from the
 * Actuator's /actuator/health, for simple liveness checks and as a
 * placeholder alongside the API endpoints added later.
 */
@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        return Map.of(
                "status", "UP",
                "service", "tag-out-api",
                "timestamp", Instant.now().toString()
        );
    }
}
