package io.ganchix.streamclouddemo;

import java.util.function.Consumer;

import io.ganchix.streamclouddemo.event.AccountConsumer;
import io.ganchix.streamclouddemo.event.AccountEvent;
import io.ganchix.streamclouddemo.event.IdempotentChannelInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.random.EasyRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.interceptor.GlobalChannelInterceptorWrapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@Slf4j
@EnableScheduling
public class StreamCloudDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(StreamCloudDemoApplication.class, args);
  }

  @Bean
  public Consumer<Message<AccountEvent>> account() {
    return new AccountConsumer();
  }

  @Autowired
  StreamBridge streamBridge;

  EasyRandom generator = new EasyRandom();

  @Scheduled(initialDelay = 20000L, fixedRate = 1000L)
  void send() {
    for (int i = 0; i < 20; i++) {
      AccountEvent accountEvent = generator.nextObject(AccountEvent.class);
      streamBridge.send("sender-out-0", accountEvent);
    }
  }

  @Bean
  public ChannelInterceptor idempotentChannelInterceptor() {
    return new IdempotentChannelInterceptor();
  }

  @Bean
  public GlobalChannelInterceptorWrapper channelInterceptorWrapper(ChannelInterceptor amigaIdempotentChannelInterceptor) {
    GlobalChannelInterceptorWrapper globalChannelInterceptorWrapper =
        new GlobalChannelInterceptorWrapper(amigaIdempotentChannelInterceptor);
    globalChannelInterceptorWrapper.setPatterns(new String[]{"*-in-*"});
    return globalChannelInterceptorWrapper;
  }
}
