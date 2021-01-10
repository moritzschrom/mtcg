package co.schrom.stats;

import co.schrom.user.UserInterface;

public interface StatsServiceInterface {
    StatsInterface getStatsForUser(UserInterface user);

    StatsInterface addStatForUser(UserInterface user, int stat);
}
