package co.schrom.card;

import java.util.List;

public interface CardServiceInterface {
    CardInterface getCard(int id);

    List<CardInterface> getCards();

    CardInterface addCard(CardInterface card);

    boolean deleteCard(int id);
}
