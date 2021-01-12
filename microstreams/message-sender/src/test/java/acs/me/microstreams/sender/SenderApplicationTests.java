package acs.me.microstreams.sender;

import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class SenderApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  public void testMessageSender() {
    try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
      TestChannelBinderConfiguration
        .getCompleteConfiguration(SenderApplication.class))
      .web(WebApplicationType.NONE)
      .run()) {

      OutputDestination target = context.getBean(OutputDestination.class);
      Message<byte[]> sourceMessage = target.receive(10000);

      final MessageConverter converter = context.getBean(CompositeMessageConverter.class);
      acs.me.microstreams.sender.entities.Message message =
        (acs.me.microstreams.sender.entities.Message) converter
        .fromMessage(sourceMessage, acs.me.microstreams.sender.entities.Message .class);

      assertThat(message.getMessage()).isBetween("message1", "message5");
    }
  }

}
