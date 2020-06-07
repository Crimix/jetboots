package com.black_dog20.jetboots.common.proxy;

import net.minecraft.entity.player.PlayerEntity;

public class ServerProxy implements IProxy {

    @Override
    public PlayerEntity getClientPlayer() {
        //No op;
        return null;
    }
}
