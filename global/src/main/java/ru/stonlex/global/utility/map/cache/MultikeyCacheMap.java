package ru.stonlex.global.utility.map.cache;

import ru.stonlex.global.utility.map.MultikeyMap;

public interface MultikeyCacheMap<I> extends MultikeyMap<I> {

    void cleanUp();
}
