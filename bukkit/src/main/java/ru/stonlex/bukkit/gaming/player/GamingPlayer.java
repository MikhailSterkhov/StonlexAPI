package ru.stonlex.bukkit.gaming.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;
import ru.stonlex.bukkit.gaming.GameAPI;
import ru.stonlex.bukkit.gaming.event.GameGhostStatusChangeEvent;
import ru.stonlex.bukkit.gaming.item.GamingItem;
import ru.stonlex.bukkit.gaming.item.GamingItemCategory;
import ru.stonlex.bukkit.gaming.database.GamingItemDatabase;
import ru.stonlex.bukkit.gaming.setting.GamingSettingType;
import ru.stonlex.bukkit.gaming.team.GamingTeam;
import ru.stonlex.bukkit.utility.BukkitPotionUtil;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.bukkit.vault.VaultManager;
import ru.stonlex.bukkit.vault.VaultPlayer;

import java.util.*;

@RequiredArgsConstructor
@Getter
public class GamingPlayer {

    protected @NonNull String playerName;
    protected @NonNull Map<String, Object> playerDataMap = new LinkedHashMap<>();

    protected @NonNull Multimap<GamingItemCategory, GamingItem> purchasedItemMap = HashMultimap.create();
    protected @NonNull Map<GamingItemCategory, GamingItem> selectionItemMap = new HashMap<>();

    @Setter
    protected GamingTeam currentTeam;

    protected boolean ghost;


    /**
     * Если долбаеб живой еще
     */
    public boolean isAlive() {
        return !isGhost();
    }

    /**
     * Преобразовать игрового пользователя
     * в Bukkit игрока
     *
     * @return - Bukkit игрок
     */
    public Player toBukkit() {
        return Bukkit.getPlayerExact(playerName);
    }

    /**
     * Преобразовать игрового пользователя
     * в Bukkit игрока
     *
     * @return - Bukkit игрок
     */
    public VaultPlayer toVault() {
        return VaultManager.INSTANCE.getVaultPlayer(playerName);
    }

    /**
     * Установить кастомную временную
     * дату игровому пользователю
     *
     * @param playerDataName - имя даты
     * @param dataValue      - значение даты
     */
    public void setPlayerData(@NonNull String playerDataName, @NonNull Object dataValue) {
        playerDataMap.put(playerDataName.toLowerCase(), dataValue);
    }

    /**
     * Получить заданное значение для
     * даты игрового пользователя
     *
     * @param playerDataName - имя даты
     * @param dataValueClass - класс значения даты
     */
    public <T> T getPlayerData(@NonNull String playerDataName, @NonNull Class<T> dataValueClass) {
        return (T) playerDataMap.get(playerDataName.toLowerCase());
    }

    /**
     * Отправить сообщение игроку из
     * данных игровых настроек
     *
     * @param messageSetting - текст сообщения из настроек
     */
    public void sendMessage(@NonNull GamingSettingType messageSetting,
                            @NonNull String... replacements) {

        Preconditions.checkArgument(messageSetting.getDefaultValue() instanceof String, "Settings %s is`nt usable here", messageSetting);
        Player player = toBukkit();

        if (player != null) {

            String textMessage = GameAPI.SETTINGS.getSetting(messageSetting).asString();
            if (replacements.length > 0 && replacements.length % 2 == 0) {

                for (int i = 0 ; i < replacements.length ; i+=2) {
                    String placeholder = replacements[i];
                    String replacement = replacements[i + 1];

                    textMessage = textMessage.replace(placeholder, replacement);
                }
            }

            player.sendMessage(textMessage);
        }
    }

    /**
     * Получить бошку игрового пользователя
     * со скином как {@link ItemStack}
     */
    public ItemStack getSkull() {
        return ItemUtil.getSkull(playerName);
    }

