package ru.stonlex.bukkit.utility;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import ru.stonlex.global.utility.ReflectionUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@UtilityClass
public class ItemUtil {

    /**
     * Опять же, этот код старый, и переписывать его мне было
     * попросту лень, да и тем более, он прекрасно работает.
     *
     * Если кому-то он неудобен, то система как бы не особо сложная,
     * поэтому можно и самому ее написать
     */

    public final ItemStack EMPTY_ITEM = new ItemStack(Material.AIR);

    public ItemStack getItemStack(Material material, byte durability, int amount, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material, amount, durability);
        ItemMeta meta = itemStack.getItemMeta();

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        meta.setLore(Arrays.asList(lore));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack getItemStack(Material material, String name, String... lore) {
        return getItemStack(material, (byte)0, 1, name, lore);
    }

    public ItemStack getItemStack(Material material, byte durability, String name, String... lore) {
        return getItemStack(material, durability, 1, name, lore);
    }

    public ItemStack getItemStack(Material material, byte durability) {
        return getItemStack(material, durability, 1, "");
    }

    public ItemStack getItemStack(Material material, int amount) {
        return getItemStack(material, (byte) 0, amount, "");
    }

    public ItemStack getItemStack(Material material) {
        return getItemStack(material, (byte) 0, 1, "");
    }

    public ItemStack getItemStack(Material material, String title) {
        return getItemStack(material, (byte) 0, 1, title);
    }

    public ItemStack getItemStack(Material material, byte durability, String name) {
        return getItemStack(material, durability, 1, name);
    }

    public ItemStack getItemStack(Material material, int amount, String name) {
        return getItemStack(material, (byte) 0, amount, name);
    }

    public ItemStack getTextureSkull(String texture, int amount, String name, String... lores) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lores));
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        try {
            GameProfile profile = new GameProfile(UUID.randomUUID(), "ItzStonlex");
            ReflectionUtil.setField(meta, "profile", ItemUtil.setGameProfileTexture(profile, texture));
        } catch (Exception ignored) {}
        
        item.setItemMeta(meta);
        return item;
    }
    
    public ItemStack getTextureSkull(String texture, String name, String... lores) {
        return getTextureSkull(texture, 1, name, lores);
    }

    public ItemStack getTextureSkull(String texture) {
        return getTextureSkull(texture, 1, "");
    }

    public ItemStack getPlayerSkull(String nickname, int amount, String name, String... lores) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, amount, (byte) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lores));

        if (nickname == null) {
            nickname = "ItzStonlex";
        }
        meta.setOwner(nickname);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta((ItemMeta) meta);
        return item;
    }

    public ItemStack getPlayerSkull(String nickname, String name, String... lores) {
        return getPlayerSkull(nickname, 1, name, lores);
    }

    public ItemStack getPlayerSkull(String nickname) {
        return getPlayerSkull(nickname, 1, "");
    }

    private GameProfile setGameProfileTexture(GameProfile gameProfile, String texture) {
        gameProfile.getProperties().put("textures", new Property("textures", texture));
        return gameProfile;
    }

    public ItemBuilder newBuilder() {
        return newBuilder(EMPTY_ITEM);
    }

    public ItemBuilder newBuilder(Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    public ItemBuilder newBuilder(ItemStack itemStack) {
        return new ItemBuilder(itemStack.clone());
    }


    @RequiredArgsConstructor
    public class ItemBuilder {

        private final ItemStack itemStack;

        public ItemBuilder setDurability(int durability) {
            this.itemStack.setDurability((byte) durability);

            return this;
        }

        public ItemBuilder setUnbreakable(boolean flag) {
            ItemMeta meta = itemStack.getItemMeta();

            meta.spigot().setUnbreakable(flag);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

            this.itemStack.setItemMeta(meta);

            return this;
        }

        public ItemBuilder setMaterial(Material material) {
            this.itemStack.setType(material);

            return this;
        }

        public ItemBuilder setAmount(int amount) {
            this.itemStack.setAmount(amount);

            return this;
        }

        public ItemBuilder setName(String name) {
            if (name == null) {
                return this;
            }

            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(name);

            this.itemStack.setItemMeta(meta);

            return this;
        }

        public ItemBuilder setLore(String... lore) {
            if (lore == null) {
                return this;
            }

            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(Arrays.asList(lore));

            this.itemStack.setItemMeta(meta);

            return this;
        }

        public ItemBuilder setLore(List<String> lore) {
            if (lore == null) {
                return this;
            }

            ItemMeta meta = itemStack.getItemMeta();
            meta.setLore(lore);

            this.itemStack.setItemMeta(meta);

            return this;
        }

        public ItemBuilder addLore(String lore) {
            ItemMeta meta = itemStack.getItemMeta();

            List<String> lores = meta.getLore();

            if (lores == null) {
                lores = new ArrayList<>();
            }

            lores.add(lore);

            return this.setLore(lores);
        }

        public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
            ItemMeta meta = itemStack.getItemMeta();

            if (enchantment == null) {
                return this;
            }

            meta.addEnchant(enchantment, level, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

            this.itemStack.setItemMeta(meta);

            return this;
        }

        public ItemBuilder addCustomPotionEffect(PotionEffect potionEffect, boolean isAdd) {
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

            potionMeta.addCustomEffect(potionEffect, isAdd);

            itemStack.setItemMeta(potionMeta);

            return this;
        }

        public ItemBuilder setMainPotionEffect(PotionEffectType potionEffectType) {
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

            potionMeta.setMainEffect(potionEffectType);

            itemStack.setItemMeta(potionMeta);

            return this;
        }

        public ItemBuilder setPotionColor(Color color) {
            PotionMeta potionMeta = (PotionMeta) itemStack.getItemMeta();

            potionMeta.setColor(color);

            itemStack.setItemMeta(potionMeta);

            return this;
        }

        public ItemBuilder setPlayerSkull(String playerSkull) {
            if (playerSkull == null) {
                return this;
            }

            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            skullMeta.setOwner(playerSkull);

            this.itemStack.setItemMeta(skullMeta);

            return this;
        }

        public ItemBuilder setTextureValue(String texture) {
            if (texture == null) {
                return this;
            }

            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            try {
                GameProfile profile = new GameProfile(UUID.randomUUID(), "ItzStonlex");
                ReflectionUtil.setField(skullMeta, "profile", ItemUtil.setGameProfileTexture(profile, texture));
            } catch (Exception ignored) {}

            this.itemStack.setItemMeta(skullMeta);

            return this;
        }

        public ItemBuilder setLeatherColor(Color color) {
            LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemStack.getItemMeta();

            armorMeta.setColor(color);

            this.itemStack.setItemMeta(armorMeta);

            return this;
        }

        public ItemStack build() {
            return itemStack;
        }
    }

}
