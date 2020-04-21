package ru.stonlex.bukkit.game.item.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.stonlex.bukkit.game.item.GameItem;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ItemType {

    private final int typeId;

    private final String typeName;
    private final String[] typeDescription;

    private final List<GameItem> itemList = new ArrayList<>();
}
