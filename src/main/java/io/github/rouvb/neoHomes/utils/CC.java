package io.github.rouvb.neoHomes.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@SuppressWarnings("deprecation")
public class CC {
    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> translate(List<String> list) {
        List<String> translated = new ArrayList<>();
        for (String s : list) {
            translated.add(translate(s));
        }
        return translated;
    }
}
