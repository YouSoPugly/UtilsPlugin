package xyz.pugly.utils.customitems.examples;

import org.bukkit.entity.Player;
import xyz.pugly.utils.customitems.Consumable;

public class HealVoucher extends Consumable {

    public HealVoucher(String name, String lore) {
        super(name, lore);
    }

    @Override
    public void onConsume(Player player) {
        player.setHealth(player.getMaxHealth());
    }
}

