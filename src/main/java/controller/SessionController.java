package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
  
  @RequestMapping("/Snss")
  public void postSession(@RequestParam String email, @RequestParam String password) {
    
  }
}
