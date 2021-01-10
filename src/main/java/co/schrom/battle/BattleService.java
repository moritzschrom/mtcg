package co.schrom.battle;

import co.schrom.card.CardInterface;
import co.schrom.database.DatabaseService;
import co.schrom.user.UserInterface;
import co.schrom.user.UserService;

import java.sql.*;

public class BattleService implements BattleServiceInterface {
    private static BattleService instance;
    private final UserService userService;

    private BattleService() {
        userService = UserService.getInstance();
    }

    public static BattleService getInstance() {
        if (BattleService.instance == null) {
            BattleService.instance = new BattleService();
        }
        return BattleService.instance;
    }

    @Override
    public boolean createOrAddUserToBattle(UserInterface user) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            Statement sm = conn.createStatement();
            ResultSet rs = sm.executeQuery("SELECT id FROM battles WHERE player_a IS NULL OR player_b IS NULL LIMIT 1;");

            Battle battle;
            if (rs.next()) {
                // Get existing battle
                battle = (Battle) this.getBattle(rs.getInt(1));
            } else {
                // Create new battle
                battle = (Battle) this.addBattle(Battle.builder().build());
            }

            // Now add user to battle
            return this.addUserToBattle(user, battle);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public BattleInterface getBattle(int id) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, player_a, player_b, winner FROM battles WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                UserInterface playerA = userService.getUser(rs.getInt(2));
                UserInterface playerB = userService.getUser(rs.getInt(3));
                UserInterface winner = userService.getUser(rs.getInt(4));

                BattleInterface battle = Battle.builder()
                        .id(rs.getInt(1))
                        .playerA(playerA)
                        .playerB(playerB)
                        .winner(winner)
                        .build();

                rs.close();
                ps.close();
                conn.close();

                return battle;
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
    public BattleInterface addBattle(BattleInterface battle) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO battles VALUES(DEFAULT);", Statement.RETURN_GENERATED_KEYS);

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return this.getBattle(generatedKeys.getInt(1));
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUserToBattle(UserInterface user, BattleInterface battle_) {

        Battle battle = (Battle) this.getBattle(battle_.getId());

        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            int affectedRows;

            if (battle.getPlayerA() == null) {
                // Set user as playerA
                PreparedStatement ps = conn.prepareStatement("UPDATE battles SET player_a = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, battle.getId());
                affectedRows = ps.executeUpdate();
                ps.close();
            } else if (battle.getPlayerB() == null) {
                // Set user as playerB
                PreparedStatement ps = conn.prepareStatement("UPDATE battles SET player_b = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, battle.getId());
                affectedRows = ps.executeUpdate();
                ps.close();
            } else {
                conn.close();
                return false;
            }

            conn.close();

            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
