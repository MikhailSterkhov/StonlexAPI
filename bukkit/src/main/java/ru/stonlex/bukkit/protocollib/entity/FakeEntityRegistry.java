package ru.stonlex.bukkit.protocollib.entity;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public final class FakeEntityRegistry {

    public static final FakeEntityRegistry INSTANCE = new FakeEntityRegistry();

    protected final TIntObjectMap<FakeEntityScope> fakeEntityScopeMap   = new TIntObjectHashMap<>();
    protected final TIntObjectMap<FakeBaseEntity> fakeEntityIdsMap      = new TIntObjectHashMap<>();


    public synchronized void registerPrototype(@NonNull FakeBaseEntity fakeBaseEntity) {
        register(FakeEntityScope.PROTOTYPE, fakeBaseEntity);
    }

    public synchronized void registerPublic(@NonNull FakeBaseEntity fakeBaseEntity) {
        register(FakeEntityScope.PUBLIC, fakeBaseEntity);
    }

    public synchronized void register(@NonNull FakeEntityScope fakeEntityScope, @NonNull FakeBaseEntity fakeBaseEntity) {
        FakeEntityScope previousEntityScope = fakeEntityScopeMap.get(fakeBaseEntity.getEntityId());

        if (previousEntityScope != null) {
            fakeEntityScopeMap.remove(fakeBaseEntity.getEntityId());
        }

        fakeEntityIdsMap.put(fakeBaseEntity.getEntityId(), fakeBaseEntity);
        fakeEntityScopeMap.put(fakeBaseEntity.getEntityId(), fakeEntityScope);

        fakeBaseEntity.entityScope = fakeEntityScope;
    }


    public synchronized FakeBaseEntity getEntityById(int entityId) {
        return fakeEntityIdsMap.get(entityId);
    }

}
