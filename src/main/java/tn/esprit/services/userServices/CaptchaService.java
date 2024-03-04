package tn.esprit.services.userServices;

public class CaptchaService {

    private static boolean isCaptchaCorrect = true;

    public static boolean isCaptchaCorrect() {
        return isCaptchaCorrect;
    }

    public static void setCaptchaCorrect(boolean captchaCorrect) {
        CaptchaService.isCaptchaCorrect = captchaCorrect;
    }
}
