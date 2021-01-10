package co.schrom.battle;

import co.schrom.card.CardInterface;

public interface BattleRoundInterface {
    int getId();

    CardInterface getCardA();

    CardInterface getCardB();

    CardInterface getWinnerCard();
}
