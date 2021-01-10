package co.schrom.user;

public interface UserInterface {
    int getId();

    String getStatus();

    String getUsername();

    String getPassword();

    String getToken();

    boolean authorize(String password);

    int getCoins();

    void setCoins(int coins);
}
