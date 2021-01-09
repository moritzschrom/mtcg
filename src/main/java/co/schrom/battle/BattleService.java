package co.schrom.battle;

import co.schrom.card.CardInterface;
import co.schrom.user.UserInterface;

public class BattleService implements BattleServiceInterface {
    @Override
    public boolean createOrAddUserToBattle(UserInterface user) {
        return false;
    }

    @Override
    public boolean addBattle(BattleInterface battle) {
        return false;
    }

    @Override
    public boolean addUserToBattle(UserInterface user, BattleInterface battle) {
        return false;
    }

    @Override
    public boolean addBattleRound(BattleInterface battle, CardInterface card1, CardInterface card2) {
        return false;
    }

    @Override
    public boolean setWinnerForBattle(UserInterface winner, BattleInterface battle) {
        return false;
    }
}
