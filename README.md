# StonlexAPI

***
##Обратная связь
* **[Discord chat](https://discord.gg/GmT9pUy8af)**    
* **[ВКонтакте](https://vk.com/itzstonlex)**

***

## Основная информация
В основном данная апи предназначена для написания плагинов, различных систем и игр в сфере Minecraft.

Она несет в себе множество функций, таких как управление базой данных, **~~Game API~~**, **ProtocolLib API**, множество удобных **утилит** и **адаптеров**, и даже **мультиязычность** (локализация сообщений).

***

## Bukkit-API


### `Создание команд:`

Теперь создавать, регистрировать и использовать команды стало куда проще! В данной разработке доступна реализации как для мелких, так и для больших команд, которые содержат огромное количество данных, алиасов и подкоманд.

Для начала создадим обычную команду при помощи `BaseCommand`, использовать которую может **ТОЛЬКО** игрок:
```java
public class ExamplePlayerCommand 
        extends BaseCommand<Player> {

    public ExamplePlayerCommand() {
        super("test", "testing");
    }

    @Override
    protected void onExecute(Player player, String[] args) {
        player.sendMessage(ChatColor.GREEN + "Вы успешно выполнили команду /player");
    }

}
```

Ранее я говорил о создании больших команд, которые помогут сэкономить множество проверок и большого количества кода. Речь шла о `BaseMegaCommand`:
```java
public class ExamplePlayerCommand
        extends BaseMegaCommand<Player> {

    public ExamplePlayerCommand() {
        super("megatest", "megatesting");
    }

    @Override
    protected void onUsage(Player player) {
        player.sendMessage("Список доступных подкоманд:");
        player.sendMessage(" - /megatest online");
        player.sendMessage(" - /megatest broadcast <сообщение>");
    }

    @CommandArgument(aliases = "players")
    protected void online(Player player, String[] args) {
        int playersCount = Bukkit.getOnlinePlayers().size();

        String onlinePlayers = Joiner.on(", ").join(Bukkit.getOnlinePlayers().stream().map(Player::getDisplayName).collect(Collectors.toSet()));

        player.sendMessage(String.format("Сейчас на сервере (%s): %s", playersCount, onlinePlayers));
    }

    @CommandArgument
    protected void broadcast(Player player, String[] args) {

        String broadcastMessage = ChatColor.translateAlternateColorCodes('&', Joiner.on(" ").join(args));

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendMessage("MegaTest Broadcast > " + broadcastMessage);
        }
    }

}
```
Каждый метод - это отдельная подкоманда, а аннотация `@CommandArgument` обозначает нужный метод подкомандой и избавит Вас от костылей, duplicated-кода, создав для определенной подкоманды указанные алиасы.

Весь менеджмент над Bukkit API происходит через один класс - `ru.stonlex.bukkit.StonlexBukkitApi`

Исходя из этого, регистрация команд происходит тоже через этот класс:
```javascript
StonlexBukkitApi.registerCommand(new ExamplePlayerCommand());
```

***
### `Конфигурации:`
Для начала попробуем создать обычную конфигурацию, которая ничего не загружает и не воспроизводит на основе `ru.stonlex.bukkit.configuration.BaseConfiguration`:
```java
public class TestConfiguration 
        extends BaseConfiguration {
    
    public TestConfiguration(@NonNull Plugin plugin) {
        super(plugin, "messages.yml");
    }

    @Override
    protected void onInstall(@NonNull FileConfiguration fileConfiguration) {
        // ...
    }

}
```

Теперь можно ее доделать так, чтобы это было похоже на конфигурацию, которая хранит в себе сообщения для локализации Вашего плагина:
```java
public class TestConfiguration 
        extends BaseConfiguration {

    protected final Map<String, String> messagesCacheMap = new HashMap<>();

    public TestConfiguration(@NonNull Plugin plugin) {
        super(plugin, "messages.yml");
    }

    @Override
    protected void onInstall(@NonNull FileConfiguration fileConfiguration) {
        for (String messageKey : fileConfiguration.getConfigurationSection("Messages").getKeys(false)) {

            String messageText = ChatColor.translateAlternateColorCodes('&', fileConfiguration.getString("Messages.".concat(messageKey)));
            messagesCacheMap.put(messageKey, messageText);
        }
    }
}
```

Хранить и использовать конфигурацию можно хоть как и хоть где, но мы так до сих пор и не уточнили, как ее создавать, инициализировать, и в прицнипе работать с ней.
```java
@Override
public void onEnable() {
    TestConfiguration configuration = new TestConfiguration(this);
    configuration.createIfNotExists();
}
```
В данном куске кода мы создали (или скопировали из ресурсов плагина) конфигурацию, инициализировали в ней данные, и можем спокойно пользоваться!
***

### `Голограммы`
Неужели **HolographicDisplays** теперь окончательно устарел?

По сути, так оно и есть, потому что **StonlexAPI** содержит в себе даже **API** для удобного создания голограмм с различными анимациями, обновлениями и типами строк.

Для начала попробуем понять, как вообще создаются голограммы:
```java
ProtocolHolographic protocolHolographic 
        = StonlexBukkitApi.createSimpleHolographic(location);
```

Текстовые строки:
```java
protocolHolographic.addTextLine(ChatColor.AQUA + "Этот клубничный пудинг был просто великолепен!");
```

Кликабельные строки:
```java
// Создание кликабельных голограмм
Consumer<Player> playerConsumer = player -> { //player = игрок, который кликнул

    player.sendMessage(ChatColor.GOLD + "Клик по голограмме прошел успешно!");
    player.sendMessage(ChatColor.GOLD + "Локация: " + LocationUtil.locationToString(protocolHolographic.getLocation()));
};

// Добавление строк в голограмму
protocolHolographic.addClickLine(ChatColor.YELLOW + "Разработчик данной API", playerConsumer);
protocolHolographic.addClickLine(ChatColor.GREEN + "https://vk.com/itzstonlex", playerConsumer);
```

Строчки с предметами:
```java
protocolHolographic.addDropLine(new ItemStack(Material.APPLE));
```

Пустые строчки:
```java
protocolHolographic.addEmptyLine();
```

И даже строчки с головами по нику или текстуре:
```java
protocolHolographic.addSkullLine("ItzStonlex", false);
protocolHolographic.addSkullLine("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDUyOGVkNDU4MDI0MDBmNDY1YjVjNGUzYTZiN2E5ZjJiNmE1YjNkNDc4YjZmZDg0OTI1Y2M1ZDk4ODM5MWM3ZCJ9fX0=", false);
```

Так как голограмма является пакетной, то ей можно манипулировать как угодно, пример тому функционал спавна и удаления этих голограм, взаимодействуя с игроками сервера:
```java
protocolHolographic.addReceivers(receiver); //заспавнить только для одного игрока
```
```java
protocolHolographic.removeReceivers(receiver); // удалить только для одного игрока
```
```java
protocolHolographic.addViewers(receiver); // показать только для одного игрока
```
```java
protocolHolographic.removeViewers(receiver); // скрыть только для одного игрока
```
```java
protocolHolographic.spawn(); // заспавнить для всех игроков, и даже для тех, кто еще не зашел
```
```java
protocolHolographic.remove(); // удалить голограмму как для всех, так и прекратить спавн для новых игроков
```
***

***
##Обратная связь
* **[Discord chat](https://discord.gg/GmT9pUy8af)**
* **[ВКонтакте](https://vk.com/itzstonlex)**
