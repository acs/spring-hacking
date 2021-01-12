package acs.me.microstreams.logger;

import java.util.HashMap;
import java.util.Map;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;


@ExtendWith(OutputCaptureExtension.class)
class LoggerApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  public void testMessageLogger(CapturedOutput output) {
    try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
      TestChannelBinderConfiguration
        .getCompleteConfiguration(LoggerApplication.class))
      .web(WebApplicationType.NONE)
      .run()) {

      InputDestination source = context.getBean(InputDestination.class);
      acs.me.microstreams.logger.entities.Message message =
        new acs.me.microstreams.logger.entities.Message();
      message.setMessage("message10");

      final MessageConverter converter = context.getBean(CompositeMessageConverter.class);
      Map<String, Object> headers = new HashMap<>();
      headers.put("contentType", "application/json");
      MessageHeaders messageHeaders = new MessageHeaders(headers);
      final Message<?> messageStream = converter.toMessage(message, messageHeaders);

      source.send(messageStream);

//      Awaitility.await().until(output::getOut, value -> {
//        System.out.println(value);
//        return true;
//      }
//      );

      Awaitility.await().until(output::getOut, value -> value.contains("message10"));
    }
  }

}
