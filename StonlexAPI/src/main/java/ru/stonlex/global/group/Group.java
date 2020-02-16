package ru.stonlex.global.group;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Group {

    /**
     * Стандартная группа, которая выдается всем
     * при первом заходе на сервер.
     */
    DEFAULT(0, '7', "L", "Игрок", "§7", "§7"),

    /**
     * Привилегилированные группы. Они имеют дополнительные права,
     * имеют шанс выпасть в донат-кейсе, также можно купить на сайте
     */
    IRON(1, 'f', "K", "Iron", "§f§lIRON§f", "§7"),
    STAR(2, '6', "J", "Star", "§6§lSTAR§6", "§7"),
    MASTER(3, 'd', "I", "Master", "§d§lMASTER§d", "§7"),
    PRO(4, 'a', "H", "Pro", "§a§lPRO§a", "§7"),

    /**
     * Универсальные группы. В данную категорию входят такие статусы,
     * как BUILDER и YOUTUBE.
     */
    BUILDER(10, 'b', "G", "Строитель", "§b§lBUILDER§b", "§7"),
    YOUTUBE(20, 'e', "F", "Ютубер", "§e§lYOUTUBE§e", "§7"),

    /**
     * Персонал. Данные группы выдаются только тем, кто является
     * лицом и персоналом проекта, их нельзя купить или получить
     * за какие-то достижения.
     */
    HELPER(30, '2', "E", "Помощник", "§2§lHELPER§2", "§f"),
    MODER(40, '9', "D", "Модератор", "§9§lMODER§9", "§f"),
    SUPPORT(50, '3', "C", "Куратор", "§3§lSUPPORT§3", "§f"),

    /**
     * Администраторы и разработчики проекта.
     */
    DEVELOPER(90, 'c', "B", "Разработчик", "§c§lDEVELOPER§c", "§f"),
    ADMIN(100, '4', "A", "Администратор", "§c§lADMIN§c", "§f"),

    /**
     * Уникальная группа, которая выдается очень "хорошим" людям
     */
    PIDOR(5, 'b', "M", "Пидорас", "§b§lPIDOR§b", "§7");


    private final int level;

    private final char colorChar;

    private final String tagPriority;
    private final String name;
    private final String prefix;
    private final String suffix;


    /**
     * Получение статуса по его уровню
     */
    public static Group getGroupByLevel(int level) {
        return Arrays.stream( values() )
                .filter(group -> group.getLevel() == level)
                .iterator().next();
    }


    /**
     * Получение цветового кода группы
     */
    public String getColor() {
        return "§" + colorChar;
    }

    /**
     * Получение цветного наименования статуса
     */
    public String getColouredName() {
        return getColor() + name;
    }



    /**
     * Является ли статус игроком
     */
    public boolean isDefault() {
        return level == 0;
    }

    /**
     * Является ли статус персоналом
     */
    public boolean isStaff() {
        return level >= 30;
    }

    /**
     * Является ли статус донатом
     */
    public boolean isDonate() {
        return level >= 1 && level <= 4;
    }

    /**
     * Является ли статус администратором
     */
    public boolean isAdmin() {
        return level >= 90;
    }

    /**
     * Является ли статус универсальным
     */
    public boolean isUniversal() {
        return level >= 10 && level <= 20;
    }

}
