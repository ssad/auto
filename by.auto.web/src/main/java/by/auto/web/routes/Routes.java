package by.auto.web.routes;

public class Routes {
    public static final String HOME = "/";
    public static final String HELP = "/help";
    public static final String SIGNIN = "/signin";
    public static final String SIGNOUT = "/signout";
    public static final String ERRORS = "/errors";
    public static final String ERROR_404 = ERRORS + "/404";

    public static class Signup {
        public static final String SIGNUP = "/signup";
    }

    public static class Profile {
        public static final String PROFILE = "/profile";
        public static final String FORGOT_PASSWORD = "/forgotpassword";
    }
    public static class Api {
        public static final String API = "/api";
        public static final String API_SIGNUP = API + "/signup";
        public static final String API_ACCOUNTS = API + "/accounts";
        public static final String API_FORGOT_PASSWORD = API_ACCOUNTS + "/token";


    }
}
