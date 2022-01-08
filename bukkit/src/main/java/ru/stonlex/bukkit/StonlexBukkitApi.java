package ru.stonlex.bukkit;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.command.BaseCommand;
import ru.stonlex.bukkit.command.manager.CommandManager;
import ru.stonlex.bukkit.holographic.ProtocolHolographic;
import ru.stonlex.bukkit.holographic.impl.SimpleHolographic;
import ru.stonlex.bukkit.holographic.manager.ProtocolHolographicManager;
import ru.stonlex.bukkit.inventory.BaseInventory;
import ru.stonlex.bukkit.inventory.BaseInventoryManager;
import ru.stonlex.bukkit.inventory.impl.BasePaginatedInventory;
import ru.stonlex.bukkit.inventory.impl.BaseSimpleInventory;
import ru.stonlex.bukkit.messaging.BukkitMessagingManager;
import ru.stonlex.bukkit.scoreboard.BaseScoreboardBuilder;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.bukkit.utility.location.CuboidRegion;
import ru.stonlex.bukkit.vault.VaultServiceManager;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public interface StonlexBukkitApi {

    CommandManager COMMAND_MANAGER                  = (CommandManager.INSTANCE);
    VaultServiceManager VAULT_API                   = (VaultServiceManager.INSTANCE);
    VaultServiceManager.Registry VAULT_REGISTRY     = (VaultServiceManager.REGISTRY);
    ProtocolHolographicManager HOLOGRAPHIC_MANAGER  = (ProtocolHolographicManager.INSTANCE);

    BaseInventoryManager INVENTORY_MANAGER          = new BaseInventoryManager();
    BukkitMessagingManager MESSAGING_MANAGER        = new BukkitMessagingManager();

    Map<String, Integer> SERVERS_ONLINE_MAP         = new HashMap<>();


    /**
     * Получить онлайн сервера, имя которого
     * указано в аргументе
     *
     * @param serverName - имя сервера
     */
    static int getServerOnline(@NonNull String serverName) {
        return StonlexBukkitApi.SERVERS_ONLINE_MAP.getOrDefault(serverName.toLowerCase(), -1);
    }

    /**
     * Получить общую сумму онлайна всех
     * подключенных серверов по Bungee
     *
     * @return - Общий онлайн Bungee
     */
    static int getGlobalOnline() {
        return getServerOnline("GLOBAL");
    }


    /**
     * Создать обыкновенную голограмму
     *
     * @param location - начальная локация голограммы
     */
    static ProtocolHolographic createSimpleHolographic(@NonNull Location location) {
        return new SimpleHolographic(location);
    }

    /**
     * Создать кубоид блоков из двух по
     * двум начальным локациям
     *
     * @param location1 - начальная локация №1
     * @param location2 - начальная локация №2
     */
    static CuboidRegion createCuboid(@NonNull Location location1, @NonNull Location location2) {
        return new CuboidRegion(location1, location2);
    }


    /**
     * Создание {@link ItemStack} по Builder паттерну
     *
     * @param material - начальный тип предмета
     */
    static ItemUtil.ItemBuilder newItemBuilder(@NonNull Material material) {
        return ItemUtil.newBuilder(material);
    }

    /**
     * Создание {@link ItemStack} по Builder паттерну
     *
     * @param materialData - начальная дата предмета
     */
    static ItemUtil.ItemBuilder newItemBuilder(@NonNull MaterialData materialData) {
        return ItemUtil.newBuilder(materialData);
    }

    /**
     * Создание {@link ItemStack} по Builder паттерну
     *
     * @param itemStack - готовый {@link ItemStack} на клонирование и переработку
     */
    static ItemUtil.ItemBuilder newItemBuilder(@NonNull ItemStack itemStack) {
        return ItemUtil.newBuilder(itemStack);
    }


    /**
     * Создать обыкновенный инвентарь без абстракции
     *
     * @param inventoryRows     - количество строк со слотами
     * @param inventoryTitle    - название инвентаря
     * @param inventoryConsumer - обработчик отрисовки предметов
     */
    static BaseInventory createSimpleInventory(int inventoryRows, @NonNull String inventoryTitle,
                                                @NonNull BiConsumer<Player, BaseInventory> inventoryConsumer) {

        return new BaseSimpleInventory(inventoryRows, inventoryTitle) {

            @Override
            public void drawInventory(@NonNull Player player) {
                inventoryConsumer.accept(player, this);
            }
        };
    }

    /**
     * Создать страничный инвентарь без абстракции
     *
     * @param inventoryRows     - количество строк со слотами
     * @param inventoryTitle    - название инвентаря
     * @param inventoryConsumer - обработчик отрисовки предметов
     */
    static BasePaginatedInventory createPaginatedInventory(int inventoryRows, @NonNull String inventoryTitle,
                                                            @NonNull BiConsumer<Player, BasePaginatedInventory> inventoryConsumer) {

        return new BasePaginatedInventory(inventoryRows, inventoryTitle) {

            @Override
            public void drawInventory(@NonNull Player player) {
                inventoryConsumer.accept(player, this);
            }
        };
    }


    /**
     * Зарегистрировать наследник {@link BaseCommand}
     * как bukkit команду на {@link StonlexBukkitApiPlugin}
     *
     * @param baseCommand - команда
     */
    static void registerCommand(@NonNull BaseCommand<?> baseCommand) {
        COMMAND_MANAGER.registerCommand(baseCommand, baseCommand.getName(), baseCommand.getAliases().toArray(new String[0]));
    }

    /**
     * Зарегистрировать наследник {@link BaseCommand}
     * как bukkit команду на {@link StonlexBukkitApiPlugin}
     *
     * @param baseCommand    - команда
     * @param commandName    - основная команда
     * @param commandAliases - дополнительные команды, обрабатывающие тот же класс (алиасы)
     */
    static void registerCommand(@NonNull BaseCommand<?> baseCommand, @NonNull String commandName, @NonNull String... commandAliases) {
        COMMAND_MANAGER.registerCommand(baseCommand, commandName, commandAliases);
    }

    /**
     * Зарегистрировать наследник {@link BaseCommand}
     * как bukkit команду
     *
     * @param plugin      - плагин, на который регистрировать команду
     * @param baseCommand - команда
     */
    static void registerCommand(@NonNull Plugin plugin, @NonNull BaseCommand<?> baseCommand) {
        COMMAND_MANAGER.registerCommand(plugin, baseCommand, baseCommand.getName(), baseCommand.getAliases().toArray(new String[0]));
    }

    /**
     * Зарегистрировать наследник {@link BaseCommand}
     * как bukkit команду
     *
     * @param plugin         - плагин, на который регистрировать команду
     * @param baseCommand    - команда
     * @param commandName    - основная команда
     * @param commandAliases - дополнительные команды, обрабатывающие тот же класс (алиасы)
     */
    static void registerCommand(@NonNull Plugin plugin, @NonNull BaseCommand<?> baseCommand, @NonNull String commandName, @NonNull String... commandAliases) {
        COMMAND_MANAGER.registerCommand(plugin, baseCommand, commandName, commandAliases);
    }


    /**
     * Создание {@link ru.stonlex.bukkit.scoreboard.BaseScoreboard} по Builder паттерну
     * с рандомным названием objective
     */
    static BaseScoreboardBuilder newScoreboardBuilder() {
        return BaseScoreboardBuilder.newScoreboardBuilder();
    }

    /**
     * Создание {@link ru.stonlex.bukkit.scoreboard.BaseScoreboard} по Builder паттерну
     *
     * @param objectiveName - название scoreboard objective
     */
    static BaseScoreboardBuilder newScoreboardBuilder(@NonNull String objectiveName) {
        return BaseScoreboardBuilder.newScoreboardBuilder(objectiveName);
    }

}
