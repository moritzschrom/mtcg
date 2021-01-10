package co.schrom.battle;

import co.schrom.user.UserInterface;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
public class Battle implements BattleInterface {
    @Getter
    int id;

    @Getter
    boolean finished;

    @Getter
    UserInterface playerA;

    @Getter
    UserInterface playerB;

    @Getter
    UserInterface winner;

    @Getter
    List<BattleRoundInterface> battleRounds;

    @Override
    public void startBattle() {

    }
}
