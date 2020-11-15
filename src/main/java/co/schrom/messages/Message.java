package co.schrom.messages;

import lombok.Getter;
import lombok.Setter;

public class Message implements MessageInterface {
    @Getter
    @Setter
    int id;

    @Getter
    String message;
}
