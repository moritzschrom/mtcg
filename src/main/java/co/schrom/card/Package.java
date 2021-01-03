package co.schrom.card;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class Package implements PackageInterface {
    @Getter
    List<CardInterface> cards;

    @Getter
    int price;
}
