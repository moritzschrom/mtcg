package co.schrom.trades;

import co.schrom.card.CardInterface;

public interface TradeInterface {

    int getId();

    CardInterface getCardA();

    CardInterface getCardB();

    int getCoins();

    boolean isAccepted();
}
