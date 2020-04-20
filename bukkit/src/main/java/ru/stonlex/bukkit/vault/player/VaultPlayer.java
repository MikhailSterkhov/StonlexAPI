package ru.stonlex.bukkit.vault.player;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import ru.stonlex.bukkit.BukkitAPI;

public class VaultPlayer  {

    private final String playerName;

    @Getter
    private final OfflinePlayer offlinePlayer;

    private final static Chat vaultChat = BukkitAPI.getVaultManager().getChatManager().getVaultChat();
    private final static Economy vaultEconomy = BukkitAPI.getVaultManager().getEconomyManager().getVaultEconomy();
    private final static Permission vaultPermission = BukkitAPI.getVaultManager().getPermissionManager().getVaultPermission();


    /**
     * Конструктор, который инициализирует Vault-игрока
     *
     * @param playerName - ник игрока, которого инициализируем
     */
    public VaultPlayer(String playerName) {
        this.playerName = playerName;
        this.offlinePlayer = Bukkit.getOfflinePlayer(playerName);
    }


    /**
     * Получить имя игрока
     */
    public String getName() {
        return playerName;
    }

    /**
     * Получить DisplayName игрока
     */
    public String getDisplayName() {
        return getPrefix() + playerName + getSuffix();
    }

    /**
     * Получить префикс игрока
     */
    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', vaultChat.getPlayerPrefix((String) null, playerName));
    }

    /**
     * Получить cуффикс игрока
     */
    public String getSuffix() {
        return ChatColor.translateAlternateColorCodes('&', vaultChat.getPlayerSuffix((String) null, playerName));
    }

    /**
     * Получить префикс группы игрока
     */
    public String getGroupPrefix() {
        return ChatColor.translateAlternateColorCodes('&', vaultChat.getGroupPrefix((String) null, getPrimaryGroup()));
    }

    /**
     * Получить суффикс группы игрока
     */
    public String getGroupSuffix() {
        return ChatColor.translateAlternateColorCodes('&', vaultChat.getGroupSuffix((String) null, getPrimaryGroup()));
    }

    /**
     * Получить приоритетную группу игрока
     */
    public String getPrimaryGroup() {
        return vaultChat.getPrimaryGroup((String) null, playerName);
    }

    /**
     * Получить баланс игрока
     */
    public double getBalance() {
        return vaultEconomy.getBalance(offlinePlayer);
    }

    /**
     * Установить баланс игрока
     *
     * @param balance - новый баланс
     */
    public void setBalance(double balance) {
        if (balance > getBalance()) {
            giveMoney(balance - getBalance());
        } else if (balance < getBalance()) {
            takeMoney(getBalance() - balance);
        }
    }

    /**
     * Возвращает boolean, который говорит о том, есть ли
     * у игрока указанное количество монет
     *
     * @param moneyCount - количество монет
     */
    public boolean hasMoney(int moneyCount) {
        return getBalance() >= moneyCount;
    }

    /**
     * Выдать монетки игроку
     *
     * @param moneyCount - кол-во выданных монет
     */
    public void giveMoney(double moneyCount) {
        vaultEconomy.depositPlayer(offlinePlayer, moneyCount);
    }

    /**
     * Снять определенное кол-во монет
     *
     * @param moneyCount - кол-во снятых монет
     */
    public void takeMoney(double moneyCount) {
        vaultEconomy.withdrawPlayer(offlinePlayer, moneyCount);
    }

    /**
     * Получить список групп игрока
     */
    public String[] getGroups() {
        return vaultPermission.getPlayerGroups(null, offlinePlayer);
    }

    /**
     * Выдать право игроку
     *
     * @param permission - право
     */
    public void addPermission(String permission) {
        vaultPermission.playerAdd(null, offlinePlayer, permission);
    }

    /**
     * Удалить право у игрока
     *
     * @param permission - право
     */
    public void removePermission(String permission) {
        vaultPermission.playerRemove(null, offlinePlayer, permission);
    }

    /**
     * Добавить группу игроку
     *
     * @param group - группа
     */
    public void addGroup(String group) {
        vaultPermission.groupAdd((String) null, playerName, group);
    }

    /**
     * Удалить группу у игрока
     *
     * @param group - группа
     */
    public void removeGroup(String group) {
        vaultPermission.groupRemove((String) null, playerName, group);
    }

    /**
     * Существует ли группа у данного игрока
     *
     * @param group - группа
     */
    public boolean hasGroup(String group) {
        return vaultPermission.groupHas((String) null, playerName, group);
    }

    /**
     * Существует ли право у данного игрока
     *
     * @param permission - право
     */
    public boolean hasPermission(String permission) {
        return vaultPermission.playerHas(null, offlinePlayer, permission);
    }
}
