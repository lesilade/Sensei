package com.sensei.config;

/**
 * Application constants.
 */
public final class Constants {

    //Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_'.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String COACH = "ROLE_COACH";
    public static final String USER = "ROLE_USER";
    public static final String TRAINEE = "ROLE_TRAINEE";
    public static final String NEWSFEED = "NEWSFEED";
    public static final String FOLLOWING = "FOLLOWING";
    public static final String FOLLOWERS = "FOLLOWERS";

    private Constants() {
    }
}
