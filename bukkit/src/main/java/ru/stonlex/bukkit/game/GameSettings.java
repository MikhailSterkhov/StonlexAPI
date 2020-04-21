package ru.stonlex.bukkit.game;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.game.enums.GameStatus;
import ru.stonlex.bukkit.game.enums.GameType;
import ru.stonlex.bukkit.utility.ItemUtil;

public final class GameSettings {

    public ItemStack TEAM_SELECT_ITEM = ItemUtil.getItemStack(Material.ANVIL, "§aВыбор команды",
            "",
            "§7Используйте данный предмет,",
            "§7чтобы выбрать команду.");

    public ItemStack SPECTATOR_TELEPORT_ITEM = ItemUtil.getItemStack(Material.COMPASS, "§aТелепортатор",
            "",
            "§7Используйте данный предмет,",
            "§7чтобы телепортироваться к любому игроку на арене.");

    public ItemStack LEAVE_IN_GAME_ITEM = ItemUtil.getItemStack(Material.BED, "§aПокинуть игру",
            "",
            "§7Используйте данный предмет,",
            "§7чтобы покинуть игру.");

    public ItemStack PERK_SELECT_ITEM = ItemUtil.getItemStack(Material.BLAZE_POWDER, "§aВыбор перка",
            "",
            "§7Используйте данный предмет,",
            "§7чтобы открыть выбор игрового перка.");

    public ItemStack KIT_SELECT_ITEM = ItemUtil.getItemStack(Material.APPLE, "§aВыбор набора",
            "",
            "§7Используйте данный предмет,",
            "§7чтобы открыть выбор игрового набора.");

    public ItemStack SHOP_ITEM = ItemUtil.getItemStack(Material.CHEST, "§aМагазин",
            "",
            "§7Используйте данный предмет,",
            "§7чтобы открыть игровой магазин.");

    public int MAX_ARENA_SLOTS = 16;
    public int PLAYERS_IN_TEAM_COUNT = 1;
    public int LOBBY_TIMER_START_SECONDS = 15;

    public GameMode DEFAULT_GAMEMODE = GameMode.ADVENTURE;
    public GameType GAME_TYPE = GameType.SOLO;
    public GameStatus GAME_STATUS = GameStatus.WAITING_PLAYERS;

    public String BROADCAST_PREFIX = "§3Объявление §8| §f";
    public String DEFAULT_PREFIX = "§6SkyWars §8| §f";
    public String ERROR_PREFIX = "§c";
    public String LOBBY_SERVER_NAME = "swlobby-1";
    public String GAME_NAME = "SkyWars";
    public String ARENA_WORLD_NAME = "world";

    public boolean BLOCK_BREAK = false;
    public boolean BLOCK_PLACE = false;
    public boolean PLAYER_INTERACT = false;
    public boolean PLAYER_INTERACT_AT_ENTITY = false;
    public boolean PLAYER_JOIN = true;
    public boolean LEAVES_DECAY = false;
    public boolean PLAYER_DAMAGE = false;
    public boolean ENTITY_DAMAGE = false;
    public boolean PLAYER_DAMAGE_FALL = false;
    public boolean PLAYER_DAMAGE_BY_PLAYER = false;
    public boolean PLAYER_DAMAGE_BY_ENTITY = false;
    public boolean ENTITY_DAMAGE_BY_PLAYER = false;
    public boolean PLAYER_MOVE = true;
    public boolean ENTITY_SPAWN = false;
    public boolean PLAYER_FOOD_CHANGE = false;
    public boolean PLAYER_PICKUP_ITEM = false;
    public boolean PLAYER_DROP_ITEM = false;
    public boolean WEATHER_CHANGE = false;

}
