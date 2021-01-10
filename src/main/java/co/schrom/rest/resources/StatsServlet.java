package co.schrom.rest.resources;

import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.stats.Stats;
import co.schrom.stats.StatsService;
import co.schrom.user.User;
import com.google.gson.Gson;

import java.util.HashMap;

public class StatsServlet extends HttpServlet {

    StatsService statsService;
    Gson gson;

    public StatsServlet() {
        gson = new Gson();
        this.statsService = StatsService.getInstance();
    }

    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        // Only authorized users can view their stats
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();
        Stats stats = (Stats) statsService.getStatsForUser(user);

        return HttpResponse.builder()
                .headers(new HashMap<>() {{
                    put("Content-Type", "application/json");
                }})
                .body(gson.toJson(stats))
                .build();
    }
}
