package ru.stonlex.example.game;

import org.bukkit.ChatColor;
import ru.stonlex.bukkit.gaming.GameProcess;
import ru.stonlex.bukkit.gaming.database.GamingDatabase;
import ru.stonlex.global.mysql.MysqlConnection;

public class ExampleGameProcess extends GameProcess {

    private MysqlConnection mysqlConnection;

    public ExampleGameProcess(int playersInTeamCount) {
        super(playersInTeamCount);

        GamingDatabase.newDatabase(mysqlConnection, "SkyWars", "Solo")
                .addPlayerDataKey("wins", gamingPlayer -> gamingPlayer.getPlayerData("wins", int.class))
                .addPlayerDataKey("kills", gamingPlayer -> gamingPlayer.getPlayerData("kills", int.class))

                .build(this);
    }


    @Override
    protected void onStart() {
        alert(ChatColor.GREEN + "Игра началась!");
    }

    @Override
    protected void onEnd() {
        alert(ChatColor.RED + "Игра окончена!");
    }
}
