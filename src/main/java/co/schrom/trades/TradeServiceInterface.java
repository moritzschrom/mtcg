package co.schrom.trades;

import co.schrom.card.CardInterface;

import java.util.List;

public interface TradeServiceInterface {
    TradeInterface getTrade(int id);

    List<TradeInterface> getTrades();

    TradeInterface addTrade(CardInterface card);

    boolean deleteTrade(int id);

    TradeInterface addOffer(TradeInterface trade, CardInterface card, int coins);

    TradeInterface acceptTrade(TradeInterface trade);
}
