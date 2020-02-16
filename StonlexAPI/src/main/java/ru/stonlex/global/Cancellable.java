package ru.stonlex.global;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancelled);
}
