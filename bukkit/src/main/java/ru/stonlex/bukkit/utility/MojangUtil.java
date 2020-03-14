package ru.stonlex.bukkit.utility;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class MojangUtil {

    /**
     * Опять же, этот код старый, и переписывать его мне было
     * попросту лень, да и тем более, он прекрасно работает.
     *
     * Если кому-то он неудобен, то система как бы не особо сложная, 
     * поэтому можно и самому ее написать
     */

    private final String UUID_URL_STRING = "https://api.mojang.com/users/profiles/minecraft/";
    private final String SKIN_URL_STRING = "https://sessionserver.mojang.com/session/minecraft/profile/";

    private final Map<String, Skin> skinMap = new HashMap<>();

    private String readURL(String url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "MoonAPI");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setDoOutput(true);

        final StringBuilder output = new StringBuilder();
        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        while (in.ready()) {
            output.append(in.readLine());
        }
        in.close();
        return output.toString();
    }

    public Skin getSkinTextures(String name) {
        Skin cachedSkin = skinMap.get(name);

        if (cachedSkin != null && !cachedSkin.isExpired()) {
            return cachedSkin;
        }

        try {
            final String playerUUID = new JsonParser()
                    .parse(MojangUtil.readURL(UUID_URL_STRING + name))
                    .getAsJsonObject()
                    .get("id")
                    .getAsString();
            final String skinUrl = MojangUtil.readURL(SKIN_URL_STRING + playerUUID + "?unsigned=false");

            final JsonObject textureProperty = new JsonParser()
                    .parse(skinUrl)
                    .getAsJsonObject()
                    .get("properties")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject();

            final String texture = textureProperty.get("value").getAsString();
            final String signature = textureProperty.get("signature").getAsString();

            Skin skin = new Skin(name, playerUUID, texture, signature, System.currentTimeMillis());

            skinMap.put(name, skin);

            return skin;
        } catch (IOException var8) {
            return null;
        }
    }

    @RequiredArgsConstructor
    @Getter
    public class Skin {

        private final String skinName;
        private final String playerUUID;
        private final String value;
        private final String signature;
        private final long timestamp;

        public boolean isExpired() {
            return System.currentTimeMillis() - this.timestamp > TimeUnit.HOURS.toMillis(12L);
        }
    }

}
