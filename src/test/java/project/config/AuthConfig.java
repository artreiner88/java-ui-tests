package project.config;

import org.aeonbits.owner.Config;

@Config.Sources({
        "file:/tmp/auth.properties",
        "classpath:config/credentials.properties"
})
public interface AuthConfig extends Config {

    @Key("username")
    String username();

    @Key("password")
    String password();

    @Key("nonExistingUser")
    String nonExistingUser();

    @Key("lockedOutUser")
    String lockedOutUser();

    @Key("wrongPassword")
    String wrongPassword();
}
