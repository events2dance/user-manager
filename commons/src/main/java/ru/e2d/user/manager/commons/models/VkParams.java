package ru.e2d.user.manager.commons.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class VkParams {
    @Expose
    @SerializedName("client_id")
    int clientId;

    @NonNull String clientSecret;

    @Expose
    @SerializedName("redirect_uri")
    @NonNull String redirectUri;

    @Expose
    @SerializedName("scope")
    String scope;

    @Expose
    @SerializedName("response_type")
    @NonNull String responseType;
}
