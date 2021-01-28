package ru.stonlex.bukkit.gaming.square.destroy;

import lombok.NonNull;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;
import ru.stonlex.bukkit.gaming.square.GamingSquare;
import ru.stonlex.bukkit.gaming.square.SquareDestroyAnimation;
import ru.stonlex.bukkit.utility.location.BlockUtil;
import ru.stonlex.bukkit.utility.ItemUtil;

public class GamingSquareBurningDestroyAnimation implements SquareDestroyAnimation {

    @Override
    public void destroy(@NonNull Player player,
                        @NonNull GamingSquare gamingSquare) {
        Block startBlock = gamingSquare.getCuboidRegion(player).corners()[0];

        new BukkitRunnable() {
            int blockCount = gamingSquare.getCuboidRegion(player).getVolume();

            @Override
            public void run() {
                if (blockCount <= 0) {
                    cancel();
                }

                BlockFace relativeFace  = BlockUtil.getAvailableRelativeByType(startBlock, startBlock.getType());
                Block relativeBlock     = startBlock.getRelative(relativeFace);

                breakBlock(relativeBlock);
                blockCount--;
            }

        }.runTaskTimer(StonlexBukkitApiPlugin.getProvidingPlugin(StonlexBukkitApiPlugin.class), 0, 10);

    }

    protected void breakBlock(@NonNull Block block) {
        block.setType(ItemUtil.EMPTY_ITEM_TYPE);

        block.getWorld().playSound(block.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1, 1);
        block.getWorld().playEffect(block.getLocation(), Effect.SMOKE, 0, 1);
    }

}
