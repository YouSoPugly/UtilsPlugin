package xyz.pugly.utils.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class GUIWindow {

    private static Map<String, GUIWindow> windows = new HashMap<>();

    private Inventory inv;
    private Map<Integer, GUIItem> items;
    private Consumer<InventoryCloseEvent> onClose;
    private Consumer<InventoryOpenEvent> onOpen;
    private final String name;

    // To use create a new GUIWindow with a name and rows
    // Then add .setOnClose and setOnOpen if needed, I suggest to do
    // window.callClosed((e) -> {window.unregister();});
    // if you don't plan on reusing a gui
    // as it helps with lag by un-caching an unused window
    //
    // To add items just .setItem(slot, GUIItem);
    // and finally to send it to the player do .show(Player);

    public GUIWindow(String name, int rows) {

        this.name = getValidName(name);
        this.inv = Bukkit.createInventory(null, rows*9, this.name);
        this.items = new HashMap<>(rows * 9);

        windows.put(this.name, this);
    }

    void callClosed(InventoryCloseEvent e) {
        if (onClose != null) onClose.accept(e);
    }

    void callOpen(InventoryOpenEvent e) {
        if (onOpen != null) onOpen.accept(e);
    }

    public void show(Player p) {
        Inventory inventory = Bukkit.createInventory(p, inv.getSize(), name);
        inventory.setContents(inv.getContents());
        p.openInventory(inv);
    }

    public void unregister() {
        windows.remove(this.name);
    }

    public String getValidName(String name) {
        return (windows.containsKey(name) ? getValidName(name + "\u00a7f") : name);
    }

    public void setOnClose(Consumer<InventoryCloseEvent> e) {
        this.onClose = e;
    }

    public void setOnOpen(Consumer<InventoryOpenEvent> e) {
        this.onOpen = e;
    }

    public void setItem(int slot, GUIItem item) {

        this.items.put(slot, item);
        this.inv.setItem(slot, item.getBukkitItem());

    }

    public void fill(GUIItem item) {

        ItemStack bukkitItem = item.getBukkitItem();

        for (int i = 0; i < inv.getSize(); i++) {
            items.put(i, item);
            inv.setItem(i, bukkitItem);
        }
    }

    public GUIItem getItem(int slot) {
        return items.get(slot);
    }

    public Inventory getInventory() {
        return this.inv;
    }

    public String getName() {
        return name;
    }

    public static GUIWindow getWindow(String name) {
        return windows.get(name);
    }
}