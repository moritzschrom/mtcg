package co.schrom.card;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SpellCard extends Card {
    @Getter
    CardType cardType = CardType.SPELL;

    @Builder
    public SpellCard(int id, String name, float damage, ElementType elementType, CardType cardType) {
        super(id, name, damage, elementType);
        this.cardType = CardType.SPELL;
    }
}
