package io.github.rouvb.neoHomes.gui;

import io.github.milkdrinkers.colorparser.paper.ColorParser;
import io.github.rouvb.neoHomes.config.Config;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.config.Lang;
import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.home.HomeService;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.AbstractItem;

public class HomeItem extends AbstractItem {

    private final ConfigManager configManager;
    private final Home home;
    private final HomeService homeService;
    private final Profile profile;

    public HomeItem(ConfigManager configManager, Home home, HomeService homeService, Profile profile) {
        this.configManager = configManager;
        this.home = home;
        this.homeService = homeService;
        this.profile = profile;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.closeInventory();
        if (clickType.isLeftClick()) {
            homeService.teleportToHome(player, home);
        } else if (clickType.isRightClick()) {
            player.sendMessage(ColorParser.of(configManager.getLang().getString(Lang.HOME_DELETED.getPath()))
                    .with("home", home.getHomeName()).legacy().build());
            profile.deleteHome(home.getHomeName());
        }
    }

    @Override
    public ItemProvider getItemProvider() {
        return new ItemBuilder(Material.getMaterial(configManager.getConfig().getString(Config.GUI_ITEMS_HOME_MATERIAL.getPath())))
                .setDisplayName(CC.translate(configManager.getConfig().getString(Config.GUI_ITEMS_HOME_NAME.getPath())
                        .replace("<home>", home.getHomeName())))
                .addLoreLines(CC.translate(configManager.getConfig().getString(Config.GUI_ITEMS_HOME_LORE.getPath())));
    }
}
