package ru.stonlex.bukkit.utility;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import ru.stonlex.bukkit.StonlexBukkitApiPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@UtilityClass
public class MinecraftRecipes {

    public final String RECIPE_KEY_PREFIX = "TynixRecipe_";

    public Collection<ShapedRecipe> getCreatedRecipes() {
        Collection<ShapedRecipe> recipeCollection = new ArrayList<>();
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();

        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();

            if (!(recipe instanceof ShapedRecipe)) {
                continue;
            }

            ShapedRecipe shapedRecipe = ((ShapedRecipe) recipe);

            if (shapedRecipe.getKey().getKey().contains(RECIPE_KEY_PREFIX)) {
                recipeCollection.add(shapedRecipe);
            }
        }

        return recipeCollection;
    }

    public void createRecipe(@NonNull Plugin plugin, @NonNull String key, @NonNull ItemStack resultItem,
                             @NonNull Consumer<MinecraftRecipeData> recipeDataConsumer) {

        if (getCreatedRecipes().stream().map(shapedRecipe -> shapedRecipe.getKey().getNamespace()).collect(Collectors.toSet()).contains((RECIPE_KEY_PREFIX).concat(key))) {
            return;
        }

        MinecraftRecipeData minecraftRecipeData = new MinecraftRecipeData(resultItem);
        recipeDataConsumer.accept(minecraftRecipeData);

        String[] recipeShape = new String[3];
        NamespacedKey namespacedKey = new NamespacedKey(plugin, (RECIPE_KEY_PREFIX).concat(key));

        if (Bukkit.getServer().getRecipesFor(resultItem).stream().anyMatch(recipe -> (recipe instanceof ShapedRecipe) && ((ShapedRecipe) recipe).getKey().equals(namespacedKey))) {
            return;
        }

        ShapedRecipe shapedRecipe = new ShapedRecipe(namespacedKey, resultItem);

        int shapeLineCounter = 0;
        for (int i = 0 ; i < 9 ; i++) {
            ItemStack itemStack = minecraftRecipeData.recipeCraft[i];
            char ingredientChar = Character.toChars('a' + i)[0];

            if (itemStack == null) {
                ingredientChar = ' ';
            }

            String shapeLine = recipeShape[shapeLineCounter];
            recipeShape[shapeLineCounter] = (shapeLine == null ? "" : shapeLine) + ingredientChar;

            if ((i + 1) % 3 == 0) {
                shapeLineCounter++;
            }
        }

        shapedRecipe.shape(recipeShape);

        shapeLineCounter = 0;
        for (int i = 0; i < 9 ; i++) {

            if (i > 0 && i % 3 == 0) {
                shapeLineCounter++;
            }

            String shapeLine = recipeShape[shapeLineCounter];
            char ingredientChar = shapeLine.charAt(i - (shapeLineCounter * 3));

            if (ingredientChar == ' ') {
                continue;
            }

            shapedRecipe.setIngredient(ingredientChar, minecraftRecipeData.recipeCraft[i].getData());
        }

        Bukkit.getServer().addRecipe(shapedRecipe);
    }

    public void createRecipe(@NonNull String key, @NonNull ItemStack resultItem,
                             @NonNull Consumer<MinecraftRecipeData> recipeDataConsumer) {

        createRecipe(StonlexBukkitApiPlugin.getPlugin(StonlexBukkitApiPlugin.class), key, resultItem, recipeDataConsumer);
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public class MinecraftRecipeData {

        private final ItemStack recipeResult;
        private final ItemStack[] recipeCraft = new ItemStack[9];

        public void setCraftSlot(int craftSlot, @NonNull ItemStack itemStack) {
            if (craftSlot > 9 || craftSlot < 1) {
                return;
            }

            recipeCraft[craftSlot - 1] = itemStack;
        }

        public void setCraftSlot(int craftSlot, @NonNull Material material) {
            if (craftSlot > 9 || craftSlot < 1) {
                return;
            }

            recipeCraft[craftSlot - 1] = new ItemStack(material);
        }

        public void setCraftSlot(int craftSlot, @NonNull MaterialData materialData) {
            if (craftSlot > 9 || craftSlot < 1) {
                return;
            }

            recipeCraft[craftSlot - 1] = materialData.toItemStack(1);
        }
    }
}
