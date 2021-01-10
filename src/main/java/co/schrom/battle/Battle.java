package co.schrom.battle;

import co.schrom.user.UserInterface;
import lombok.Builder;
import lombok.Getter;

@Builder
public class Battle implements BattleInterface {
    @Getter
    int id;

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
