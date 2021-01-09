package ru.stonlex.bukkit.utility.location;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import ru.stonlex.bukkit.utility.ItemUtil;

import java.util.Arrays;
import java.util.Collection;

@UtilityClass
public class BlockUtil {

    public boolean isEmpty(@NonNull Block block) {
        return block.getTypeId() == 0;
    }

    public BlockFace getEmptyRelative(@NonNull Block block, boolean checkObliquely) {
        return getEmptyRelative(block, 1, checkObliquely);
    }

    public BlockFace getEmptyRelative(@NonNull Block block, int distance, boolean checkObliquely) {
        return getAvailableRelativeByType(block, ItemUtil.EMPTY_ITEM_TYPE, distance, checkObliquely);
    }

    public BlockFace getEmptyRelative(@NonNull Block block, int distance) {
        return getAvailableRelativeByType(block, ItemUtil.EMPTY_ITEM_TYPE, distance, true);
    }

    public BlockFace getEmptyRelative(@NonNull Block block) {
        return getAvailableRelativeByType(block, ItemUtil.EMPTY_ITEM_TYPE, 1, true);
    }

    public BlockFace getAvailableRelativeByType(@NonNull Block block, @NonNull Material material,
                                                int distance, boolean checkObliquely) {

        Collection<BlockFace> blockFaceCollection = !checkObliquely ? Arrays.asList(BlockFace.UP, BlockFace.NORTH, BlockFace.EAST,
                BlockFace.SOUTH, BlockFace.WEST, BlockFace.DOWN) : Arrays.asList(BlockFace.values());

        for (BlockFace blockFace : blockFaceCollection) {
            if (!block.getRelative(blockFace, distance).getType().equals(material)) {
                continue;
            }

            return blockFace;
        }

        return BlockFace.SELF;
    }

    public BlockFace getAvailableRelativeByType(@NonNull Block block, @NonNull Material material,
                                                int distance) {

        return getAvailableRelativeByType(block, material, distance, true);
    }

    public BlockFace getAvailableRelativeByType(@NonNull Block block, @NonNull Material material) {

        return getAvailableRelativeByType(block, material, 1, true);
    }

}
