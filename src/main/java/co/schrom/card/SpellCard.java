package co.schrom.card;

import lombok.Builder;
import lombok.Getter;

public class SpellCard extends Card {
    @Getter
    @Builder.Default
    CardType cardType = CardType.SPELL;
}
