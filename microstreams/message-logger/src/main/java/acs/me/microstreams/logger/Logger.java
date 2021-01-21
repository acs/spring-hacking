package acs.me.microstreams.logger;

import java.util.function.Consumer;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import acs.me.microstreams.logger.entities.Message;


@Configuration
public class Logger {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LoggerApplication.class);

  @Bean
  public Consumer<Message> logUserMessage() {
    return message -> {
      LOGGER.info(message.getMessage());
    };
  }

}
