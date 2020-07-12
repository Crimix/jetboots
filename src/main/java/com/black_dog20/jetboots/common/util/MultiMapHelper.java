package com.black_dog20.jetboots.common.util;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MultiMapHelper {

    public static void removeValues(Multimap<String, AttributeModifier> multimap, String key, UUID uuid) {
        List<AttributeModifier> list = multimap.get(key).stream()
                .filter(a -> a.getID().equals(uuid))
                .collect(Collectors.toList());
        list.forEach(a -> multimap.remove(key, a));
    }
}
