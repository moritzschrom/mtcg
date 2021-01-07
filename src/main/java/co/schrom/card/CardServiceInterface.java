package co.schrom.card;

import java.util.List;

public interface CardServiceInterface {
    CardInterface getCard(int id);

    List<CardInterface> getCards();

    CardInterface addCard(CardInterface card);

    CardInterface addCardToPackage(CardInterface card, PackageInterface cardPackage);

    boolean deleteCard(int id);
}
