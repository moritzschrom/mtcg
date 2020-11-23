package co.schrom.user;

public interface UserInterface {
    int getId();

    String getUsername();

    String getPassword();

    String getToken();

    boolean authorize(String password);
}
