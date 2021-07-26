package xyz.pugly.utils.GUI;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
       GUIWindow window = GUIWindow.getWindow(e.getView().getTitle());
        if(window != null) {
            GUIItem item = window.getItem(e.getRawSlot());
            if(item != null) {
                item.invClick(e);
            }
            if (e.getRawSlot() < window.getInventory().getSize()
                    || e.getClick().isShiftClick()
                    || (e.getClick().isCreativeAction() && e.getRawSlot() < window.getInventory().getSize())
                    || (e.getCursor() != null && e.getRawSlot()  < window.getInventory().getSize())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {
        GUIWindow window = GUIWindow.getWindow(e.getView().getTitle());
        if(window != null) {
            window.callOpen(e);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        GUIWindow window = GUIWindow.getWindow(e.getView().getTitle());
        if(window != null) {
            window.callClosed(e);
        }
    }

}