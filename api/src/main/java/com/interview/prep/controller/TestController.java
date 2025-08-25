package com.interview.prep.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*") // Allow CORS if needed
public class TestController {
  // e.g. in your controller
  @GetMapping("/api/secure/ping")
  public String ping() { return "pong"; }
}