    /**
     * Установить игровому пользователю
     * статус наблюдателя игры
     *
     * @param isGhost - разрешение на статус
     */
    public void setGhost(boolean isGhost) {
        GameGhostStatusChangeEvent ghostStatusChangeEvent = new GameGhostStatusChangeEvent(this, isGhost);
        Bukkit.getPluginManager().callEvent(ghostStatusChangeEvent);

        if (ghostStatusChangeEvent.isCancelled()) {
            return;
        }

        Player player = toBukkit();

        if (isGhost) {
            player.addPotionEffect(BukkitPotionUtil.getInfinityPotion(PotionEffectType.INVISIBILITY));
            player.addPotionEffect(BukkitPotionUtil.getInfinityPotion(PotionEffectType.SPEED));

            if (currentTeam != null) {
                currentTeam.removePlayer(this);
            }

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                if (GamingPlayer.of(onlinePlayer).isGhost()) {

                    onlinePlayer.showPlayer(player);
                    player.showPlayer(onlinePlayer);

                    continue;
                }

                onlinePlayer.hidePlayer(player);
            }

            player.setAllowFlight(true);
            player.setFlying(true);

        } else {

            player.setFlying(false);
            player.setAllowFlight(false);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {

                if (GamingPlayer.of(onlinePlayer).isAlive()) {
                    onlinePlayer.showPlayer(player);
                    player.showPlayer(onlinePlayer);
                }
            }

        }

        player.getInventory().clear();
        player.setGameMode(GameMode.ADVENTURE);

        player.setHealth(toBukkit().getHealthScale());
        player.setNoDamageTicks(isGhost ? Integer.MAX_VALUE : 0);

        this.ghost = isGhost;
    }

    public void setSelectedItem(@NonNull GamingItem gamingItem) {
        selectionItemMap.put(gamingItem.getItemCategory(), gamingItem);

        GamingItemDatabase.CURRENT.select(this, gamingItem);
    }

    public void addBoughtItem(@NonNull GamingItem gamingItem) {
        purchasedItemMap.put(gamingItem.getItemCategory(), gamingItem);

        GamingItemDatabase.CURRENT.purchase(this, gamingItem);
    }


    public boolean isItemSelected(@NonNull GamingItem gamingItem) {
        return selectionItemMap.containsValue(gamingItem);
    }

    public boolean isItemBought(@NonNull GamingItem gamingItem) {
        return purchasedItemMap.containsValue(gamingItem);
    }

    public boolean hasItemSelected(@NonNull GamingItemCategory itemCategory) {
        return selectionItemMap.get(itemCategory) != null;
    }


    public GamingItem getItemSelection(@NonNull GamingItemCategory itemCategory) {
        GamingItem gamingItem = selectionItemMap.get(itemCategory);

        if (gamingItem != null) {
            selectionItemMap.put(itemCategory,
                    gamingItem = GamingItemDatabase.CURRENT.getItemSelection(this, itemCategory));
        }

        return gamingItem;
    }

    public Collection<GamingItem> getBoughtItems(@NonNull GamingItemCategory itemCategory) {
        Collection<GamingItem> gamingItemCollection = purchasedItemMap.get(itemCategory);

        if (gamingItemCollection != null) {
            purchasedItemMap.putAll(itemCategory,
                    gamingItemCollection = GamingItemDatabase.CURRENT.getItemPurchased(this).get(itemCategory));
        }

        return gamingItemCollection;
    }


// ============================================= // PLAYER FACTORY // ============================================= //

    public static final Map<String, GamingPlayer> GAMING_PLAYER_MAP = new HashMap<>();


    /**
     * Получить игрового пользователя по его нику
     *
     * @param playerName - ник игрока
     */
    public static GamingPlayer of(@NonNull String playerName) {
        return GAMING_PLAYER_MAP.computeIfAbsent(playerName.toLowerCase(), GamingPlayer::new);
    }

    /**
     * Получить игрового пользователя по Bukkit игроку
     *
     * @param player - Bukkit игрок
     */
    public static GamingPlayer of(@NonNull Player player) {
        return of(player.getName());
    }

// ============================================= // PLAYER FACTORY // ============================================= //

}
