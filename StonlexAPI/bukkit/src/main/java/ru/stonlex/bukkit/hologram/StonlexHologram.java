package ru.stonlex.bukkit.hologram;

import lombok.Getter;
import ru.stonlex.bukkit.protocol.entity.impl.FakeArmorStand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class StonlexHologram {

    private Location location;

    public StonlexHologram(Location location) {
        this.location = location;
    }


    private final List<FakeArmorStand> entities = new ArrayList<>();
    private final List<String> lines = new ArrayList<>();

    private Consumer<Player> clickAction;


    private static final double distance = 0.25D;


    /**
     * Получить количество строк
     */
    public int getLineCount() {
        return getLines().size();
    }

    /**
     * Получить строку по ее индексу
     *
     * @param index - индекс строки
     */
    public String getLine(int index) {
        return lines.get(index);
    }

    /**
     * Добавить строку
     *
     * @param line - строка
     */
    public void addLine(String line) {
        if (location == null || location.getWorld() == null) {
            return;
        }

        FakeArmorStand stand = new FakeArmorStand(location.clone().add(0, -(distance * lines.size()), 0));

        stand.setInvisible(true);
        stand.setCustomNameVisible(true);
        stand.setCustomName(line);

        stand.setClickAction(clickAction);

        Bukkit.getOnlinePlayers().forEach(stand::addReceiver);

        entities.add(stand);
        lines.add(line);
    }

    /**
     * Изменить определенную строку выбранную по индексу
     * на другую
     *
     * @param index - индекс выбранной строки
     * @param line - новая строка
     */
    public void modifyLine(int index, String line) {
        lines.set(index, line);

        refreshHologram();
    }

    /**
     * Заспавнить голограмму всем игрокам онлайн
     */
    public void spawn() {
        Bukkit.getOnlinePlayers().forEach(this::spawnToPlayer);
    }

    /**
     * Заспавнить голограмму определенному игроку
     *
     * @param player - игрок
     */
    public void spawnToPlayer(Player player) {
        entities.forEach(fakeArmorStand -> fakeArmorStand.addReceiver(player));
    }

    /**
     * Удалить голограмму для всех
     */
    public void remove() {
        Bukkit.getOnlinePlayers().forEach(this::removeToPlayer);

        entities.clear();
    }

    /**
     * Удалитьб голограмму для определенного игрока
     *
     * @param player - игрок
     */
    public void removeToPlayer(Player player) {
        entities.forEach(fakeArmorStand -> fakeArmorStand.removeReceiver(player));
    }

    /**
     * Телепортировать голограмму на другую локацию
     *
     * @param location - новая локация
     */
    public void teleport(Location location) {
        this.location = location;

        int count = 0;
        for (FakeArmorStand stand : entities) {
            stand.teleport(location.clone().add(0, -(distance * count), 0));

            count++;
        }
    }

    /**
     * Установить дейтствие при клике на голограмму
     *
     * @param clickAction - действие при клике
     */
    public void setClickAction(Consumer<Player> clickAction) {
        this.clickAction = clickAction;

        entities.forEach(fakeArmorStand -> fakeArmorStand.setClickAction(clickAction));
    }

    /**
     * Обновить голограмму
     */
    public void refreshHologram() {
        for (int i = 0; i < lines.size(); i++) {

            String line = lines.get(i);
            FakeArmorStand stand = entities.get(i);

            stand.setCustomName(line);
        }

        teleport(location);
    }

}
