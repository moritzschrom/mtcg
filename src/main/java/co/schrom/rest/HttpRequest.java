package co.schrom.rest;

import co.schrom.user.User;
import co.schrom.user.UserInterface;
import co.schrom.user.UserService;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpRequest implements HttpRequestInterface {
    @Getter
    String version;

    @Getter
    String method;

    @Getter
    String path;

    @Getter
    Map<String, String> params;

    @Getter
    HashMap<String, String> headers;

    @Getter
    String body;

    @Getter
    UserInterface authUser;

    public HttpRequest() {
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
    }

    @Override
    public void read(BufferedReader reader) {
        try {
            StringBuilder sb = new StringBuilder();
            int s;

            while ((s = reader.read()) != -1) {
                sb.append((char) s);
                if (!reader.ready()) break;
            }

            String[] lines = sb.toString().split("\n");
            sb = new StringBuilder();
            int lineCount = 0;
            boolean contentReached = false;

            for (String line : lines) {
                // First line
                if (lineCount == 0) {
                    String[] segments = line.split(" ");
                    if (segments.length >= 3) {
                        // HTTP Method
                        method = segments[0].toUpperCase().trim();

                        // Path: /path?param1=value1&param2=value2
                        // Non-escaped RegEx: ([^\n\r\?]+)(\??)(.*)
                        // noinspection RegExpRedundantEscape
                        Matcher m = Pattern.compile("([^\\n\\r\\?]+)(?:\\??)(.*)").matcher(segments[1]);
                        // Group 1: /path
                        if (m.matches() && m.groupCount() >= 1) {
                            path = m.group(1);
                        }
                        // Group 2: param1=value1&param2=value2
                        if (m.groupCount() >= 2) {
                            String[] queries = m.group(2).split("&");
                            for (String query : queries) {
                                String[] queryParts = query.split("=");
                                if (queryParts.length == 2) {
                                    params.put(queryParts[0].trim(), queryParts[1].trim());
                                }
                            }
                        }

                        // HTTP Version
                        version = segments[2].trim();
                    }
                }

                // Headers
                else if (!contentReached && !"\n".equals(line) && !"\r".equals(line)) {
                    String[] segments = line.split(": ");
                    if (segments.length == 2) {
                        headers.put(segments[0].trim(), segments[1].trim());
                    }
                }

                // Body
                else {
                    contentReached = true;
                    if (!"\n".equals(line) && !"\r".equals(line)) {
                        sb.append(line).append("\n");
                        body = sb.toString(); // Rewrite the body after every new line
                        body = body.substring(0, body.length() - 1); // Remove the last \n
                    }
                }

                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void authorizeRequest() {
        String authorizationHeader = headers.get("Authorization");
        if (authorizationHeader != null) {
            String token = authorizationHeader.replace("Basic ", "");
            String[] parts = token.split("_");
            if (parts.length == 2) {
                User user = (User) UserService.getInstance().getUserByUsername(parts[0]);
                if (user != null && token.equals(user.getToken())) {
                    authUser = user;
                }
            }
        }
    }

}
