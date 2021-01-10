package co.schrom.battle;

import co.schrom.card.Card;
import co.schrom.card.CardInterface;
import co.schrom.card.CardService;
import co.schrom.database.DatabaseService;
import co.schrom.user.UserInterface;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DeckService implements DeckServiceInterface {
    private static DeckService instance;

    private CardService cardService;

    private DeckService() {
        cardService = CardService.getInstance();
    }

    public static DeckService getInstance() {
        if (DeckService.instance == null) {
            DeckService.instance = new DeckService();
        }
        return DeckService.instance;
    }

    @Override
    public List<CardInterface> getDeck(UserInterface user) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT id, name, damage, card_type, element_type, is_locked FROM cards WHERE user_id = ? AND in_deck;");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            List<CardInterface> cards = new ArrayList<>();
            while (rs.next()) {
                cards.add(Card.fromPrimitives(
                        rs.getInt(1), // id
                        rs.getString(2), // name
                        rs.getFloat(3), // damage
                        rs.getString(4), // card_type
                        rs.getString(5), // element_type
                        rs.getBoolean(6))); // is_locked
            }

            rs.close();
            ps.close();
            conn.close();

            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addCardsWithIdsToDeck(int[] ids, UserInterface user) {
        List<CardInterface> userCards = cardService.getCardsForUser(user);
        List<CardInterface> newDeck = new ArrayList<>();

        // A deck can only consist of 4 cards
        if (ids.length == 4) {
            for (int id : ids) {
                // Check if the card belongs to the user
                List<CardInterface> filteredCards = userCards.stream().filter(card -> card.getId() == id).collect(Collectors.toList());
                if (filteredCards.size() == 1) {
                    CardInterface card = filteredCards.get(0);
                    newDeck.add(card);
                }
            }
            // Only when all cards belong to the user
            if (newDeck.size() == 4) {
                // Clear the deck
                this.clearDeck(user);

                // Attach new cards to the deck
                for (CardInterface card : newDeck) {
                    this.addCardToDeck(card, user);
                }
                return true;
            }
        }

        // Otherwise return false
        return false;
    }

    @Override
    public boolean addCardToDeck(CardInterface card, UserInterface user) {
        if (card.isLocked()) {
            return false;
        }

        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(id) FROM  cards WHERE user_id = ? AND in_deck;");
            ps.setInt(1, user.getId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);

                // A deck consists of a maximum of 5 cards.
                if (count >= 4) {
                    return false;
                }

                ps = conn.prepareStatement("UPDATE cards SET in_deck = TRUE, user_id = ? WHERE id = ?;");
                ps.setInt(1, user.getId());
                ps.setInt(2, card.getId());

                int affectedRows = ps.executeUpdate();

                rs.close();
                ps.close();
                conn.close();

                return affectedRows != 0;
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean clearDeck(UserInterface user) {
        try {
            Connection conn = DatabaseService.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE cards SET in_deck = FALSE WHERE user_id = ?;");
            ps.setInt(1, user.getId());

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
