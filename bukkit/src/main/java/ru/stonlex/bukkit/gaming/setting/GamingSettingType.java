package ru.stonlex.bukkit.gaming.setting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.GameMode;
import ru.stonlex.bukkit.gaming.GamingMode;
import ru.stonlex.bukkit.gaming.GamingStatus;

@RequiredArgsConstructor
public enum GamingSettingType {

    PLAYER_BLOCK_BREAK(false),
    PLAYER_BLOCK_PLACE(false),
    PLAYER_DAMAGE_BY_PLAYER(false),
    PLAYER_DAMAGE_BY_CREATURE(false),
    CREATURE_DAMAGE_BY_PLAYER(false),
    CREATURE_DAMAGE_BY_CREATURE(false),
    PLAYER_PRE_LOGIN_KICK(false),
    CREATURE_SPAWN(false),
    CREATURE_FALL_DAMAGE(false),
    PLAYER_FALL_DAMAGE(false),
    CREATURE_DAMAGE(false),
    PLAYER_DAMAGE(false),
    PLAYER_INTERACT(false),
    PLAYER_INTERACT_AT_ENTITY(false),
    PLAYER_INTERACT_AT_BLOCK(false),
    WEATHER_CHANGE(true),

    ITEM_PURCHASE_NO_MONEY_MESSAGE("§cОшибка, у Вас недостаточно средств!"),
    ITEM_PURCHASE_MESSAGE("§fВы успешно приобрели &a%item%"),
    ITEM_SELECT_MESSAGE("§fВы успешно выбрали &a%item%"),
    PLAYER_JOIN_MESSAGE(null),
    PLAYER_QUIT_MESSAGE(null),
    PLAYER_DEATH_MESSAGE(null),
    PLAYER_PRE_LOGIN_MESSAGE(null),

    LOBBY_SERVER("GameLobby-1"),
    GAME_NAME("Game"),
    ARENA_NAME("world"),
    ARENA_WORLD("world"),

    COUNTDOWN_START_SECONDS(30),
    COUNTDOWN_CURRENT_SECONDS(30),

    GAME_STATUS(GamingStatus.WAIT_PLAYERS),
    GAME_MODE(GamingMode.SOLO),

    PLAYER_GAME_MODE(GameMode.ADVENTURE),
    ;


    @Getter
    private final Object defaultValue;

}
