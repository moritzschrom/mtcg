package co.schrom.card;

public interface CardInterface {
    String getName();

    float getDamage();

    CardType getCardType();

    ElementType getElementType();

    boolean winsAgainst(CardInterface card);
}