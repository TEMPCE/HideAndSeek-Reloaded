package akki697222.hideandseek.core;

import akki697222.hideandseek.items.*;

public enum Items {
    AreaRadar(new AreaRadar()),
    Redbull(new Redbull()),
    KnockbackStick(new KnockbackStick()),
    JumpBoost(new JumpBoost()),
    BatteryPack(new BatteryPack())
    ;

    final IGameItem item;

    Items(IGameItem item) {
        this.item = item;
    }

    public IGameItem getItem() {
        return item;
    }
}
