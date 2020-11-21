package co.schrom.messages;

import java.util.List;

public interface MessageServiceInterface {
    MessageInterface getMessage(int id);

    List<MessageInterface> getMessages();

    MessageInterface addMessage(MessageInterface message);

    MessageInterface updateMessage(int id, MessageInterface message);

    boolean deleteMessage(int id);
}
