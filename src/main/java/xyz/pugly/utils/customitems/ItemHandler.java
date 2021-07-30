package xyz.pugly.utils.customitems;

import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ItemHandler implements Listener {

    private static final Collection<Consumable> consumables = new ArrayList<Consumable>();

    public static void registerConsumables(Consumable... toRegister) {
        Collections.addAll(consumables, toRegister);
    }


    public static void onClick(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
            return;

        if (e.getItem() == null)
            return;

        for (Consumable consumable : consumables) {
            if (consumable.getMaterial() == e.getItem().getType()) {
                consumable.onConsume(e.getPlayer());
                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                return;
            }
        }
    }


}
