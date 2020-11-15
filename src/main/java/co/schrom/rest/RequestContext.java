package co.schrom.rest;

import co.schrom.rest.resources.MessageServlet;
import lombok.Getter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RequestContext implements RequestContextInterface {
    @Getter
    Socket socket;

    @Getter
    HttpRequest request;

    @Getter
    HttpResponse response;

    @Getter
    Map<String, Method> routes;

    public RequestContext(Socket socket) {
        this.socket = socket;
        request = new HttpRequest();
        routes = new HashMap<>() {{
            try {
                put("^GET /messages/\\d+/?$", MessageServlet.class.getDeclaredMethod("handleGet", HttpRequestInterface.class));
                put("^GET /messages/?$", MessageServlet.class.getDeclaredMethod("handleIndex", HttpRequestInterface.class));
                put("^POST /messages/?$", MessageServlet.class.getDeclaredMethod("handlePost", HttpRequestInterface.class));
                put("^PUT /messages/\\d+/?$", MessageServlet.class.getDeclaredMethod("handlePut", HttpRequestInterface.class));
                put("^DELETE /messages/\\d+/?$", MessageServlet.class.getDeclaredMethod("handleDelete", HttpRequestInterface.class));
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
