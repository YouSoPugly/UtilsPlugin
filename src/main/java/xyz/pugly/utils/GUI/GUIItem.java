package xyz.pugly.utils.GUI;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.Consumer;

public class GUIItem {

    private Consumer<InventoryClickEvent> invClick;
    private ItemStack item;

    // When creating enter an item stack to be placed in the gui slot, and a consumer to run when clicked.
    // It uses raw slot btw, so no worries about it being the same slot as in the player inventory.
    // Example consumer, e being the InventoryClickEvent:
    // (e) -> {
    // e.setCancelled(true);
    // e.getView().close()
    // }

    public GUIItem(ItemStack item, Consumer<InventoryClickEvent> toRun) {
        this.invClick = toRun;
        this.item = item;
    }

    ItemStack getBukkitItem() {
        return this.item;
    }

    void invClick(InventoryClickEvent e) {
        this.invClick.accept(e);
    }

    public static GUIItem getBorder(Material m) {
        ItemStack is = new ItemStack(m, 1);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("\u00a77");
        is.setItemMeta(im);
        return new GUIItem(is, (e) -> {
            e.setCancelled(true);
        });
    }
}
