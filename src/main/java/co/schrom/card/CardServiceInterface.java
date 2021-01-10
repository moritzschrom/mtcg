package co.schrom.card;

import co.schrom.user.UserInterface;

import java.util.List;

public interface CardServiceInterface {
    CardInterface getCard(int id);

    List<CardInterface> getCards();

    List<CardInterface> getCardsForUser(UserInterface user);

    List<CardInterface> getCardsForPackage(PackageInterface cardPackage);

    CardInterface addCard(CardInterface card);

    CardInterface addCardToPackage(CardInterface card, PackageInterface cardPackage);

    CardInterface addCardToUser(CardInterface card, UserInterface user);

    CardInterface lockCard(CardInterface card, boolean isLocked);

    boolean deleteCard(int id);
}
