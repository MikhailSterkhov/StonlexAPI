package ru.stonlex.bukkit.protocollib.entity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class FakeEntityRegistry {

    public static final FakeEntityRegistry INSTANCE = new FakeEntityRegistry();

    protected final TIntObjectMap<FakeBaseEntity> fakeEntityIdsMap = new TIntObjectHashMap<>();


    public synchronized FakeBaseEntity getEntityById(int entityId) {
        return fakeEntityIdsMap.get(entityId);
    }

    public synchronized void registerEntity(@NonNull FakeBaseEntity fakeBaseEntity) {
        if (hasEntity(fakeBaseEntity)) {
            return;
        }

        fakeEntityIdsMap.put(fakeBaseEntity.entityId, fakeBaseEntity);
    }

    public synchronized void unregisterEntity(@NonNull FakeBaseEntity fakeBaseEntity) {
        if (!hasEntity(fakeBaseEntity)) {
            return;
        }

        fakeEntityIdsMap.remove(fakeBaseEntity.entityId);
    }

    public synchronized boolean hasEntity(@NonNull FakeBaseEntity fakeBaseEntity) {
        return fakeEntityIdsMap.containsKey(fakeBaseEntity.entityId);
    }


    public synchronized Collection<FakeBaseEntity> getEntitiesByScope(@NonNull FakeEntityScope fakeEntityScope) {
        return fakeEntityIdsMap.valueCollection()
                .stream()
                .filter(fakeBaseEntity -> fakeBaseEntity.entityScope.equals(fakeEntityScope))
                .collect(Collectors.toSet());
    }

    public synchronized Collection<FakeBaseEntity> getReceivableEntities(@NonNull Player player) {
        Collection<FakeBaseEntity> receivableEntities = new ArrayList<>();

        for (FakeBaseEntity fakeBaseEntity : fakeEntityIdsMap.valueCollection()) {

            if (fakeBaseEntity.hasReceiver(player)) {
                receivableEntities.add(fakeBaseEntity);
            }
        }

        return receivableEntities;
    }

    public synchronized Collection<FakeBaseEntity> getViewableEntities(@NonNull Player player) {
        Collection<FakeBaseEntity> viewableEntities = new ArrayList<>();

        for (FakeBaseEntity fakeBaseEntity : fakeEntityIdsMap.valueCollection()) {

            if (fakeBaseEntity.hasViewer(player)) {
                viewableEntities.add(fakeBaseEntity);
            }
        }

        return viewableEntities;
    }

}
