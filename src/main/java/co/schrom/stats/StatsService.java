package co.schrom.stats;

import co.schrom.database.DatabaseService;
import co.schrom.user.UserInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsService implements StatsServiceInterface {
    private static StatsService instance;

    private StatsService() {

    }

    public static StatsService getInstance() {
        if (StatsService.instance == null) {
            StatsService.instance = new StatsService();
        }
        return StatsService.instance;
    }

    @Override
    public StatsInterface getStatsForUser(UserInterface user) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT total_battles, won_battles, lost_battles FROM users WHERE id = ?;");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Stats stats = Stats.builder().totalBattles(rs.getInt(1)).wonBattles(rs.getInt(2)).lostBattles(rs.getInt(3)).build();

                rs.close();
                ps.close();
                conn.close();

                return stats;
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public StatsInterface addStatForUser(UserInterface user, int stat) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps;

            if (stat > 0) {
                // Win
                ps = conn.prepareStatement("UPDATE users SET won_battles = won_battles+1, total_battles = total_battles+1 WHERE id = ?;");
            } else if (stat < 0) {
                // Loss
                ps = conn.prepareStatement("UPDATE users SET lost_battles = lost_battles+1, total_battles = total_battles+1 WHERE id = ?;");
            } else {
                // Tie
                ps = conn.prepareStatement("UPDATE users SET total_battles = total_battles+1 WHERE id = ?;");
            }

            ps.setInt(1, user.getId());

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows > 0) {
                return this.getStatsForUser(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
