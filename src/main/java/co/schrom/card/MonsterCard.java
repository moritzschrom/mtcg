package co.schrom.card;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MonsterCard extends Card {
    @Getter
    CardType cardType = CardType.MONSTER;

    @Builder
    public MonsterCard(int id, String name, float damage, ElementType elementType) {
        super(id, name, damage, elementType);
        this.cardType = CardType.MONSTER;
    }
}
