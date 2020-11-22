package co.schrom.user;

import com.google.common.hash.Hashing;
import lombok.Builder;
import lombok.Getter;

import java.nio.charset.StandardCharsets;

@Builder
public class User implements UserInterface {

    @Getter
    String username;

    @Getter
    String password;

    @Override
    public boolean authorize(String password) {
        @SuppressWarnings("UnstableApiUsage")
        String passwordHash = Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();

        return passwordHash.equals(getPassword());
    }
}
