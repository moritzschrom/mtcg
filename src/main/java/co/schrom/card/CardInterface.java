package co.schrom.card;

public interface CardInterface {
    int getId();

    String getName();

    float getDamage();

    CardType getCardType();

    ElementType getElementType();

    boolean isLocked();

    boolean winsAgainst(CardInterface card);
}
