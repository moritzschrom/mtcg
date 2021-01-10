package co.schrom.trades;

import co.schrom.card.CardInterface;
import co.schrom.card.CardService;
import co.schrom.database.DatabaseService;
import co.schrom.user.User;
import co.schrom.user.UserService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradeService implements TradeServiceInterface {
    private static TradeService instance;
    private final CardService cardService;
    private final UserService userService;

    private TradeService() {
        cardService = CardService.getInstance();
        userService = UserService.getInstance();
    }

    public static TradeService getInstance() {
        if (TradeService.instance == null) {
            TradeService.instance = new TradeService();
        }
        return TradeService.instance;
    }

    @Override
    public TradeInterface getTrade(int id) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, card_a, card_b, coins, accepted FROM trades WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                CardInterface cardA = cardService.getCard(rs.getInt(2));
                CardInterface cardB = cardService.getCard(rs.getInt(3));

                Trade trade = Trade.builder()
                        .id((rs.getInt(1)))
                        .cardA(cardA)
                        .cardB(cardB)
                        .coins(rs.getInt(4))
                        .accepted(rs.getBoolean(5))
                        .build();

                rs.close();
                ps.close();
                conn.close();

                return trade;
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
    public List<TradeInterface> getTrades() {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id FROM trades;");
            ResultSet rs = ps.executeQuery();

            List<TradeInterface> trades = new ArrayList<>();
            while (rs.next()) {
                trades.add(this.getTrade(rs.getInt(1)));
            }

            rs.close();
            ps.close();
            conn.close();

            return trades;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TradeInterface addTrade(CardInterface card) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO trades(card_a) VALUES(?);", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, card.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                return null;
            }

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    ps.close();
                    conn.close();

                    return this.getTrade(id);
                }
            }
            ps.close();
            conn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean deleteTrade(int id) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM trades WHERE id = ?;");
            ps.setInt(1, id);

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TradeInterface addOffer(TradeInterface trade, CardInterface card, int coins) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE trades SET card_b = ?, coins = ? WHERE id = ?;");
            ps.setInt(1, card.getId());
            ps.setInt(2, coins);
            ps.setInt(3, trade.getId());

            int affectedRows = ps.executeUpdate();

            ps.close();
            conn.close();

            if (affectedRows > 0) {
                return this.getTrade(trade.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public TradeInterface acceptTrade(TradeInterface trade) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT a.user_id, b.user_id FROM trades JOIN cards a on trades.card_a = a.id JOIN cards b ON trades.card_b = b.id WHERE trades.id=?;");
            ps.setInt(1, trade.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User userA = (User) userService.getUser(rs.getInt(1));
                User userB = (User) userService.getUser(rs.getInt(2));

                cardService.addCardToUser(trade.getCardB(), userA);
                cardService.addCardToUser(trade.getCardA(), userB);

                ps = conn.prepareStatement("UPDATE trades SET accepted = TRUE WHERE id=?;");
                ps.setInt(1, trade.getId());
                int affectedRows = ps.executeUpdate();
                if (affectedRows != 0) {
                    return this.getTrade(trade.getId());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
