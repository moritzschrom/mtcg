package co.schrom.user;

import com.google.common.hash.Hashing;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;

@Builder(toBuilder = true)
public class User implements UserInterface {

    @Getter
    int id;

    @Getter
    String username;

    @Getter
    String password;

    @Getter
    String token;

    @Getter
    @Setter
    @Builder.Default
    int coins = 20;

    @Override
    public boolean authorize(String password) {
        //noinspection UnstableApiUsage
        String passwordHash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        return passwordHash.equals(getPassword());
    }
}
