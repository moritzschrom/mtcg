package co.schrom.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class Card implements CardInterface {
    @Getter
    String name;

    @Getter
    float damage;

    @Getter
    ElementType elementType;

    @Override
    public boolean winsAgainst(CardInterface card) {

        System.out.println(this.getCardType());

        // wrap MonsterCard vs MonsterCard
        if (CardType.MONSTER.equals(this.getCardType()) && CardType.MONSTER.equals(card.getCardType())) {

            // Dragons defeat Goblins
            if ("Dragon".equals(this.getName()) && "Goblin".equals(card.getName())) {
                return true;
            }

            // Wizards defeat Orks
            if ("Wizard".equals(this.getName()) && "Ork".equals(card.getName())) {
                return true;
            }

            // FireElves defeat Dragons
            if ("FireElve".equals(this.getName()) && "Dragon".equals(card.getName())) {
                return true;
            }
        }

        // wrap SpellCard vs MonsterCard
        if (CardType.SPELL.equals(this.getCardType()) && CardType.MONSTER.equals(card.getCardType())) {

            // WaterSpells defeat Knight
            if (ElementType.WATER.equals(this.getElementType()) && "Knight".equals(card.getName())) {
                return true;
            }
        }

        // wrap MonsterCard vs SpellCard
        if (CardType.MONSTER.equals(this.getCardType()) && CardType.SPELL.equals(card.getCardType())) {

            // Kraken defeat all Spells
            if ("Kraken".equals(this.getName())) {
                return true;
            }

        }

        return false;
    }
}
