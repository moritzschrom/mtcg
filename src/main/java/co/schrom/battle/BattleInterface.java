package co.schrom.battle;

import co.schrom.user.UserInterface;

import java.util.List;

public interface BattleInterface {
    int getId();

    boolean isFinished();

    UserInterface getPlayerA();

    UserInterface getPlayerB();

    UserInterface getWinner();

    void startBattle();

    List<BattleRoundInterface> getBattleRounds();
}
