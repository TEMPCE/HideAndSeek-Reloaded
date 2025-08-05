package com.tempce.hideandseek.core.item;

import com.tempce.hideandseek.core.item.items.*;

public enum Items {
    AreaRadar(new AreaRadar()),
    Redbull(new Redbull()),
    KnockbackStick(new KnockbackStick()),
    JumpBoost(new JumpBoost()),
    BatteryPack(new BatteryPack())
    ;

    final AbstractGameItem item;

    Items(AbstractGameItem item) {
        this.item = item;
    }

    public AbstractGameItem getItem() {
        return item;
    }
}
