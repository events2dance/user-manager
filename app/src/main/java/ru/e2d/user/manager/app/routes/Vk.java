package ru.e2d.user.manager.app.routes;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import lombok.Value;
import org.apache.http.HttpHeaders;
import ru.e2d.user.manager.commons.models.VkParams;
import spark.*;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.URLEncoder.encode;

@Value(staticConstructor = "of")
public class Vk implements RouteInjection {
    Service http;
    VkParams vkParams;
    VkApiClient vk;
    Map<UUID, String> referrers = new ConcurrentHashMap<>();
    Map<String, Integer> users = new ConcurrentHashMap<>();

    @Override
    public void injection() {
        http.get("/vk/authorize/params", GetAuthorizeParams.of(vkParams));
        http.get("/vk/authorize", Authorize.of(vkParams, referrers));
        http.get("/vk/authorize/callback", AuthorizeCallback.of(vkParams, vk, referrers, users));
        http.get("/vk/validate", ValidateToken.of(vk, users));
    }

    @Value(staticConstructor = "of")
    private static class Authorize implements Route {
        private static final String baseVkUrl = "https://oauth.vk.com/authorize";
        VkParams params;
        Map<UUID, String> referrers;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            UUID uuid = UUID.randomUUID();
            Optional.ofNullable(request.headers("Referer"))
                    .ifPresent(referrer -> {
                        referrers.put(uuid, referrer);
                    });
            String state = encode(uuid.toString(), "UTF8");

            String url = String.format(
                    "%s?client_id=%d&display=page&redirect_uri=%s&scope=%s&response_type=%s&state=%s&v=5.126",
                    baseVkUrl, params.getClientId(), encode(params.getRedirectUri(), "UTF8"), params.getScope(),
                    params.getResponseType(), state);

            response.redirect(url);
            return "";
        }
    }

    @Value(staticConstructor = "of")
    private static class GetAuthorizeParams implements Route {
        VkParams vkParams;

        @Override
        public Object handle(Request request, Response response) throws Exception {
            return vkParams;
        }
    }

    @Value(staticConstructor = "of")
    private static class AuthorizeCallback implements Route {
        VkParams vkParams;
        VkApiClient vk;
        Map<UUID, String> referrers;
        Map<String, Integer> users;


        @Override
        public Object handle(Request request, Response response) throws Exception {
            String code = request.queryParams("code");
            String state = request.queryParams("state");


            UserAuthResponse vkRes = vk.oAuth()
                    .userAuthorizationCodeFlow(
                            vkParams.getClientId(),
                            vkParams.getClientSecret(),
                            vkParams.getRedirectUri(),
                            code)
                    .execute();

            users.put(vkRes.getAccessToken(), vkRes.getUserId());

            UUID uuid = UUID.fromString(state);

            if (referrers.containsKey(uuid)) {
                String referer = referrers.get(uuid);
                response.redirect(referer);
            }

            return vkRes.getAccessToken();
        }
    }

    @Value(staticConstructor = "of")
    private static class ValidateToken implements Route {
        VkApiClient vk;
        Map<String, Integer> users;
        Pattern authPattern = Pattern.compile("Bearer (.*)");

        @Override
        public Object handle(Request request, Response response) throws Exception {
            Matcher authMatch = authPattern.matcher(request.headers(HttpHeaders.AUTHORIZATION));
            if (authMatch.find()) {
                String token = authMatch.group(1);
                if (users.containsKey(token)) {
                    List<UserXtrCounters> res = vk.users().get(new UserActor(users.get(token), token)).execute();
                    response.status(204);
                } else {
                    response.status(401);
                }
            } else {
                response.status(401);
            }
            return null;
        }
    }

}
