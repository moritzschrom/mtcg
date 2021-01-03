package co.schrom.card;

import co.schrom.user.UserInterface;
import lombok.Getter;

public class Battle implements BattleInterface {
    @Getter
    UserInterface playerA;

    @Getter
    UserInterface playerB;

    @Getter
    UserInterface winner;

    @Getter
    UserInterface looser;

    @Override
    public void startBattle() {

    }
}
