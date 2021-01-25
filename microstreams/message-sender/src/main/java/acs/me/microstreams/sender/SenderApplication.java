package acs.me.microstreams.sender;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

import acs.me.microstreams.sender.entities.Message;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SenderApplication {

	private final String[] msgs = {"message1", "message2", "message3", "message4"};

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SenderApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(SenderApplication.class, args);
	}

	@Bean
	public Supplier<Message> sendMessages() {
		return () -> {
			Message message = new Message();
			message.setMessage(this.msgs[new Random().nextInt(this.msgs.length)]);
			return message;
		};
	}

	@Bean
	public Consumer<Message> logUserMessage() {
		return message -> {
			LOGGER.info(message.getMessage());
		};
	}

}
