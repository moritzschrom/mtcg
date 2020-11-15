package co.schrom.messages;

import java.util.List;

public interface MessageServiceInterface {
    MessageInterface getMessage(int id);

    List<MessageInterface> getMessages();

    boolean addMessage(MessageInterface message);

    boolean replaceMessage(int id, MessageInterface message);

    boolean removeMessage(int id);

    int nextId();
}
