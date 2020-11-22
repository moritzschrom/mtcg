package co.schrom.user;

import java.util.List;

public class UserService implements UserServiceInterface {
    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (UserService.instance == null) {
            UserService.instance = new UserService();
        }
        return UserService.instance;
    }

    @Override
    public UserInterface getUser(int id) {
        return null;
    }

    @Override
    public List<UserInterface> getUsers() {
        return null;
    }

    @Override
    public UserInterface addUser(UserInterface user) {
        return null;
    }

    @Override
    public UserInterface updateUser(int id, UserInterface user) {
        return null;
    }

    @Override
    public boolean deleteUser(int id) {
        return false;
    }
}
