package co.schrom.rest.resources;

import co.schrom.messages.Message;
import co.schrom.messages.MessageService;
import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageServlet extends HttpServlet {

    MessageService messageService;
    Gson g;
    Pattern p;

    public MessageServlet() {
        g = new Gson();
        p = Pattern.compile("/messages/(\\d+)/?");
        this.messageService = MessageService.getInstance();
    }

    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        return HttpResponse.builder()
                .headers(new HashMap<>() {{
                    put("Content-Type", "application/json");
                }})
                .body(g.toJson(messageService.getMessages()))
                .build();
    }

    @Override
    public HttpResponseInterface handleGet(HttpRequestInterface request) {
        Matcher m = p.matcher(request.getPath());
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));
            Message message = (Message) messageService.getMessage(id);
            if (message != null) {
                return HttpResponse.builder()
                        .headers(new HashMap<>() {{
                            put("Content-Type", "application/json");
                        }})
                        .body(g.toJson(message))
                        .build();
            }
        }
        return HttpResponse.notFound();
    }

    @Override
    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        Message message = g.fromJson(request.getBody(), Message.class);
        message.setId(messageService.nextId());
        messageService.addMessage(message);
        return HttpResponse.builder().body(g.toJson(message)).build();
    }

    @Override
    public HttpResponseInterface handlePut(HttpRequestInterface request) {
        Matcher m = p.matcher(request.getPath());
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));

            Message newMessage = g.fromJson(request.getBody(), Message.class);
            newMessage.setId(id);

            if (messageService.replaceMessage(id, newMessage)) {
                return HttpResponse.builder()
                        .headers(new HashMap<>() {{
                            put("Content-Type", "application/json");
                        }})
                        .body(g.toJson(newMessage))
                        .build();
            }
        }
        return HttpResponse.notFound();
    }

    @Override
    public HttpResponseInterface handleDelete(HttpRequestInterface request) {
        Matcher m = p.matcher(request.getPath());
        if (m.matches()) {
            int id = Integer.parseInt(m.group(1));
            if (messageService.removeMessage(id)) {
                return HttpResponse.ok();
            }
        }
        return HttpResponse.notFound();
    }
}
