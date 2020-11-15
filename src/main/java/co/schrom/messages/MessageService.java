package co.schrom.messages;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MessageService implements MessageServiceInterface {
    @Getter
    List<MessageInterface> messages;

    private static MessageService instance;

    private MessageService() {
        this.messages = new ArrayList<>();
    }

    @Override
    public MessageInterface getMessage(int id) {
        for (MessageInterface message : messages) {
            if (message.getId() == id) {
                return message;
            }
        }
        return null;
    }

    @Override
    public boolean addMessage(MessageInterface message) {
        return messages.add(message);
    }

    @Override
    public boolean removeMessage(int id) {
        return messages.removeIf(message -> message.getId() == id);
    }

    public static MessageService getInstance() {
        if (MessageService.instance == null) {
            MessageService.instance = new MessageService();
        }
        return MessageService.instance;
    }
}
