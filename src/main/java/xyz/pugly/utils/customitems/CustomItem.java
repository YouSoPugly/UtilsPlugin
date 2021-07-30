package xyz.pugly.utils.customitems;

import org.bukkit.Material;

public abstract class CustomItem {

    private String lore;
    private String name;
    private Material material;

    protected CustomItem(String name) {
        this.name = name;
    }

    protected CustomItem(Material material, String name) {
        this.material = material;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getLore() {
        return lore;
    }

    public Material getMaterial() {
        return material;
    }

    public CustomItem setName(String name) {
        this.name = name;
        return this;
    }

    public CustomItem setLore(String lore) {
        this.lore = lore;
        return this;
    }

    public CustomItem setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
