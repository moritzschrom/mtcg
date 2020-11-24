package co.schrom.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Card implements CardInterface {
    @Getter
    String name;

    @Getter
    float damage;

    @Getter
    ElementType elementType;

    @Override
    public CardType getCardType() {
        return null;
    }

    @Override
    public boolean winsAgainst(CardInterface card) {
        return false;
    }
}
