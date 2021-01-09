package ru.stonlex.bukkit.vault;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VaultPlayer {

    @Getter
    private final String playerName;


    public String getDisplayName() {
        return getPrefix() + playerName + getSuffix();
    }

    public String getPrefix() {
        return VaultManager.INSTANCE.getChatProvider().getProvider().getPlayerPrefix((String)null, playerName);
    }

    public String getGroupPrefix() {
        return VaultManager.INSTANCE.getChatProvider().getProvider().getGroupPrefix((String)null, playerName);
    }

    public String getSuffix() {
        return VaultManager.INSTANCE.getChatProvider().getProvider().getPlayerSuffix((String)null, playerName);
    }

    public String getGroupSuffix() {
        return VaultManager.INSTANCE.getChatProvider().getProvider().getGroupSuffix((String)null, playerName);
    }

    public String getPrimaryGroup() {
        return VaultManager.INSTANCE.getPermissionProvider().getProvider().getPrimaryGroup((String)null, playerName);
    }

    public String[] getGroups() {
        return VaultManager.INSTANCE.getPermissionProvider().getProvider().getPlayerGroups((String)null, playerName);
    }

    public double getBalance() {
        return VaultManager.INSTANCE.getEconomyProvider().getProvider().getBalance(playerName);
    }

    public boolean hasPermission(String permission) {
        return VaultManager.INSTANCE.getPermissionProvider().getProvider().playerHas((String)null, playerName, permission);
    }

    public boolean hasGroup(String groupName) {
        return VaultManager.INSTANCE.getPermissionProvider().getProvider().playerInGroup((String)null, playerName, groupName);
    }

    public boolean hasMoney(double moneyCount) {
        return getBalance() >= moneyCount;
    }

    public void setMoney(double moneyCount) {
        double playerBalance = getBalance();

        if (moneyCount > playerBalance) {
            giveMoney(moneyCount - playerBalance);
        }

        if (moneyCount < playerBalance) {
            takeMoney(playerBalance - moneyCount);
        }
    }

    public void giveMoney(double moneyToGive) {
        VaultManager.INSTANCE.getEconomyProvider().getProvider().depositPlayer(playerName, moneyToGive);
    }

    public void takeMoney(double moneyToTake) {
        VaultManager.INSTANCE.getEconomyProvider().getProvider().withdrawPlayer(playerName, moneyToTake);
    }

    public void addPermission(String permission) {
        VaultManager.INSTANCE.getPermissionProvider().getProvider().playerAdd((String)null, playerName, permission);
    }

    public void removePermission(String permission) {
        VaultManager.INSTANCE.getPermissionProvider().getProvider().playerRemove((String)null, playerName, permission);
    }

    public void addGroup(String groupName) {
        VaultManager.INSTANCE.getPermissionProvider().getProvider().playerAddGroup((String)null, playerName, groupName);
    }
    
    public void removeGroup(String groupName) {
        VaultManager.INSTANCE.getPermissionProvider().getProvider().playerRemoveGroup((String)null, playerName, groupName);
    }
    
}
