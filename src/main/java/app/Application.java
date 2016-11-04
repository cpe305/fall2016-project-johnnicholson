package app;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;

@Configuration
@ComponentScan(basePackages = "controller, app, transactions, model")
@EnableAutoConfiguration
public class Application {
  public static Logger lgr = Logger.getLogger(Application.class);

  public static void main(String[] args) {
    BasicConfigurator.configure();
    SpringApplication.run(Application.class, args);
    lgr.info("Application Started");
  }

  @RequestMapping("/error")
  public static String error() {
    return "Error";
  }
}
