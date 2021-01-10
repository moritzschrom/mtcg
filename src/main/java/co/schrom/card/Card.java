package co.schrom.card;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public abstract class Card implements CardInterface {
    @Getter
    int id;

    @Getter
    String name;

    @Getter
    float damage;

    @Getter
    ElementType elementType;

    @Getter
    boolean locked;

    @Override
    public boolean winsAgainst(CardInterface card) {

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
            //noinspection RedundantIfStatement
            if ("Kraken".equals(this.getName())) {
                return true;
            }

        }

        return false;
    }

    @Override
    public float calculateDamage(CardInterface card) {
        // Effectiveness only relevant for spell cards
        if (CardType.SPELL.equals(this.getCardType())) {
            // Effective (double damage)
            if ((ElementType.WATER.equals(this.getElementType()) && ElementType.FIRE.equals(card.getElementType())) ||
                    (ElementType.FIRE.equals(this.getElementType()) && ElementType.NORMAL.equals(card.getElementType())) ||
                    (ElementType.NORMAL.equals(this.getElementType()) && ElementType.WATER.equals(card.getElementType()))) {
                System.out.println(2 * this.getDamage());
                return 2 * this.getDamage();
            }

            // Not Effective
            if ((ElementType.FIRE.equals(this.getElementType()) && ElementType.WATER.equals(card.getElementType())) ||
                    (ElementType.NORMAL.equals(this.getElementType()) && ElementType.FIRE.equals(card.getElementType())) ||
                    (ElementType.WATER.equals(this.getElementType()) && ElementType.NORMAL.equals(card.getElementType()))) {
                System.out.println(this.getDamage() / 2);
                return this.getDamage() / 2;
            }
        }

        // No Effect
        System.out.println(this.getDamage());
        return this.getDamage();
    }

    public static CardInterface fromPrimitives(int id, String name, float damage, String cardTypeString, String elementTypeString, boolean locked) {
        CardType cardType;
        ElementType elementType;
        CardInterface card;

        try {
            cardType = CardType.valueOf(cardTypeString);
        } catch (IllegalArgumentException e) {
            cardType = CardType.MONSTER;
        }

        try {
            elementType = ElementType.valueOf(elementTypeString);
        } catch (IllegalArgumentException e) {
            elementType = ElementType.NORMAL;
        }

        if (CardType.MONSTER.equals(cardType)) {
            // Monster Card
            card = MonsterCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(elementType)
                    .build();
        } else {
            // Otherwise it is a Spell Card
            card = SpellCard.builder()
                    .id(id)
                    .name(name)
                    .damage(damage)
                    .elementType(elementType)
                    .build();
        }

        return card;
    }
}
