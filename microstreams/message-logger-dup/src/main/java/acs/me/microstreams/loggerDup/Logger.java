package acs.me.microstreams.loggerDup;

import java.util.function.Consumer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import acs.me.microstreams.loggerDup.entities.Message;


@Configuration
public class Logger {

  private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoggerApplication.class);

  @Bean
  public Consumer<Message> process() {
    return message -> {
      logger.info(message.getMessage());
    };
  }

}
