package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import java.time.Instant;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class WATExpirationAction extends Action<CheckWATExpiration> {


    @Override
    public CompletionStage<Result> call(Http.Request request) {
        String WAT = request.session().get("WAT").orElse("None");
        if(WAT.equals("None"))
        {
            return CompletableFuture.completedFuture(Results.redirect("/admin/login"));
        }
        Base64.Decoder decoder = Base64.getUrlDecoder();
        ObjectMapper objectMapper = new ObjectMapper();

        String[] chunks = WAT.split("\\.");
        String payload = new String(decoder.decode(chunks[1]));
        try {
            JsonNode jsonNode = objectMapper.readTree(payload);
            long watExpiry = jsonNode.get("exp").asLong();
            Instant instant = Instant.ofEpochSecond(watExpiry);
            Instant currentTime = Instant.now();

            if (instant.isBefore(currentTime)){
                return CompletableFuture.completedFuture(Results.redirect("/admin/login"));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return delegate.call(request);
    }
}
