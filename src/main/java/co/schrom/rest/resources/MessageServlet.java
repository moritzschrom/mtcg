package co.schrom.rest.resources;

import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;

public class MessageServlet extends HttpServlet {

    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        return HttpResponse.builder().body("handleIndex()").build();
    }

    @Override
    public HttpResponseInterface handleGet(HttpRequestInterface request) {
        return HttpResponse.builder().body("handleGet()").build();
    }

    @Override
    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        return HttpResponse.builder().body("handlePost()").build();
    }

    @Override
    public HttpResponseInterface handlePut(HttpRequestInterface request) {
        return HttpResponse.builder().body("handlePut()").build();

    }

    @Override
    public HttpResponseInterface handlePatch(HttpRequestInterface request) {
        return HttpResponse.builder().body("handlePatch()").build();
    }

    @Override
    public HttpResponseInterface handleDelete(HttpRequestInterface request) {
        return HttpResponse.builder().body("handleDelete()").build();
    }
}
