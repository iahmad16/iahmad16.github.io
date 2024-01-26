package models.enums;

public enum SocialMediaPlatform {
    INSTAGRAM,
    FACEBOOK,
    TWITTER,
    SNAPCHAT,
    TIK_TOK,
    WHATS_APP;

    public static SocialMediaPlatform convert(String platform){
        switch (platform){
            case "INSTAGRAM":
                return SocialMediaPlatform.INSTAGRAM;
            case "FACEBOOK":
                return SocialMediaPlatform.FACEBOOK;
            case "TWITTER":
                return SocialMediaPlatform.TWITTER;
            case "SNAPCHAT":
                return SocialMediaPlatform.SNAPCHAT;
            case "TIKTOK":
                return SocialMediaPlatform.TIK_TOK;
            case "WHATSAPP":
                return SocialMediaPlatform.WHATS_APP;
            default:
                throw new IllegalArgumentException("INVALID SOCIAL MEDIA PLATFORM: " + platform);

        }
    }
}
