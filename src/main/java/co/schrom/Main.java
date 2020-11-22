package co.schrom;

import co.schrom.rest.RestService;

public class Main {

    public static void main(String[] args) {
        Thread restServiceT = new Thread(new RestService(8080));
        restServiceT.start();
    }

}
