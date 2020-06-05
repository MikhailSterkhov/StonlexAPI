package ru.stonlex.bukkit.depend.vault;

public interface IVaultProvider<T> {

    /**
     * Зарегистрировать провайдер ваулта
     */
    void registerProvider();

    /**
     * Получить провайдер ваулта
     */
    T getProvider();

}
