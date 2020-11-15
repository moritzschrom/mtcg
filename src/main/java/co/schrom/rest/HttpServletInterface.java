package co.schrom.rest;

public interface HttpServletInterface {
    HttpResponseInterface handleIndex(HttpRequestInterface request);

    HttpResponseInterface handleGet(HttpRequestInterface request);

    HttpResponseInterface handlePost(HttpRequestInterface request);

    HttpResponseInterface handlePut(HttpRequestInterface request);

    HttpResponseInterface handlePatch(HttpRequestInterface request);

    HttpResponseInterface handleDelete(HttpRequestInterface request);
}
