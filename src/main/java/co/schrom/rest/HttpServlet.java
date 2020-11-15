package co.schrom.rest;

public abstract class HttpServlet implements HttpServletInterface {
    @Override
    public HttpResponseInterface handleIndex(HttpRequestInterface request) {
        return HttpResponse.notImplemented();
    }

    @Override
    public HttpResponseInterface handleGet(HttpRequestInterface request) {
        return HttpResponse.notImplemented();
    }

    @Override
    public HttpResponseInterface handlePost(HttpRequestInterface request) {
        return HttpResponse.notImplemented();
    }

    @Override
    public HttpResponseInterface handlePut(HttpRequestInterface request) {
        return HttpResponse.notImplemented();
    }

    @Override
    public HttpResponseInterface handlePatch(HttpRequestInterface request) {
        return HttpResponse.notImplemented();
    }

    @Override
    public HttpResponseInterface handleDelete(HttpRequestInterface request) {
        return HttpResponse.notImplemented();
    }
}
