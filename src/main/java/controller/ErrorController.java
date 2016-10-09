package controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {

  @Override
  public String getErrorPath() {
    return null;
  }

}
