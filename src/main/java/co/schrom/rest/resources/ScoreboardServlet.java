package co.schrom.rest.resources;

import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.stats.StatsService;
import com.google.gson.Gson;

import java.util.HashMap;

public class ScoreboardServlet extends HttpServlet {

    StatsService statsService;
    Gson gson;

    public ScoreboardServlet() {
        gson = new Gson();
        this.statsService = StatsService.getInstance();
    }

    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        // Only authorized users can view the scoreboard
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        return HttpResponse.builder()
                .headers(new HashMap<>() {{
                    put("Content-Type", "application/json");
                }})
                .body(gson.toJson(statsService.getScoreboard()))
                .build();
    }
}
