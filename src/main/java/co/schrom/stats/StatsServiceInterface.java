package co.schrom.stats;

import co.schrom.user.UserInterface;
import com.google.gson.JsonArray;

public interface StatsServiceInterface {
    StatsInterface getStatsForUser(UserInterface user);

    StatsInterface addStatForUser(UserInterface user, int stat);

    boolean updateEloForPlayers(UserInterface playerA, UserInterface playerB, double pointsA, double pointsB);

    JsonArray getScoreboard();
}
