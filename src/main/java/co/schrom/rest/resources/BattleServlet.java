package co.schrom.rest.resources;

import co.schrom.battle.Battle;
import co.schrom.battle.BattleService;
import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.user.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.SneakyThrows;

import java.util.HashMap;

public class BattleServlet extends HttpServlet {

    BattleService battleService;
    Gson gson;

    public BattleServlet() {
        gson = new GsonBuilder().excludeFieldsWithModifiers().create();
        this.battleService = BattleService.getInstance();
    }

    @SneakyThrows
    @Override
    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        // Only authorized users can battle
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();

        Battle battle = (Battle) battleService.createOrAddUserToBattle(user);
        Battle battleResult = (Battle) battleService.waitForBattleToFinish(battle);

        if (battleResult != null) {
            return HttpResponse.builder()
                    .headers(new HashMap<>() {{
                        put("Content-Type", "application/json");
                    }})
                    .body(gson.toJson(battleResult))
                    .build();
        }

        return HttpResponse.internalServerError();
    }
}
