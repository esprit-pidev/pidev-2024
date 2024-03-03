package tn.esprit.services.userServices;

import java.util.prefs.Preferences;

public class UserSession {
    public static final RememberMeTokenService tokenService = new RememberMeTokenService();
    public static UserSession CURRENT_USER;
    private static AuthResponseDTO user_LoggedIn;

    public UserSession(AuthResponseDTO user_LoggedIn ) {
        this.user_LoggedIn = user_LoggedIn;
    }

    public static UserSession getSameInstance(AuthResponseDTO user_LoggedIn) {
        if(CURRENT_USER == null) {
            CURRENT_USER = new UserSession(user_LoggedIn);
        }
        return CURRENT_USER;
    }

    public static AuthResponseDTO getUser_LoggedIn() {
        return user_LoggedIn;
    }

    public static void Logout() {
        Preferences prefs = Preferences.userRoot().node("com/myapp");
        prefs.remove("rememberMeToken");
        tokenService.deleteByUser(user_LoggedIn);
        user_LoggedIn = null;
        CURRENT_USER = null;

    }

    @Override
    public String toString() {
        return "UserSession{" +
                "_this_user='" + user_LoggedIn + '\'' +
                ", privileges="  +
                '}';
    }
}
