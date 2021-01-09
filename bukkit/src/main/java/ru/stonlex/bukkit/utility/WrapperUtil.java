package ru.stonlex.bukkit.utility;

import com.comphenix.protocol.utility.MinecraftProtocolVersion;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.util.UUID;

@UtilityClass
public class WrapperUtil {

    public static final int CURRENT_VERSION_ID = MinecraftProtocolVersion.getCurrentVersion();
    public final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

    /**
     * Получение класса из net.minecraft.server
     */
    public Class<?> getNmsClass(String className) {
        try {

            return Class.forName("net.minecraft.server." + VERSION + "." + className);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            return null;
        }
    }

    /**
     * Поучение класса из org.bukkit.craftbukkit
     */
    public Class<?> getCraftbukkitClass(String packageName, String className) {
        try {

            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + (packageName != null && !packageName.isEmpty() ? packageName + "." : "") + className);

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            return null;
        }
    }

}
