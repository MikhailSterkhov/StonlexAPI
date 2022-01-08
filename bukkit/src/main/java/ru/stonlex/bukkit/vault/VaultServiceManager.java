package ru.stonlex.bukkit.vault;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import ru.stonlex.bukkit.vault.impl.ChatService;
import ru.stonlex.bukkit.vault.impl.EconomyService;
import ru.stonlex.bukkit.vault.impl.PermissionService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VaultServiceManager {
    
    public static final VaultServiceManager INSTANCE = new VaultServiceManager();
    
    public static final Registry REGISTRY = new Registry();
    static {
        REGISTRY.register(Chat.class, new ChatService());
        REGISTRY.register(Economy.class, new EconomyService());
        REGISTRY.register(Permission.class, new PermissionService());
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Registry {
    
        private final Map<Class<?>, VaultService<?>> serviceMap = new HashMap<>();

        public Collection<VaultService<?>> getVaultServices() {
            return serviceMap.values();
        }
        
        public <V> void register(Class<V> provider, VaultService<V> service) {
            serviceMap.put(provider, service);
        }
        
        @SuppressWarnings("unchecked")
        public <V> VaultService<V> getRegisteredService(Class<V> provider) {
            return (VaultService<V>) serviceMap.get(provider);
        }
        
    }
    
    private <V> V getProvider(Class<V> provider) {
        return REGISTRY.getRegisteredService(provider).getCurrentService(Bukkit.getServer());
    }

    public String getPlayerDisplay(boolean suffix, @NonNull String playerName) {
        return getPrefix(playerName) + playerName + (suffix ? getSuffix(playerName) : "");
    }

    public String getPlayerDisplay(@NonNull String playerName) {
        return getPlayerDisplay(true, playerName);
    }

    public String getPrefix(@NonNull String playerName) {
        return getProvider(Chat.class).getPlayerPrefix((String)null, playerName);
    }

    public String getGroupPrefix(@NonNull String playerName) {
        return getProvider(Chat.class).getGroupPrefix((String)null, playerName);
    }

    public String getSuffix(@NonNull String playerName) {
        return getProvider(Chat.class).getPlayerSuffix((String)null, playerName);
    }

    public String getGroupSuffix(@NonNull String playerName) {
        return getProvider(Chat.class).getGroupSuffix((String)null, playerName);
    }

    public String getPrimaryGroup(@NonNull String playerName) {
        return getProvider(Permission.class).getPrimaryGroup((String)null, playerName);
    }

    public String[] getGroups(@NonNull String playerName) {
        return getProvider(Permission.class).getPlayerGroups((String)null, playerName);
    }

    public double getBalance(@NonNull String playerName) {
        return getProvider(Economy.class).getBalance(playerName);
    }

    public boolean hasPermission(@NonNull String playerName, String permission) {
        return getProvider(Permission.class).playerHas((String)null, playerName, permission);
    }

    public boolean hasGroup(@NonNull String playerName, String groupName) {
        return getProvider(Permission.class).playerInGroup((String)null, playerName, groupName);
    }

    public boolean hasMoney(@NonNull String playerName, double moneyCount) {
        return getBalance(playerName) >= moneyCount;
    }

    public void setMoney(@NonNull String playerName, double moneyCount) {
        double playerBalance = getBalance(playerName);

        if (moneyCount > playerBalance) {
            giveMoney(playerName, moneyCount - playerBalance);
        }

        if (moneyCount < playerBalance) {
            takeMoney(playerName, playerBalance - moneyCount);
        }
    }

    public void giveMoney(@NonNull String playerName, double moneyToGive) {
        getProvider(Economy.class).depositPlayer(playerName, moneyToGive);
    }

    public void takeMoney(@NonNull String playerName, double moneyToTake) {
        getProvider(Economy.class).withdrawPlayer(playerName, moneyToTake);
    }

    public void addPermission(@NonNull String playerName, String permission) {
        getProvider(Permission.class).playerAdd((String)null, playerName, permission);
    }

    public void removePermission(@NonNull String playerName, String permission) {
        getProvider(Permission.class).playerRemove((String)null, playerName, permission);
    }

    public void addGroup(@NonNull String playerName, String groupName) {
        getProvider(Permission.class).playerAddGroup((String)null, playerName, groupName);
    }

    public void removeGroup(@NonNull String playerName, String groupName) {
        getProvider(Permission.class).playerRemoveGroup((String)null, playerName, groupName);
    }
    
}
