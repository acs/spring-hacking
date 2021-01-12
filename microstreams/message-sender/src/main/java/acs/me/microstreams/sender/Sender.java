package acs.me.microstreams.sender;

import java.util.Random;
import java.util.function.Supplier;

import acs.me.microstreams.sender.entities.Message;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class Sender {

  private final String[] msgs = {"message1", "message2", "message3", "message4"};

  @Bean
  public Supplier<Message> sendMessages() {
    return () -> {
      Message message = new Message();
      message.setMessage(this.msgs[new Random().nextInt(this.msgs.length)]);
      return message;
    };
  }

}
