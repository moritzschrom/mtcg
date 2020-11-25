package co.schrom.card;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MonsterCard extends Card {
    @Getter
    CardType cardType = CardType.MONSTER;

    @Builder
    public MonsterCard(String name, float damage, ElementType elementType, CardType cardType) {
        super(name, damage, elementType);
        this.cardType = CardType.MONSTER;
    }
}
