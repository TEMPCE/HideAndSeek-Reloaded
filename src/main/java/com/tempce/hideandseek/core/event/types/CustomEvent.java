package com.tempce.hideandseek.core.event.types;

import com.tempce.hideandseek.core.event.custom.CustomGameEvent;
import org.bukkit.entity.Player;

import static com.tempce.hideandseek.Hideandseek.plugin;

public abstract class CustomEvent extends Event {
    public CustomEvent() {
        super("custom");
    }

    /**
     * Custom events execute code individually, not per player, so the 'player' argument is always null. and executes only once.
     */
    public abstract void apply(Player player);

    public static final class JavaClassEvent extends CustomEvent {
        private final String classPath;

        public JavaClassEvent(String classPath) {
            this.classPath = classPath;
        }

        @Override
        public void apply(Player player) {
            ClassLoader classLoader = plugin.getClass().getClassLoader();
            try {
                Class<?> clazz = classLoader.loadClass(classPath);

                if (CustomGameEvent.class.isAssignableFrom(clazz)) {
                    CustomGameEvent customEvent = (CustomGameEvent) clazz.getDeclaredConstructor().newInstance();
                    customEvent.onEvent();
                } else {
                    throw new IllegalStateException("Class " + classPath + " does not implement CustomGameEvent.");
                }
            } catch (Exception e) {
                throw new IllegalStateException("Failed to load or execute CustomGameEvent class: " + classPath, e);
            }
        }

        public String getClassPath() {
            return classPath;
        }
    }
}
