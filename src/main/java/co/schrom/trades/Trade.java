package co.schrom.trades;

import co.schrom.card.CardInterface;
import lombok.Builder;
import lombok.Getter;

@Builder
public class Trade implements TradeInterface {

    @Getter
    int id;

    @Getter
    CardInterface cardA;

    @Getter
    CardInterface cardB;

    @Getter
    int coins;

    @Getter
    boolean accepted;
}
