package ru.stonlex.example.custom;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.bukkit.utility.custom.CustomItem;

public class ExampleCustomItem extends CustomItem {

    public ExampleCustomItem() {
        super(ItemUtil.newBuilder(Material.STICK)

                .setName("§aПалка-хуялка")
                .build());
    }

    @Override
    public void onInteract(@NonNull Player player, @NonNull Action mouseAction, Location location) {
        switch (mouseAction) {

            case RIGHT_CLICK_BLOCK:
                player.sendMessage("Ты клинул ПРАВОЙ кнопкой мыши по блоку с палкой-хуялкой в руках");
                break;

            case LEFT_CLICK_BLOCK:
                player.sendMessage("Ты клинул ЛЕВОЙ кнопкой мыши по блоку с палкой-хуялкой в руках");
                break;
        }
    }

}
