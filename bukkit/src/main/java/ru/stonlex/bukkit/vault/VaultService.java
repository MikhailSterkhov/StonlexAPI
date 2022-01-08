package ru.stonlex.bukkit.vault;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

@RequiredArgsConstructor
public abstract class VaultService<V> {

    @Getter
    private final Class<V> providerType;

    public abstract V getCurrentService(Server server);

    public abstract void register(Plugin plugin, ServicePriority priority, V instance);
    public abstract void register(Plugin plugin, V instance);
}
