package com.black_dog20.jetboots.api.events;

import com.black_dog20.bml.utils.translate.ITranslation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * A event used to add extra tooltips to items.
 * Is only fired client sided.
 */
public class ExtraTooltipEvent extends Event {

    public enum Type {
        NORMAL,
        NOT_SNEAKING,
        SNEAKING
    }

    private List<Component> extraTooltips = new ArrayList<>();
    private final ItemStack stack;
    private final Type type;

    public ExtraTooltipEvent(ItemStack stack, Type type) {
        this.stack = stack;
        this.type = type;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Type getType() {
        return type;
    }

    public void add(Component tooltip) {
        extraTooltips.add(tooltip);
    }

    public void add(ITranslation tooltip) {
        add(tooltip.get());
    }

    public List<Component> getExtraTooltips() {
        return extraTooltips;
    }
}
