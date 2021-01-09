package ru.stonlex.bukkit.gaming.square.destroy;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import ru.stonlex.bukkit.gaming.square.GamingSquare;
import ru.stonlex.bukkit.gaming.square.SquareDestroyAnimation;

public class GamingSquareClearDestroyAnimation implements SquareDestroyAnimation {

    @Override
    public void destroy(@NonNull Player player,
                        @NonNull GamingSquare gamingSquare) {

        for (Block block : gamingSquare.getCuboidRegion(player).getBlocks()) {
            block.setType(Material.AIR);
        }
    }

}
