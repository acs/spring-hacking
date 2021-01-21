package acs.me.microstreams.logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class LoggerApplication {

  public static void main(String[] args)  throws Exception {

    SpringApplication.run(LoggerApplication.class, args);
    System.out.println("Hit 'Enter' to terminate");
    System.in.read();
  }

}
