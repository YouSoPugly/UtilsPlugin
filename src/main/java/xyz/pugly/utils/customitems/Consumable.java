package xyz.pugly.utils.customitems;

import org.bukkit.entity.Player;

public abstract class Consumable extends CustomItem {

    protected Consumable(String name, String lore) {
        super(name);
        setLore(lore);
    }

    public abstract void onConsume(Player player);
}
