package ch.fimal.CM.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)"; // Your
                                                                                                                // secret
                                                                                                                // should
                                                                                                                // always
                                                                                                                // be
                                                                                                                // strong
                                                                                                                // (uppercase,
                                                                                                                // lowercase,
                                                                                                                // numbers,
                                                                                                                // symbols)
                                                                                                                // so
                                                                                                                // that
                                                                                                                // nobody
                                                                                                                // can
                                                                                                                // potentially
                                                                                                                // decode
                                                                                                                // the
                                                                                                                // signature.
    public static final int ACCESS_TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    // public static final int ACCESS_TOKEN_EXPIRATION = 300000; // 600000
    // milliseconds = 600 seconds = 10 minutes.
    public static final int REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7 days
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "/user/register"; // Public path that clients can use to register.
}
