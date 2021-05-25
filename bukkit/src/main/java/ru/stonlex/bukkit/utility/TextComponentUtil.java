package ru.stonlex.bukkit.utility;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class TextComponentUtil {

    /**
     * Опять же, этот код старый, и переписывать его мне было
     * попросту лень, да и тем более, он прекрасно работает.
     *
     * Если кому-то он неудобен, то система как бы не особо сложная,
     * поэтому можно и самому ее написать
     */

    /**
     * Создание сообщения
     */
    public BaseComponent[] createMessage(String message, String click, String hover,
                                                ClickEvent.Action clickAction) {
        TextComponent textComponent = new TextComponent(message);

        textComponent.setClickEvent(new ClickEvent(clickAction, click));
        textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new BaseComponent[]{new TextComponent(hover).duplicate()}));

        return new BaseComponent[]{textComponent.duplicate()};
    }

    /**
     * Создание сообщения
     */
    public BaseComponent[] createMessage(String message) {
        TextComponent textComponent = new TextComponent(message);

        return new BaseComponent[]{textComponent.duplicate()};
    }

    /**
     * Построение нескольких сообщений в один массив
     */
    public BaseComponent[] buildMessages(BaseComponent[]... baseComponents) {
        List<BaseComponent> outputList = new ArrayList<>();

        for (BaseComponent[] baseComponentArray : baseComponents) {
            outputList.addAll(Arrays.asList(baseComponentArray));
        }
        return outputList.toArray(new BaseComponent[0]);
    }



    /**
     * Получение билдера сообщений
     */
    public MessageBuilder newBuilder() {
        return newBuilder("");
    }

    /**
     * Получение билдера сообщений
     */
    public MessageBuilder newBuilder(String text) {
        return new MessageBuilder(text);
    }



    public class MessageBuilder {

        private final TextComponent component;

        public MessageBuilder(String message) {
            this.component = new TextComponent(message);
        }

        public MessageBuilder setText(String text) {
            this.component.setText(text);

            return this;
        }

        public MessageBuilder setHoverEvent(HoverEvent.Action action, String text) {
            final HoverEvent hoverEvent = new HoverEvent(action, new BaseComponent[]{new TextComponent(text)});

            this.component.setHoverEvent(hoverEvent);

            return this;
        }

        public MessageBuilder setClickEvent(ClickEvent.Action action, String text) {
            ClickEvent clickEvent = new ClickEvent(action, text);

            this.component.setClickEvent(clickEvent);

            return this;
        }

        public MessageBuilder setBold(boolean flag) {
            this.component.setBold(flag);

            return this;
        }

        public MessageBuilder setUnderlined(boolean flag) {
            this.component.setUnderlined(flag);

            return this;
        }

        public MessageBuilder setColor(ChatColor color) {
            this.component.setColor(color);

            return this;
        }

        public BaseComponent[] build() {
            return new BaseComponent[]{component};
        }
    }

}
