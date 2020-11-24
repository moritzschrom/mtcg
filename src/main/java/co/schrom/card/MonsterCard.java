package co.schrom.card;

import lombok.Builder;
import lombok.Getter;

public class MonsterCard extends Card {
    @Getter
    @Builder.Default
    CardType cardType = CardType.MONSTER;
}
