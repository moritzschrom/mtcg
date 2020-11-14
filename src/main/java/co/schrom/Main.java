package co.schrom;

import co.schrom.rest.RestService;

public class Main {

    public static void main(String[] args) {
        RestService.getInstance().listen(8080);
    }

}
