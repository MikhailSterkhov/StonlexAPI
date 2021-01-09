package ru.stonlex.bukkit.gaming.square;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import ru.stonlex.bukkit.gaming.item.GamingItem;
import ru.stonlex.bukkit.gaming.item.GamingItemCategory;
import ru.stonlex.bukkit.gaming.player.GamingPlayer;
import ru.stonlex.bukkit.utility.location.CuboidRegion;
import ru.stonlex.global.utility.CollectionUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

@Getter
public class GamingSquare extends GamingItem {

    protected final int squareRadius;

    protected final Multimap<Player, Block> playerCageMap = LinkedHashMultimap.create();
    protected final Map<Player, CuboidRegion> playerCuboidMap = new HashMap<>();

    public GamingSquare(double squareCost, String itemName, MaterialData materialData, int squareRadius) {
        super(squareCost, itemName, materialData, GamingItemCategory.SQUARE_CATEGORY);

        this.squareRadius = squareRadius;
    }


// ======================================= // ANIMATIONS // ======================================= //

    @Setter
    protected SquareDestroyAnimation squareDestroyAnimation;

    @Setter
    protected SquareBuildAnimation squareBuildAnimation;

// ======================================= // ANIMATIONS // ======================================= //


    /**
     * Процесс выстраивания клетки и кеширования
     * поставленных блоков
     *
     * @param location - центральная локация для создания
     *                 клетки по указанному радиусу
     */
    protected void build(@NonNull Player player, @NonNull Location location) {
        CuboidRegion outsideCuboid = new CuboidRegion(

                location.add(squareRadius, squareRadius, squareRadius),
                location.subtract(squareRadius, squareRadius, squareRadius)
        );

        CuboidRegion insideCuboid = new CuboidRegion(

                location.add(squareRadius-1, squareRadius-1, squareRadius-1),
                location.subtract(squareRadius-1, squareRadius-1, squareRadius-1)
        );

        Collection<Block> blockCollection = new LinkedList<>();

        for (Block block : CollectionUtil.filterCollection(outsideCuboid.getBlocks(), block -> !insideCuboid.contains(block))) {
            blockCollection.add(block);
            block.setTypeIdAndData(materialData.getItemTypeId(), materialData.getData(), true);
        }

        playerCageMap.putAll(player, blockCollection);
        playerCuboidMap.put(player, outsideCuboid);
    }

    /**
     * Разрушить все кешированные блоки клетки
     *
     * Если #squareDestroyAnimation : {@link SquareDestroyAnimation} != null,
     * то процесс разрушения будет выполнять он
     */
    protected void destroy(@NonNull Player player) {
        if (squareDestroyAnimation != null) {
            squareDestroyAnimation.destroy(player, this);

            return;
        }

        for (Block block : playerCageMap.get(player))
            block.setType(Material.AIR);

        playerCageMap.removeAll(player);
        playerCuboidMap.remove(player);
    }

    public CuboidRegion getCuboidRegion(@NonNull Player player) {
        return playerCuboidMap.get(player);
    }


    @Override
    public void accept(@NonNull GamingPlayer gamingPlayer) {
        Player player = gamingPlayer.toBukkit();
        Location location = player.getLocation().clone().subtract(0, 1, 0);

        build(player, location);

        player.teleport(location);
    }

    @Override
    public void cancel(@NonNull GamingPlayer gamingPlayer) {
        destroy(gamingPlayer.toBukkit());
    }

}
