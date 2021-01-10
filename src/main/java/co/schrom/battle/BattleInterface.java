package co.schrom.battle;

import co.schrom.user.UserInterface;

public interface BattleInterface {
    int getId();

    UserInterface getPlayerA();

    UserInterface getPlayerB();

    UserInterface getWinner();

    UserInterface getLooser();

    void startBattle();
}
