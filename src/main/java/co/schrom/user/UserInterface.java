package co.schrom.user;

public interface UserInterface {

    String getUsername();

    String getPasswordHash();

    boolean authorize(String password);

}
