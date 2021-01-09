package ru.stonlex.bukkit.gaming;

import ru.stonlex.bukkit.gaming.item.GamingItemManager;
import ru.stonlex.bukkit.gaming.setting.GamingSettings;
import ru.stonlex.bukkit.gaming.team.GamingTeamManager;

public interface GameAPI {

    GamingSettings    SETTINGS            = new GamingSettings();
    GamingTeamManager TEAM_MANAGER        = new GamingTeamManager();
    GamingItemManager ITEM_MANAGER        = new GamingItemManager();


    // TODO: Доделать листенер игровых настроек
    // TODO: Доделать выозовы игровых событий
    // TODO: Документировать игровые процессы и прочие объекты
    // TODO: Сделать парсер предметов для сундуков в ItemUtil
}
