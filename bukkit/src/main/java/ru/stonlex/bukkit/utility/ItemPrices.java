package ru.stonlex.bukkit.utility;

import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ItemPrices {

    @Getter
    private final Map<Material, Double> itemPrices = new HashMap<>();


    public void addType(@NonNull Material material, double itemCost) {
        itemPrices.put(material, itemCost);
    }

    public double getTypeCost(@NonNull Material material) {
        return itemPrices.get(material);
    }

    public int getTypeCostInt(@NonNull Material material) {
        return (int) Math.round(getTypeCost(material));
    }
}
