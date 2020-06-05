package ru.stonlex.bukkit.depend.vault.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.BukkitAPI;
import ru.stonlex.bukkit.depend.vault.IVaultPlayer;
import ru.stonlex.bukkit.depend.vault.manager.VaultManager;

@RequiredArgsConstructor
public class StonlexVaultPlayer implements IVaultPlayer {

    private static final VaultManager VAULT_MANAGER = BukkitAPI.getInstance().getVaultManager();

    @Getter
    private final String playerName;


    @Override
    public String getDisplayName() {
        return getPrefix() + playerName + getSuffix();
    }

    @Override
    public String getPrefix() {
        return VAULT_MANAGER.getChatProvider().getProvider().getPlayerPrefix((String)null, playerName);
    }

    @Override
    public String getGroupPrefix() {
        return VAULT_MANAGER.getChatProvider().getProvider().getGroupPrefix((String)null, playerName);
    }

    @Override
    public String getSuffix() {
        return VAULT_MANAGER.getChatProvider().getProvider().getPlayerSuffix((String)null, playerName);
    }

    @Override
    public String getGroupSuffix() {
        return VAULT_MANAGER.getChatProvider().getProvider().getGroupSuffix((String)null, playerName);
    }

    @Override
    public String getPrimaryGroup() {
        return VAULT_MANAGER.getPermissionProvider().getProvider().getPrimaryGroup((String)null, playerName);
    }

    @Override
    public String[] getGroups() {
        return VAULT_MANAGER.getPermissionProvider().getProvider().getPlayerGroups((String)null, playerName);
    }

    @Override
    public double getBalance() {
        return VAULT_MANAGER.getEconomyProvider().getProvider().getBalance(playerName);
    }

    @Override
    public boolean hasPermission(String permission) {
        return VAULT_MANAGER.getPermissionProvider().getProvider().playerHas((String)null, playerName, permission);
    }

    @Override
    public boolean hasGroup(String groupName) {
        return VAULT_MANAGER.getPermissionProvider().getProvider().playerInGroup((String)null, playerName, groupName);
    }

    @Override
    public boolean hasMoney(double moneyCount) {
        return getBalance() >= moneyCount;
    }

    @Override
    public void setMoney(double moneyCount) {
        double playerBalance = getBalance();

        if (moneyCount > playerBalance) {
            giveMoney(moneyCount - playerBalance);
        }

        if (moneyCount < playerBalance) {
            takeMoney(playerBalance - moneyCount);
        }
    }

    @Override
    public void giveMoney(double moneyToGive) {
        VAULT_MANAGER.getEconomyProvider().getProvider().depositPlayer(playerName, moneyToGive);
    }

    @Override
    public void takeMoney(double moneyToTake) {
        VAULT_MANAGER.getEconomyProvider().getProvider().withdrawPlayer(playerName, moneyToTake);
    }

    @Override
    public void addPermission(String permission) {
        VAULT_MANAGER.getPermissionProvider().getProvider().playerAdd((String)null, playerName, permission);
    }

    @Override
    public void removePermission(String permission) {
        VAULT_MANAGER.getPermissionProvider().getProvider().playerRemove((String)null, playerName, permission);
    }

    @Override
    public void addGroup(String groupName) {
        VAULT_MANAGER.getPermissionProvider().getProvider().playerAddGroup((String)null, playerName, groupName);
    }

    @Override
    public void removeGroup(String groupName) {
        VAULT_MANAGER.getPermissionProvider().getProvider().playerRemoveGroup((String)null, playerName, groupName);
    }

}
