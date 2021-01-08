package co.schrom.battle;

import co.schrom.card.CardInterface;
import co.schrom.user.UserInterface;

import java.util.List;

public interface DeckServiceInterface {
    List<CardInterface> getDeck(UserInterface user);

    boolean addCardsWithIdsToDeck(int[] ids, UserInterface user);

    boolean addCardToDeck(CardInterface card, UserInterface user);

    boolean clearDeck(UserInterface user);
}
