package co.schrom.user;

public interface UserInterface {
    String getUsername();

    String getPassword();

    boolean authorize(String password);
}
