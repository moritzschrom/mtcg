package co.schrom.rest;

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

    public HttpRequest() {
        this.params = new HashMap<>();
        this.headers = new HashMap<>();
    }

    @Override
    public void read(BufferedReader reader) {
        try {
            StringBuilder sb = new StringBuilder();
            int lineCount = 0;
            boolean contentReached = false;

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                // First line
                if (lineCount == 0) {
                    String[] segments = line.split(" ");
                    if (segments.length >= 3) {
                        // HTTP Method
                        method = segments[0].toUpperCase().trim();

                        // Path: /path?param1=value1&param2=value2
                        // Non-escaped RegEx: ([^\n\r\?]+)(\??)(.*)
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
                else if (!contentReached && !"".equals(line)) {
                    String[] segments = line.split(": ");
                    if (segments.length == 2) {
                        headers.put(segments[0].trim(), segments[1].trim());
                    }
                }

                // Body
                else {
                    contentReached = true;
                    if (!"".equals(line)) {
                        sb.append(line).append("\n");
                        body = sb.toString(); // Rewrite the body after every new line
                    }
                }

                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
