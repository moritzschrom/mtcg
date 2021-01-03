package co.schrom.card;

import co.schrom.user.UserInterface;

public interface BattleInterface {
    UserInterface getPlayerA();

    UserInterface getPlayerB();

    UserInterface getWinner();

    UserInterface getLooser();

    void startBattle();
}
