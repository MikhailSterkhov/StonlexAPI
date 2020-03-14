package ru.stonlex.bukkit.board.manager;

import ru.stonlex.bukkit.board.StonlexSidebar;
import ru.stonlex.bukkit.board.StonlexSidebarBuilder;
import ru.stonlex.global.utility.AbstractCacheManager;

/**
 * @Author ItzStonlex.
 * @VK https://vk.com/itzstonlex
 * <p>
 * (Created on 01.08.2019 15:25)
 */
public final class SidebarManager extends AbstractCacheManager<StonlexSidebar> {

    /**
     * Вызов нового билдера
     */
    public StonlexSidebarBuilder newBuilder() {
        return new StonlexSidebarBuilder();
    }


    /**
     * Кеширование скорборда
     *
     * @param sidebarName - Кешированное имя скорборда.
     * @param stonlexSidebar - Скорборд, который нужно закешировать.
     */
    public void cacheSidebar(String sidebarName, StonlexSidebar stonlexSidebar) {
        cache(sidebarName.toLowerCase(), stonlexSidebar);
    }

    /**
     * Получение скорборда из кеша
     *
     * @param sidebarName - Имя получаемого скорборда
     */
    public StonlexSidebar getSidebar(String sidebarName) {
        return get(sidebarName.toLowerCase());
    }

}