package io.ganchix.streamclouddemo.event;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

@Slf4j
public class IdempotentChannelInterceptor implements ChannelInterceptor {

  AtomicInteger counter = new AtomicInteger(0);

  @Override
  public Message<?> preSend(final Message<?> message, final MessageChannel channel) {

      log.info("Channel interceptor message {}", message);
      if (counter.get() % 2 == 0) {
        return null;
      }

    return message;
  }

  @Override
  public void afterSendCompletion(final Message<?> message, final MessageChannel channel, final boolean sent,
      @Nullable final Exception ex) {
    counter.incrementAndGet();
  }
}
