package co.schrom.rest.resources;

import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.user.User;
import co.schrom.user.UserService;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

public class UserServlet extends HttpServlet {

    UserService userService;
    Gson g;

    public UserServlet() {
        g = new Gson();
        this.userService = UserService.getInstance();
    }

    @Override
    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        User user = g.fromJson(request.getBody(), User.class);

        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            //Generate a pseudo random token
            //noinspection UnstableApiUsage
            user = (User) userService.addUser(
                    user.toBuilder()
                            .token(Hashing.sha256().hashString(user.getUsername() + new Date().getTime() + Math.random(), StandardCharsets.UTF_8).toString())
                            .password(Hashing.sha256().hashString(user.getPassword(), StandardCharsets.UTF_8).toString())
                            .build());

            if (user != null) {
                return HttpResponse.builder()
                        .statusCode(201)
                        .reasonPhrase("Created")
                        .headers(new HashMap<>() {{
                            put("Content-Type", "application/json");
                        }})
                        .body(g.toJson(user))
                        .build();
            }
        }
        return HttpResponse.internalServerError();
    }

    public HttpResponseInterface handleLogin(HttpRequestInterface request) {
        Properties data = g.fromJson(request.getBody(), Properties.class);
        String username = data.getProperty("username");
        String password = data.getProperty("password");
        if (username != null && password != null) {
            User user = (User) userService.getUserByUsername(username);
            if (user.authorize(password)) {
                return HttpResponse.builder()
                        .statusCode(200)
                        .reasonPhrase("OK")
                        .headers(new HashMap<>() {{
                            put("Content-Type", "application/json");
                        }})
                        .body(g.toJson(user.getToken()))
                        .build();
            }
        }
        return HttpResponse.unauthorized();
    }
}
