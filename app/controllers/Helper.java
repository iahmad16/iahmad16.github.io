package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.classes.DiscountedService;
import models.classes.SocialMediaAccount;
import play.filters.csrf.CSRF;
import play.libs.ws.WSClient;
import play.mvc.Controller;
import play.mvc.Http;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Helper extends Controller {
    private static WSClient ws;

    @Inject
    public Helper(WSClient ws) {
        this.ws = ws;
    }

    // Decodes the WAT and extracts the logged-in admin's role
    public static String getRole(Http.Request request){
        String WAT = request.session().get("WAT").orElse("None");
        String[] chunks = WAT.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            return jsonNode.get("data").get("role").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static CSRF.Token getCSRF(Http.Request request){
        return CSRF.getToken(request).orElse(new CSRF.Token("defaultTokenName", "defaultTokenValue"));
    }

    // There are 2 versions of the same method since the structure of the JsonNode varies.
    // For example the JsonNode socialMediaAccountsNode that comes from the API is structured like:
    // {[{"platform": "FACEBOOK", "accountLink": "SOME LINK"}, {"platform": "INSTAGRAM", "accountLink": "SOME LINK"}]}
    // (parseSocialMediaAccounts handles this)

    // While post adding the shop details the socialMediaAccountsNode looks like this:
    // {["FACEBOOK":"SOME LINK", "INSTAGRAM":"SOME LINK"]} (parseSocialMediaAccounts2 handles this)

    // Similarly for discountedServices

    // Converts the social media account json object into a list of SocialMediaAccount objects
    // Used when shop listings are fetched from the API
    public static List<SocialMediaAccount> parseSocialMediaAccounts(JsonNode socialMediaAccountsNode) {
    List<SocialMediaAccount> socialMediaAccountsList = new ArrayList<>();
    socialMediaAccountsNode.elements().forEachRemaining(entry -> {
        String platform = entry.get("platform").asText();
        String account_link = entry.get("accountLink").asText();
        SocialMediaAccount socialMediaAccount = new SocialMediaAccount(platform, account_link);
        socialMediaAccountsList.add(socialMediaAccount);
    });
    return socialMediaAccountsList;
}

    // Converts the social media account json object into a list of SocialMediaAccount objects
    // Used when shop is added/edited (has different structural format)
    public static List<SocialMediaAccount> parseSocialMediaAccounts2(JsonNode socialMediaAccountsNode) {
        List<SocialMediaAccount> socialMediaAccountsList = new ArrayList<>();
        socialMediaAccountsNode.fieldNames().forEachRemaining(platform -> {
            String account_link = socialMediaAccountsNode.get(platform).asText();
            SocialMediaAccount socialMediaAccount = new SocialMediaAccount(platform, account_link);
            socialMediaAccountsList.add(socialMediaAccount);
        });
        return socialMediaAccountsList;
    }

    // Converts the discounted services json object into a list of DiscountedService objects
    // Used when shop listings are fetched from the API
    public static List<DiscountedService> parseDiscountedServices(JsonNode discountedServicesNode){
        List<DiscountedService> discountedServicesList = new ArrayList<>();
        discountedServicesNode.elements().forEachRemaining(entry -> {
            String serviceName = entry.get("serviceName").asText();
            Double discountPercent = entry.get("discountPercent").asDouble();
            DiscountedService discountedService = new DiscountedService(serviceName, discountPercent);
            discountedServicesList.add(discountedService);
        });
        return discountedServicesList;
    }

    // Converts the discounted services json object into a list of DiscountedServices objects
    // Used when shop is added/edited (has different structural format)
    public static List<DiscountedService> parseDiscountedServices2(JsonNode discountedServicesNode){
        List<DiscountedService> discountedServicesList = new ArrayList<>();
        discountedServicesNode.fieldNames().forEachRemaining(serviceName -> {
            Double discountPercent = discountedServicesNode.get(serviceName).asDouble();
            DiscountedService discountedService = new DiscountedService(serviceName, discountPercent);
            discountedServicesList.add(discountedService);
        });
        return discountedServicesList;
    }

}
