package co.schrom.user;

import java.util.List;

public interface UserServiceInterface {
    UserInterface getUser(int id);

    List<UserInterface> getUsers();

    UserInterface addUser(UserInterface user);

    UserInterface updateUser(int id, UserInterface user);

    boolean deleteUser(int id);
}
