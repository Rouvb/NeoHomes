package io.github.rouvb.neoHomes.gui;

import io.github.rouvb.neoHomes.config.Config;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.home.Home;
import io.github.rouvb.neoHomes.home.HomeService;
import io.github.rouvb.neoHomes.profile.Profile;
import io.github.rouvb.neoHomes.profile.manager.ProfileManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.xenondevs.invui.gui.Gui;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.gui.structure.Markers;
import xyz.xenondevs.invui.item.Item;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.SimpleItem;
import xyz.xenondevs.invui.window.Window;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class HomesGUI {

    private final ConfigManager configManager;
    private final ProfileManager profileManager;
    private final HomeService homeService;

    public HomesGUI(ConfigManager configManager, ProfileManager profileManager, HomeService homeService) {
        this.configManager = configManager;
        this.profileManager = profileManager;
        this.homeService = homeService;
    }

    public void open(Player viewer) {
        Item border = new SimpleItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName(""));

        Profile profile = profileManager.getProfile(viewer.getUniqueId());
        List<Item> homeItems = getHomeItems(profile);

        Gui gui = PagedGui.items()
                .setStructure(configManager.getConfig().getString(Config.GUI_LAYOUT.getPath()))
                .addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('#', border)
                .addIngredient('<', new BackItem(configManager))
                .addIngredient('>', new ForwardItem(configManager))
                .setContent(homeItems)
                .build();

        Window window = Window.single()
                .setViewer(viewer)
                .setTitle(configManager.getConfig().getString(Config.GUI_TITLE.getPath()))
                .setGui(gui)
                .build();

        window.open();
    }

    private List<Item> getHomeItems(Profile profile) {
        Collection<Home> homes = profile.getHomes();
        return homes.stream()
                .map(home -> new HomeItem(configManager, home, homeService, profile))
                .collect(Collectors.toList());
    }
}
