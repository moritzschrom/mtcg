package co.schrom.messages;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Message implements MessageInterface {
    @Getter
    @Setter
    int id;

    @Getter
    String message;
}
