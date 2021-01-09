package ru.stonlex.global.utility;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@UtilityClass
public class CollectionUtil {

    /**
     * Проверить наличие элементов одной коллекции в другой
     *
     * @param mainCollection - главная коллекция, в которой будем находить элементы
     * @param targetCollection - целевая коллекция, из нее будет получать необходимые элементы
     */
    public <E> boolean containsElements(@NonNull Collection<E> mainCollection,
                                        @NonNull Collection<E> targetCollection) {

        for (E collectionElement : targetCollection) {
            if (mainCollection.contains(collectionElement)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Проверить наличие всех элементов одной коллекции в другой
     *
     * @param mainCollection - главная коллекция, в которой будем находить элеенты
     * @param targetCollection - целевая коллекция, из нее будет получать необходимые элементы
     */
    public <E> boolean containsAllElements(@NonNull Collection<E> mainCollection,
                                           @NonNull Collection<E> targetCollection) {
        int containsCounter = 0;

        for (E collectionElement : targetCollection) {
            if (mainCollection.contains(collectionElement)) {
                containsCounter++;
            }
        }

        return containsCounter >= targetCollection.size();
    }

    /**
     * Изменить строки коллекции, сделав
     * их все маленькими буквами
     *
     * @param collection - коллекция
     */
    public <T extends Collection<String>> T toLowerCase(@NonNull T collection) {
        return handleCollection(collection, String::toLowerCase);
    }

    /**
     * Изменить строки коллекции, сделав
     * их все большими буквами
     *
     * @param collection - коллекция
     */
    public <T extends Collection<String>> T toUpperCase(@NonNull T collection) {
        return handleCollection(collection, String::toUpperCase);
    }

    /**
     * Обработать элементы коллеции
     *
     * @param collection - коллекция
     * @param collectionHandler - обработчик коллекции
     */
    public <E, T extends Collection<E>> T handleCollection(@NonNull T collection, @NonNull CollectionHandler<E> collectionHandler) {
        List<E> collectionList = ((List<E>) collection);

        for (int index = 0 ; index < collection.size(); index++) {
            E collectionElement = collectionList.get(index);

            collectionList.set(index, collectionHandler.handleElement(collectionElement));
        }

        return collection;
    }

    /**
     * Перестроить коллекию в новую
     *
     * @param collectionFrom - коллеция, которую перерабатываем
     * @param newCollectionGeneric - новый тип дженерика коллекции
     * @param collectionRebuilder - обработчик переработки коллекции
     */
    public <F, T, C extends Collection<T>> C rebuildCollection(@NonNull Collection<? extends F> collectionFrom,
                                                               @NonNull Class<T> newCollectionGeneric,
                                                               @NonNull CollectionRebuilder<F, T> collectionRebuilder) {

        C resultCollection = (C) new ArrayList<T>();

        for (F elementFrom : collectionFrom) {
            resultCollection.add(collectionRebuilder.rebuildElement(elementFrom));
        }

        return resultCollection;
    }

    /**
     * Создать общую коллекцию из всех элементов
     * перечисленный коллекций в аргументе
     *
     * @param collections - коллекции для объединения
     */
    public <T, C extends Collection<T>> C createGlobalCollection(@NonNull C... collections) {
        Collection<T> globalCollection = new ArrayList<T>();

        for (C collection : collections) {
            globalCollection.addAll(collection);
        }

        return (C) globalCollection;
    }

    /**
     * Отфильтровать текущую коллекцию
     *
     * @param collection - коллеция
     * @param filterPredicate - фильтр элементов
     */
    public <T, C extends Collection<T>> C filterCollection(@NonNull C collection,
                                                           @NonNull Predicate<T> filterPredicate) {
        return (C) collection.stream()
                .filter(filterPredicate).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Обработчик изменения элементов коллекции
     *
     * @param <E> - тип элемента коллекции
     */
    public interface CollectionHandler<E> {

        /**
         * Обработать элемент коллекции
         *
         * @param element - элемент
         */
        E handleElement(E element);
    }

    /**
     * Обработчик переработки элементов коллекции
     *
     * @param <F> - тип элемента коллекции, которую перерабатывают
     * @param <T> - тип элемента коллекции, которую возвращают
     */
    public interface CollectionRebuilder<F, T> {

        /**
         * Обработать элемент коллекции
         *
         * @param element - элемент
         */
        T rebuildElement(F element);
    }

}
