package ru.stonlex.bukkit.depend.messaging.packet;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;

public interface MessagingPacket {

    /**
     * Запись и инициализация пакета для отправки
     *
     * @param dataOutput -
     */
    void writePacket(ByteArrayDataOutput dataOutput);

    /**
     * Чтение пакета
     *
     * @param dataInput -
     */
    void readPacket(ByteArrayDataInput dataInput);


    /**
     * Обработка пакета при его чтении
     */
    void handle();

}
