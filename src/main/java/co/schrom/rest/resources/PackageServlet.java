package co.schrom.rest.resources;

import co.schrom.card.Package;
import co.schrom.card.*;
import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PackageServlet extends HttpServlet {

    PackageService packageService;
    CardService cardService;
    Gson gson;

    public PackageServlet() {
        gson = new Gson();
        this.packageService = PackageService.getInstance();
        this.cardService = CardService.getInstance();
    }

    public HttpResponseInterface handlePost(HttpRequestInterface request) {

        // Only admins can create card packages.
        if (request.getAuthUser() == null || !"admin".equalsIgnoreCase(request.getAuthUser().getUsername())) {
            return HttpResponse.unauthorized();
        }

        Package cardPackage = (Package) packageService.addPackage(gson.fromJson(request.getBody(), Package.class));

        if (cardPackage != null) {

            JsonObject jsonObject = JsonParser.parseString(request.getBody()).getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("cards");
            List<CardInterface> cards = new ArrayList<>();

            for (JsonElement cardJsonElement : jsonArray) {
                JsonObject cardJson = cardJsonElement.getAsJsonObject();

                CardInterface card = cardService.addCard(Card.fromPrimitives(
                        0,
                        cardJson.get("name").getAsString(),
                        cardJson.get("damage").getAsFloat(),
                        cardJson.get("cardType").getAsString(),
                        cardJson.get("elementType").getAsString()
                ));

                card = cardService.addCardToPackage(card, cardPackage);

                cards.add(card);
            }

            JsonObject returnJsonObject = (JsonObject) gson.toJsonTree(cardPackage);
            returnJsonObject.add("cards", gson.toJsonTree(cards));

            return HttpResponse.builder()
                    .headers(new HashMap<>() {{
                        put("Content-Type", "application/json");
                    }})
                    .statusCode(201)
                    .body(gson.toJson(returnJsonObject))
                    .build();
        }
        return HttpResponse.internalServerError();
    }
}
