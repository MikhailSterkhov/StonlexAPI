package ru.stonlex.bukkit.menu;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.stonlex.bukkit.menu.button.InventoryButton;
import ru.stonlex.bukkit.menu.button.applicable.ButtonApplicable;
import ru.stonlex.bukkit.utility.ItemUtil;
import ru.stonlex.global.utility.NumberUtil;

import java.util.*;

@Getter
public abstract class PageStonlexMenu extends StonlexMenu {

    private int page, pagesCount;

    private final String inventoryTitle;
    private final PageItemMap itemMap = new PageItemMap();


    /**
     * Инициализация инвентаря
     *
     * @param inventoryTitle - Заголовок инвентаря
     * @param inventoryRows  - Количество строк в инвентаре
     */
    public PageStonlexMenu(String inventoryTitle, int inventoryRows) {
        this(0, inventoryTitle, inventoryRows);
    }

    /**
     * Инициализация инвентаря
     *
     * @param page           - Страница инвентаря
     * @param inventoryTitle - Заголовок инвентаря
     * @param inventoryRows  - Количество строк в инвентаре
     */
    private PageStonlexMenu(int page, String inventoryTitle, int inventoryRows) {
        super(inventoryTitle.concat(" | " + (page + 1)), inventoryRows);

        this.page = page;
        this.inventoryTitle = inventoryTitle;
    }


    /**
     * На страницу назад
     */
    private void backward(Player player) {
        if (page - 1 < 0) {
            throw new RuntimeException( String.format("Page cannot be < 0 (%s - 1 < 0)", page) );
        }

        this.page--;

        setTitle( inventoryTitle.concat(" | " + (page + 1)) );
        openInventory(player);
    }

    /**
     * На страницу вперед
     */
    private void forward(Player player) {
        if (page >= pagesCount) {
            throw new RuntimeException( String.format("Page cannot be >= max pages count (%s >= %s)", page, pagesCount) );
        }

        this.page++;

        setTitle( inventoryTitle.concat(" | " + (page + 1)) );
        openInventory(player);
    }

    /**
     * Построение страничного инвентаря
     */
    private void buildPage(Player player) {
        itemMap.clear();

        clearInventory();
        drawPagedInventory(player, page + 1);

        this.pagesCount = itemMap.getButtonList().size() / itemMap.slotsList.size();

        if ( !(page >= pagesCount) ) {
            setItem(getBukkitInventory().getSize() - 3, ItemUtil.getItemStack(Material.ARROW,
                    "§eВперед"), (player1, event) -> forward(player));
        }

        if ( !(page - 1 < 0) ) {
            setItem(getBukkitInventory().getSize() - 5, ItemUtil.getItemStack(Material.ARROW,
                    "§eНазад"), (player1, event) -> backward(player));
        }


        for (int i = 0; i < itemMap.slotsList.size(); i++) {
            int index = page * itemMap.slotsList.size() + i;

            if (itemMap.getButtonList().size() <= index) {
                break;
            }

            int slot = itemMap.getSlotsList().get(i);

            InventoryButton inventoryButton = itemMap.getButtonList().get(index);
            ItemStack itemStack = inventoryButton.getItemStack();

            setItem(slot, itemStack, inventoryButton.getButtonApplicable());
        }
    }

    /**
     * Добавить предмет на страницу
     *
     * @param itemStack - предмет
     */
    public void addItemToPage(ItemStack itemStack) {
        itemMap.addItem(itemStack);
    }

    /**
     * Добавить предмет на страницу
     *
     * @param itemStack - предмет
     * @param buttonApplicable - действие при клике
     */
    public void addItemToPage(ItemStack itemStack, ButtonApplicable buttonApplicable) {
        itemMap.addItem(itemStack, buttonApplicable);
    }

    /**
     * Установить количество используемых слотов на страницу
     *
     * @param slotArray - слоты
     */
    public void setPageSize(Integer... slotArray) {
        itemMap.setSlotsList( Arrays.asList(slotArray) );
    }

    /**
     * Установить количество используемых слотов на страницу
     *
     * @param slotList - слоты
     */
    public void setPageSize(List<Integer> slotList) {
        itemMap.setSlotsList( slotList );
    }

    /**
     * Добавить линию в разметку страницы
     *
     * @param rowIndex - индекс линии
     * @param sideTab - отступ по слотам для боков линии
     * @param sidesEnable - слоты по бокам линии (true - оставить, false - убрать)
     */
    public void addRowToPageSize(int rowIndex, int sideTab, boolean sidesEnable) {
        List<Integer> currentSlotsSize = itemMap.getSlotsList();

        if (rowIndex <= 0) {
            throw new IllegalArgumentException("row index must be > 0");
        }

        if (rowIndex >= 7) {
            throw new IllegalArgumentException("row index must be < 6");
        }

        int startSlotIndex = (rowIndex * 9) - 8;
        int endSlotIndex = startSlotIndex + 9;

        if ( sidesEnable ) {
            if ( sideTab < 0 ) {
                throw new IllegalArgumentException("side tab must be > 0");
            }

            startSlotIndex += sideTab;
            endSlotIndex -= sideTab;
        }

        for ( int slotIndex : NumberUtil.toManyArray(startSlotIndex, endSlotIndex) ) {
            currentSlotsSize.add( slotIndex );
        }

        setPageSize( currentSlotsSize );
    }

    @Override
    public void drawInventory(Player player) {
        buildPage(player);
    }

    /**
     * Отрисовка страничного инвентаря
     *
     * @param player - игрок, которому отрисовывать
     * @param page - страница
     */
    public abstract void drawPagedInventory(Player player, int page);


    @Getter
    public static class PageItemMap {

        private List<InventoryButton> buttonList = new LinkedList<>();

        @Setter
        private List<Integer> slotsList = new ArrayList<>();


        /**
         * Добавление кнопки в инвентарь
         *
         * @param itemStack - предмет
         */
        public void addItem(ItemStack itemStack) {
            addItem(itemStack, (player, event) -> {});
        }

        /**
         * Добавление кликабельной кнопки в инвентарь
         *
         * @param itemStack - предмет
         * @param buttonApplicable - кликабельность
         */
        public void addItem(ItemStack itemStack, ButtonApplicable buttonApplicable) {
            InventoryButton inventoryButton = new InventoryButton(itemStack, buttonApplicable);

            buttonList.add(inventoryButton);
        }

        /**
         * Очистить все списки
         */
        public void clear() {
            this.slotsList.clear();
            this.buttonList.clear();
        }

    }

}
