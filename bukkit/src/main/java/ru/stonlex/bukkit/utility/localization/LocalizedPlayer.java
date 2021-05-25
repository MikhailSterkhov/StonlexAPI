package ru.stonlex.bukkit.utility.localization;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.stonlex.global.localization.LocalizationResource;
import ru.stonlex.global.utility.query.ResponseHandler;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class LocalizedPlayer {

    public static LocalizedPlayer create(@NonNull String playerName, @NonNull LocalizationResource localizationResource) {
        return new LocalizedPlayer(playerName, localizationResource);
    }

    public static LocalizedPlayer create(@NonNull Player player, @NonNull LocalizationResource localizationResource) {
        return new LocalizedPlayer(player.getName(), localizationResource);
    }

    private final String playerName;
    private final LocalizationResource localizationResource;


    public void sendMessage(@NonNull String messageKey) {
        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            return;
        }

        if (localizationResource.hasMessage(messageKey)) {

            if (localizationResource.isText(messageKey)) {
                player.sendMessage(localizationResource.getText(messageKey));

            } else if (localizationResource.isList(messageKey)) {

                player.sendMessage(localizationResource.getTextList(messageKey).toArray(new String[0]));
            }

        } else {

            player.sendMessage(ChatColor.RED + messageKey);
        }
    }

    public void sendMessage(@NonNull ResponseHandler<String, LocalizationResource> messageHandler) {
        sendMessage(messageHandler.handleResponse(localizationResource));
    }

    public void sendTitle(String titleKey, String subtitleKey,
                          int fadeIn, int stay, int fadeOut) {

        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            return;
        }

        titleKey    = (titleKey == null || titleKey.trim().isEmpty() || localizationResource.isList(titleKey) ? "" : titleKey);
        subtitleKey = (subtitleKey == null || subtitleKey.trim().isEmpty() || localizationResource.isList(subtitleKey) ? "" : subtitleKey);

        String title    = localizationResource.hasMessage(titleKey) ? localizationResource.getText(titleKey) : (ChatColor.RED + titleKey);
        String subtitle = localizationResource.hasMessage(subtitleKey) ? localizationResource.getText(subtitleKey) : (ChatColor.RED + subtitleKey);

        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public void sendTitle(ResponseHandler<String, LocalizationResource> titleHandler, ResponseHandler<String, LocalizationResource> subtitleHandler,
                          int fadeIn, int stay, int fadeOut) {

        Player player = Bukkit.getPlayer(playerName);

        if (player == null) {
            return;
        }

        String title    = titleHandler.handleResponse(localizationResource);
        String subtitle = subtitleHandler.handleResponse(localizationResource);

        player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
    }

    public void sendTitle(String titleKey,
                          String subtitleKey) {

        sendTitle(titleKey, subtitleKey, 10, 70, 20);
    }

    public void sendTitle(ResponseHandler<String, LocalizationResource> titleHandler,
                          ResponseHandler<String, LocalizationResource> subtitleHandler) {

        sendTitle(titleHandler, subtitleHandler, 10, 70, 20);
    }

}
