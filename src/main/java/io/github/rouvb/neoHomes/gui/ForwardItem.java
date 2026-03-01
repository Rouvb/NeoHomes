package io.github.rouvb.neoHomes.gui;

import io.github.rouvb.neoHomes.config.Config;
import io.github.rouvb.neoHomes.config.ConfigManager;
import io.github.rouvb.neoHomes.utils.CC;
import org.bukkit.Material;
import xyz.xenondevs.invui.gui.PagedGui;
import xyz.xenondevs.invui.item.ItemProvider;
import xyz.xenondevs.invui.item.builder.ItemBuilder;
import xyz.xenondevs.invui.item.impl.controlitem.PageItem;

public class ForwardItem extends PageItem {

    private final ConfigManager configManager;

    public ForwardItem(ConfigManager configManager) {
        super(true);
        this.configManager = configManager;
    }

    @Override
    public ItemProvider getItemProvider(PagedGui<?> pagedGui) {
        ItemBuilder builder = new ItemBuilder(Material.getMaterial(configManager.getConfig().getString(Config.GUI_ITEMS_NEXT_PAGE_MATERIAL.getPath())));
        builder.setDisplayName(CC.translate(configManager.getConfig().getString(Config.GUI_ITEMS_NEXT_PAGE_NAME.getPath())));
        return builder;
    }
}
