package com.example.jenkinstest.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
  @GetMapping("/status")
  public String status() {
    return "Application is running!";
  }
}
