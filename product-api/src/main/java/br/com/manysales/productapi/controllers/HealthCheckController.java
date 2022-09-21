package br.com.manysales.productapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/check")
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<HashMap<String, Object>> getStatus(){
        HashMap<String, Object> response = new HashMap<String, Object>();

        response.put("service", "product-api");
        response.put("application-status", "up");
        response.put("service-online", HttpStatus.OK);
        return ResponseEntity.ok(response);
    }
}
