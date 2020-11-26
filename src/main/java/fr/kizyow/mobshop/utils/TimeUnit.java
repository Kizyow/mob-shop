package fr.kizyow.mobshop.utils;

import java.util.HashMap;

public enum TimeUnit {

    MINUTE("minutes", "min", 60),
    HOUR("heures", "h", 60 * 60),
    DAY("jours", "j", 60 * 60 * 24);

    private final String name;
    private final String shortcut;
    private final long toSecond;

    private static final HashMap<String, TimeUnit> ID_SHORTCUT = new HashMap<>();

    TimeUnit(String name, String shortcut, long toSecond) {
        this.name = name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;

    }

    static {

        for (TimeUnit units : values()) {

            ID_SHORTCUT.put(units.shortcut, units);

        }

    }

    public static TimeUnit getFromShortcut(String shortcut) {
        return ID_SHORTCUT.get(shortcut);

    }

    public String getName() {
        return name;

    }

    public String getShortcut() {
        return shortcut;

    }

    public long getToSecond() {
        return toSecond;

    }

    public static boolean existFromShortcut(String shortcut) {
        return ID_SHORTCUT.containsKey(shortcut);

    }

}