package co.schrom.card;

import java.util.List;

public interface PackageInterface {
    List<CardInterface> getCards();

    int getPrice();

    String getName();
}
