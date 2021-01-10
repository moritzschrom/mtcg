package co.schrom.rest.resources;

import co.schrom.card.CardInterface;
import co.schrom.card.CardService;
import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.trades.Trade;
import co.schrom.trades.TradeInterface;
import co.schrom.trades.TradeService;
import co.schrom.user.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TradeServlet extends HttpServlet {

    TradeService tradeService;
    CardService cardService;
    Gson gson;
    Pattern pattern;

    public TradeServlet() {
        gson = new Gson();
        pattern = Pattern.compile("/trades/(\\d+)/?");
        this.tradeService = TradeService.getInstance();
        this.cardService = CardService.getInstance();
    }

    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        // Only authorized users can view their trades
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        List<TradeInterface> trades = tradeService.getTrades();

        return HttpResponse.builder()
                .headers(new HashMap<>() {{
                    put("Content-Type", "application/json");
                }})
                .body(gson.toJson(trades))
                .build();
    }

    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        // Only authorized users can create trades
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();

        JsonObject jsonObject = JsonParser.parseString(request.getBody()).getAsJsonObject();
        if (jsonObject.has("cardA")) {
            int id = jsonObject.get("cardA").getAsInt();
            List<CardInterface> userCards = cardService.getCardsForUser(user);
            List<CardInterface> filteredCards = userCards.stream().filter(card -> card.getId() == id).collect(Collectors.toList());
            if (filteredCards.size() > 0) {
                Trade trade = (Trade) tradeService.addTrade(filteredCards.get(0));

                if (trade != null) {
                    return HttpResponse.builder()
                            .headers(new HashMap<>() {{
                                put("Content-Type", "application/json");
                            }})
                            .statusCode(201)
                            .body(gson.toJson(trade))
                            .build();
                }
            }
        }

        return HttpResponse.badRequest();
    }

    public HttpResponseInterface handlePostOffer(HttpRequestInterface request) {
        // Only authorized users can offer on trades
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();

        Matcher m = pattern.matcher(request.getPath());
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));
            Trade trade = (Trade) tradeService.getTrade(id);

            if (trade != null) {

                JsonObject jsonObject = JsonParser.parseString(request.getBody()).getAsJsonObject();

                if (jsonObject.has("cardB") || jsonObject.has("coins")) {

                    int cardId = jsonObject.has("cardB") ? jsonObject.get("cardB").getAsInt() : 0;
                    int coins = jsonObject.has("coins") ? jsonObject.get("coins").getAsInt() : 0;

                    List<CardInterface> userCards = cardService.getCardsForUser(user);
                    List<CardInterface> filteredCards = userCards.stream().filter(card -> card.getId() == cardId).collect(Collectors.toList());

                    if (filteredCards.size() > 0) {
                        trade = (Trade) tradeService.addOffer(trade, filteredCards.get(0), coins);

                        if (trade != null) {
                            return HttpResponse.builder()
                                    .headers(new HashMap<>() {{
                                        put("Content-Type", "application/json");
                                    }})
                                    .body(gson.toJson(trade))
                                    .build();
                        }
                    }
                }
            }
        }

        return HttpResponse.badRequest();
    }

    public HttpResponseInterface handlePostAccept(HttpRequestInterface request) {
        // Only authorized users can accept trades
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();

        Matcher m = Pattern.compile("/trades/(\\d+)/accept/?").matcher(request.getPath());
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));
            Trade trade = (Trade) tradeService.getTrade(id);

            if (trade != null && trade.getCardA() != null && (trade.getCardB() != null || trade.getCoins() > 0)) {
                List<CardInterface> userCards = cardService.getCardsForUser(user);

                Trade finalTrade = trade;
                List<CardInterface> filteredCards = userCards.stream().filter(card -> card.getId() == finalTrade.getCardA().getId()).collect(Collectors.toList());

                if (filteredCards.size() > 0) {
                    trade = (Trade) tradeService.acceptTrade(trade);

                    if (trade != null) {
                        return HttpResponse.builder()
                                .headers(new HashMap<>() {{
                                    put("Content-Type", "application/json");
                                }})
                                .body(gson.toJson(trade))
                                .build();
                    }
                }
            }
        }

        return HttpResponse.badRequest();
    }

    @Override
    public HttpResponseInterface handleDelete(HttpRequestInterface request) {
        // Only authorized users can delete trades
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();

        Matcher m = pattern.matcher(request.getPath());
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));

            Trade trade = (Trade) tradeService.getTrade(id);

            if (trade != null) {
                List<CardInterface> userCards = cardService.getCardsForUser(user);
                List<CardInterface> filteredCards = userCards.stream().filter(card -> card.getId() == trade.getCardA().getId()).collect(Collectors.toList());
                if (filteredCards.size() > 0) {
                    if (tradeService.deleteTrade(trade.getId())) {
                        return HttpResponse.ok();
                    }
                }
            }
        }

        return HttpResponse.badRequest();
    }
}
