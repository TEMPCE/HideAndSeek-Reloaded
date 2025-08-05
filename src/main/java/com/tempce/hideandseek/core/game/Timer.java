package com.tempce.hideandseek.core.game;

import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nonnull;

public class Timer extends BukkitRunnable {
    private final BossBar bossBar;
    private final Runnable onTimerEnd;
    private int tick;
    private final String format;
    private boolean canceled = false;

    /**
     *
     * @param endTime timer end time with seconds
     * @param bossBar bossbar for shows timer
     * @param onTimerEnd if timer end, runs this
     * @param format %time% is time in seconds, %formattedTime% is formatted time with style: MM:ss
     */
    public Timer(int endTime, @Nonnull BossBar bossBar, @Nonnull Runnable onTimerEnd, @Nonnull String format) {
        this.onTimerEnd = onTimerEnd;
        this.bossBar = bossBar;
        this.format = format;
        this.tick = endTime * 20;
    }

    @Override
    public void run() {
        int currentTimeInSeconds = tick / 20;
        int minutes = currentTimeInSeconds / 60;
        int seconds = currentTimeInSeconds % 60;
        String formattedTime = String.format("%02d:%02d", minutes, seconds);

        bossBar.setTitle(format
                .replaceAll("%time%", String.valueOf(currentTimeInSeconds))
                .replaceAll("%formattedTime%", formattedTime));

        if (tick <= 0) {
            onTimerEnd.run();
            cancel();
        }

        if (canceled) {
            cancel();
        }

        tick--;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
