package models.classes;

import models.enums.SocialMediaPlatform;

public class SocialMediaAccount {
    public SocialMediaPlatform platform;
//    public String socialMediaIconImage;
    public String accountLink;

    public SocialMediaAccount(String platform, String accountLink) {
        this.platform = stringToPlatform(platform);
//        this.socialMediaIconImage = socialMediaIconImage;
        this.accountLink = accountLink;
    }

    public static SocialMediaPlatform stringToPlatform(String platform){
        switch (platform.toUpperCase()) {
            case "FACEBOOK":
                return SocialMediaPlatform.FACEBOOK;
            case "INSTAGRAM":
                return SocialMediaPlatform.INSTAGRAM;
            case "TWITTER":
                return SocialMediaPlatform.TWITTER;
            case "SNAPCHAT":
                return SocialMediaPlatform.SNAPCHAT;
            case "TIK_TOK":
            case "TIKTOK":
                return SocialMediaPlatform.TIK_TOK;
            case "WHATSAPP":
            case "WHATS_APP":
                return SocialMediaPlatform.WHATS_APP;
            default:
                throw new IllegalArgumentException("INVALID PLATFORM: " + platform);
        }
    }

    public String platformToString(){
        switch (this.platform) {
            case FACEBOOK:
                return "Facebook";
            case INSTAGRAM:
                return "Instagram";
            case TWITTER:
                return "Twitter";
            case SNAPCHAT:
                return "Snapchat";
            case TIK_TOK:
                return "TikTok";
            case WHATS_APP:
                return "Whatsapp";
            default:
                throw new IllegalArgumentException("INVALID PLATFORM: " + this.platform);
        }
    }
}
