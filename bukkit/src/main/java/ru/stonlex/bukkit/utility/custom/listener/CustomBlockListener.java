package ru.stonlex.bukkit.utility.custom.listener;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import ru.lattycraft.bukkit.util.MetadataUtil;
import ru.stonlex.bukkit.utility.custom.CustomBlock;

public class CustomBlockListener implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlaceEvent) {
        Block block = blockPlaceEvent.getBlock();

        if (MetadataUtil.hasMetadata(block, "custom")) {
            CustomBlock customBlock = MetadataUtil.getMetadata(block, "custom", CustomBlock.class);
            customBlock.onPlayerPlace(blockPlaceEvent);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreakEvent) {
        Block block = blockBreakEvent.getBlock();

        if (MetadataUtil.hasMetadata(block, "custom")) {
            CustomBlock customBlock = MetadataUtil.getMetadata(block, "custom", CustomBlock.class);
            customBlock.onPlayerBreak(blockBreakEvent);

            if (!blockBreakEvent.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                customBlock.drop(block.getLocation());
            }

            blockBreakEvent.setExpToDrop(0);
            blockBreakEvent.setDropItems(false);
        }
    }

}
