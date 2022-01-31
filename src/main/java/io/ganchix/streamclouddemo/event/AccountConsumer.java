package io.ganchix.streamclouddemo.event;

import java.util.function.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

@Slf4j
public class AccountConsumer implements Consumer<Message<AccountEvent>> {
    

    @Override
    public void accept(Message<AccountEvent> accountEventMessage) {
        log.info("Message: {}", accountEventMessage);
    }
}
