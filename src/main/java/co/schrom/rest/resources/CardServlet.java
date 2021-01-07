package co.schrom.rest.resources;

import co.schrom.card.CardInterface;
import co.schrom.card.CardService;
import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.user.User;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class CardServlet extends HttpServlet {

    CardService cardService;
    Gson g;

    public CardServlet() {
        g = new Gson();
        this.cardService = CardService.getInstance();
    }

    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        // Only authorized users can view their cards
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();
        List<CardInterface> cards = cardService.getCardsForUser(user);

        return HttpResponse.builder()
                .headers(new HashMap<>() {{
                    put("Content-Type", "application/json");
                }})
                .body(g.toJson(cards))
                .build();
    }
}
