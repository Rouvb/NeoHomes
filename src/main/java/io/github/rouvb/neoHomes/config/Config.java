package io.github.rouvb.neoHomes.config;

import lombok.Getter;

@Getter
public enum Config {
    BLACKLISTED_WORDS("blacklisted.words"),
    BLACKLISTED_WORLDS("blacklisted.worlds"),
    HOME_COUNTDOWN("home.countdown"),
    HOME_CANCEL_ON_DAMAGE("home.cancel.on_damage"),
    HOME_CANCEL_ON_MOVE("home.cancel_on_move"),
    GUI_TITLE("gui.title"),
    GUI_LAYOUT("gui.layout"),
    GUI_ITEMS_BACKGROUND_NAME("gui.items.background.name"),
    GUI_ITEMS_BACKGROUND_MATERIAL("gui.items.background.material"),
    GUI_ITEMS_PREVIOUS_PAGE_NAME("gui.items.previous_page.name"),
    GUI_ITEMS_PREVIOUS_PAGE_MATERIAL("gui.items.previous_page.material"),
    GUI_ITEMS_NEXT_PAGE_NAME("gui.items.next_page.name"),
    GUI_ITEMS_NEXT_PAGE_MATERIAL("gui.items.next_page.material"),
    GUI_ITEMS_HOME_NAME("gui.items.home.name"),
    GUI_ITEMS_HOME_LORE("gui.items.home.lore"),
    GUI_ITEMS_HOME_MATERIAL("gui.items.home.material");

    private final String path;

    Config(String path) {
        this.path = path;
    }
}
