package ru.stonlex.bukkit.board;

import lombok.NonNull;
import org.apache.commons.lang.RandomStringUtils;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.board.objective.SidebarObjective;

import java.util.function.Consumer;

/**
 * @Author ItzStonlex.
 * @VK https://vk.com/itzstonlex
 * <p>
 * (Created on 01.08.2019 15:25)
 */
public class MoonSidebarBuilder {

    private final MoonSidebar sidebar;


    public MoonSidebarBuilder() {
        this.sidebar = new MoonSidebar();

        SidebarObjective sidebarObjective = new SidebarObjective(RandomStringUtils.randomAlphabetic(16), "§6§lMoonStudio");

        sidebar.setObjective(sidebarObjective);
    }


    public MoonSidebarBuilder setDisplayName(String displayName) {
        sidebar.setDisplayName(displayName);

        return this;
    }

    public MoonSidebarBuilder setLine(int index, String line) {
        sidebar.setLine(index, line);

        return this;
    }

    public MoonSidebarBuilder newUpdater(@NonNull Consumer<MoonSidebar> task, long delay) {
        sidebar.getUpdater().newTask(task, delay);

        return this;
    }

    public void showToPlayer(Player player) {
        MoonSidebar oldSidebar = MoonSidebar.getPlayerSidebarsMap().get(player.getName().toLowerCase());

        if (oldSidebar != null) {
            oldSidebar.hide();
        }

        sidebar.send(player);

        sidebar.getUpdater().start();
    }

}