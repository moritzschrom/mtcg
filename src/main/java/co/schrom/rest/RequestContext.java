package co.schrom.rest;

import co.schrom.rest.resources.*;
import lombok.Getter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestContext implements RequestContextInterface {
    @Getter
    SocketWrapper socket;

    @Getter
    HttpRequest request;

    @Getter
    HttpResponse response;

    @Getter
    Map<String, Method> routes;

    public RequestContext(SocketWrapperInterface socket) {
        this.socket = (SocketWrapper) socket;
        request = new HttpRequest();
        routes = new HashMap<>() {{
            try {
                put("^GET /messages/\\d+/?$", MessageServlet.class.getDeclaredMethod("handleGet", HttpRequestInterface.class));
                put("^GET /messages/?$", MessageServlet.class.getDeclaredMethod("handleIndex", HttpRequestInterface.class));
                put("^POST /messages/?$", MessageServlet.class.getDeclaredMethod("handlePost", HttpRequestInterface.class));
                put("^PUT /messages/\\d+/?$", MessageServlet.class.getDeclaredMethod("handlePut", HttpRequestInterface.class));
                put("^DELETE /messages/\\d+/?$", MessageServlet.class.getDeclaredMethod("handleDelete", HttpRequestInterface.class));

                put("^POST /users/?$", UserServlet.class.getDeclaredMethod("handlePost", HttpRequestInterface.class));
                put("^GET /users/\\d+/?$", UserServlet.class.getDeclaredMethod("handleGet", HttpRequestInterface.class));
                put("^DELETE /users/\\d+/?$", UserServlet.class.getDeclaredMethod("handleDelete", HttpRequestInterface.class));
                put("^POST /login/?$", UserServlet.class.getDeclaredMethod("handleLogin", HttpRequestInterface.class));

                put("^POST /packages/?$", PackageServlet.class.getDeclaredMethod("handlePost", HttpRequestInterface.class));

                put("^POST /transactions/packages/?$", TransactionServlet.class.getDeclaredMethod("handleAcquirePackage", HttpRequestInterface.class));

                put("^GET /cards/?$", CardServlet.class.getDeclaredMethod("handleIndex", HttpRequestInterface.class));

                put("^GET /deck/?$", DeckServlet.class.getDeclaredMethod("handleIndex", HttpRequestInterface.class));
                put("^PUT /deck/?$", DeckServlet.class.getDeclaredMethod("handlePut", HttpRequestInterface.class));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }};

        handleSocket();
    }

    @Override
    public void handleSocket() {
        try {
            // Read the InputStream
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            request.read(reader);
            request.authorizeRequest();

            // Resolve the method for the route
            Method method = resolveRoute(request);
            if (method != null) {
                // Try to invoke the resolved method
                try {
                    response = (HttpResponse) method.invoke(method.getDeclaringClass().getConstructor().newInstance(), request);
                } catch (InstantiationException | NoSuchMethodException e) {
                    // Error 500 - Internal Server Error
                    response = HttpResponse.internalServerError();
                    e.printStackTrace();
                }
            } else {
                // Error 404 - Not Found
                response = HttpResponse.notFound();
            }

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            response.write(writer);
        } catch (IOException | IllegalAccessException | InvocationTargetException ignored) {
        }
    }

    public Method resolveRoute(HttpRequestInterface request) {
        if (request.getMethod() == null || request.getPath() == null) {
            return null;
        }
        String requestRoute = request.getMethod().toUpperCase() + " " + request.getPath();
        for (Map.Entry<String, Method> entry : this.routes.entrySet()) {
            if (Pattern.matches(entry.getKey(), requestRoute)) {
                return entry.getValue();
            }
        }

        return null;
    }
}
