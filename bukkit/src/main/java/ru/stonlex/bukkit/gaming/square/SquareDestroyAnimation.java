package ru.stonlex.bukkit.gaming.square;

import lombok.NonNull;
import org.bukkit.entity.Player;

public interface SquareDestroyAnimation {

    void destroy(@NonNull Player player, @NonNull GamingSquare gamingSquare);
}
