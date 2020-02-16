package ru.stonlex.bukkit.board.manager;

import ru.stonlex.bukkit.board.MoonSidebar;
import ru.stonlex.bukkit.board.MoonSidebarBuilder;
import ru.stonlex.global.utility.AbstractCacheManager;

/**
 * @Author ItzStonlex.
 * @VK https://vk.com/itzstonlex
 * <p>
 * (Created on 01.08.2019 15:25)
 */
public final class SidebarManager extends AbstractCacheManager<MoonSidebar> {

    /**
     * Вызов нового билдера
     */
    public MoonSidebarBuilder newBuilder() {
        return new MoonSidebarBuilder();
    }


    /**
     * Кеширование скорборда
     *
     * @param sidebarName - Кешированное имя скорборда.
     * @param moonSidebar - Скорборд, который нужно закешировать.
     */
    public void cacheSidebar(String sidebarName, MoonSidebar moonSidebar) {
        cache(sidebarName.toLowerCase(), moonSidebar);
    }

    /**
     * Получение скорборда из кеша
     *
     * @param sidebarName - Имя получаемого скорборда
     */
    public MoonSidebar getSidebar(String sidebarName) {
        return get(sidebarName.toLowerCase());
    }

}