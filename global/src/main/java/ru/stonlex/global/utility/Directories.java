package ru.stonlex.global.utility;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("all")
@UtilityClass
public class Directories {

    public void clearDirectory(@NonNull File file, boolean delete) {
        if (!file.exists()) {
            return;
        }

        if (file.isDirectory()) {
            for (File directoryFile : file.listFiles()) {

                if (directoryFile.isDirectory()) {
                    clearDirectory(directoryFile, true);

                    continue;
                }

                directoryFile.delete();
            }
        }

        if (delete) {
            file.delete();
        }
    }

    @SneakyThrows
    public void copyDirectory(@NonNull Path source, @NonNull Path target) {
        if (Files.isDirectory(source)) {

            if (!Files.exists(target)) {
                Files.createDirectory(target);
            }

            for (File directoryFile : source.toFile().listFiles()) {
                copyDirectory(directoryFile.toPath(), target.resolve(directoryFile.getName()));
            }

        } else {

            if (!Files.exists(target)) {
                Files.copy(source, target);
            }
        }
    }

}
