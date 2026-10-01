package com.ecommerce.gateway;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallbackController {

    @GetMapping("/fallback")
    public ResponseEntity<Map<String, String>> fallback() {
        return ResponseEntity.status(503).body(Map.of(
                "status", "SERVICE_UNAVAILABLE",
                "message", "Downstream service is temporarily unavailable"));
    }
}
