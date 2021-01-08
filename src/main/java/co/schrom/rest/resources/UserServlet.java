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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServlet extends HttpServlet {

    UserService userService;
    Gson g;
    Pattern p;

    public UserServlet() {
        g = new Gson();
        p = Pattern.compile("/users/([a-zA-Z]+)/?");
        this.userService = UserService.getInstance();
    }

    @Override
    public HttpResponseInterface handleGet(HttpRequestInterface request) {
        if (request.getAuthUser() == null) {
            return HttpResponse.unauthorized();
        }

        Matcher m = p.matcher(request.getPath());
        if (m.matches()) {
            String username = m.group(1);

            User user = (User) userService.getUserByUsername(username);
            if (user == null) {
                return HttpResponse.notFound();
            }

            if (request.getAuthUser().getId() != user.getId()) {
                return HttpResponse.forbidden();
            }
            // Hide password and token in REST response.
            user = user.toBuilder().password(null).token(null).build();

            if (user != null) {
                return HttpResponse.builder()
                        .headers(new HashMap<>() {{
                            put("Content-Type", "application/json");
                        }})
                        .body(g.toJson(user))
                        .build();
            }
        }
        return HttpResponse.notFound();
    }

    @Override
    public HttpResponseInterface handlePut(HttpRequestInterface request) {
        if (request.getAuthUser() == null) {
            return HttpResponse.unauthorized();
        }

        Matcher m = p.matcher(request.getPath());
        if (m.matches()) {
            String username = m.group(1);

            User user = (User) userService.getUserByUsername(username);
            if (user == null) {
                return HttpResponse.notFound();
            }

            if (request.getAuthUser().getId() != user.getId()) {
                return HttpResponse.forbidden();
            }

            User newUser = g.fromJson(request.getBody(), User.class);
            newUser = newUser.toBuilder()
                    .coins(user.getCoins())
                    .username(user.getUsername())
                    .password(Hashing.sha256().hashString(newUser.getPassword(), StandardCharsets.UTF_8).toString())
                    .build();

            user = (User) userService.updateUser(user.getId(), newUser);
            if (user != null) {
                return HttpResponse.builder()
                        .headers(new HashMap<>() {{
                            put("Content-Type", "application/json");
                        }})
                        .body(g.toJson(user))
                        .build();
            }
        }
        return HttpResponse.internalServerError();
    }

    @Override
    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        User user = g.fromJson(request.getBody(), User.class);

        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            user.setCoins(20);
            //Generate a pseudo random token
            //noinspection UnstableApiUsage
            user = (User) userService.addUser(
                    user.toBuilder()
                            .token(user.getUsername() + "_" + Hashing.sha256().hashString(user.getUsername() + new Date().getTime() + Math.random(), StandardCharsets.UTF_8).toString())
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

    public HttpResponseInterface handleDelete(HttpRequestInterface request) {
        if (request.getAuthUser() == null) {
            return HttpResponse.unauthorized();
        }

        Matcher m = p.matcher(request.getPath());
        if (m.matches()) {
            String username = m.group(1);

            User user = (User) userService.getUserByUsername(username);
            if (user == null) {
                return HttpResponse.notFound();
            }

            if (request.getAuthUser().getId() != user.getId()) {
                return HttpResponse.forbidden();
            }
            boolean result = userService.deleteUser(user.getId());
            if (result) {
                return HttpResponse.ok();
            }
            return HttpResponse.internalServerError();
        }
        return HttpResponse.notFound();
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
