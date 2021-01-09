package co.schrom.battle;

import co.schrom.card.CardInterface;
import co.schrom.user.UserInterface;

public interface BattleServiceInterface {

    boolean createOrAddUserToBattle(UserInterface user);

    boolean addBattle(BattleInterface battle);

    boolean addUserToBattle(UserInterface user, BattleInterface battle);

    boolean addBattleRound(BattleInterface battle, CardInterface card1, CardInterface card2);

    boolean setWinnerForBattle(UserInterface winner, BattleInterface battle);
}
