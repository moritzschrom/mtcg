package co.schrom.stats;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Stats implements StatsInterface {
    @Getter
    int totalBattles;

    @Getter
    int wonBattles;

    @Getter
    int lostBattles;
}
