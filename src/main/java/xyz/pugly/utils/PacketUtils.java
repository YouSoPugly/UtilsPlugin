package xyz.pugly.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.UUID;

public class PacketUtils {

    // Each of these is quite straight forward in how to use,
    // so you shouldn't have much trouble


    private static final ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    public static void sendFakeBlock(Player p, Location l, Material m, int id) {

        PacketContainer fallingBlock = getFallingBlockEntity(id, l, m);
        PacketContainer metadata = getFallingBlockMetadata(id, l);

        try {
            manager.sendServerPacket(p, fallingBlock);
            manager.sendServerPacket(p, metadata);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + e);
        }
    }

    public static void sendHologram(Player player, Location location, String text, int id) {


        PacketContainer armorstand = getDefaultEntity(id, location, EntityType.ARMOR_STAND);
        PacketContainer metadata = getArmorstandMetadata(id, location, text);

        try {
            manager.sendServerPacket(player, armorstand);
            manager.sendServerPacket(player, metadata);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + e);
        }

    }

    public static void deleteEntity(Player p, int[] ids) {
        PacketContainer kill = getKillPacket(ids);

        try {
            manager.sendServerPacket(p, kill);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + kill, e);
        }

    }

    public static void sendFakeItem(Player p, Location l, ItemStack item, String name, int id) {

        PacketContainer itemPacket = getDefaultEntity(id, l, EntityType.DROPPED_ITEM);
        PacketContainer itemMeta = getItemMetadata(id, l, name, item);
        PacketContainer velocity = getVelocityPacket(id, new Vector(0, 0, 0));

        try {
            manager.sendServerPacket(p, itemPacket);
            manager.sendServerPacket(p, itemMeta);
            manager.sendServerPacket(p, velocity);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(
                    "Cannot send packet " + e);
        }
    }

    public static PacketContainer getItemMetadata(int id, Location l, String text, ItemStack item) {

        PacketContainer metadata = manager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        metadata.getModifier().writeDefaults();
        metadata.getIntegers().write(0, id); // set mob id to the one of the previously created mob

        Entity as = l.getWorld().spawnEntity(new Location(l.getWorld(), 0,-10,0), EntityType.DROPPED_ITEM);
        WrappedDataWatcher watcher = new WrappedDataWatcher(as);

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true); // set name visible

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(text)[0].getHandle());

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt); // set name

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(5, WrappedDataWatcher.Registry.get(Boolean.class)), true); // set no grav

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(7, WrappedDataWatcher.Registry.getItemStackSerializer(false)), item);

        metadata.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        return metadata;

    }

    public static PacketContainer getPosition(int id, Vector change) {

        PacketContainer position = manager.createPacket(PacketType.Play.Server.POSITION);

        position.getIntegers().write(0, id)
                .write(1, (int)(change.getX()*32*128))
                .write(2, (int)(change.getY()*32*128))
                .write(3, (int)(change.getZ()*32*128));

        position.getBooleans().write(0, false);

        return position;

    }

    public static PacketContainer getArmorstandMetadata(int id, Location l, String text) {

        PacketContainer metadata = manager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        metadata.getModifier().writeDefaults();
        metadata.getIntegers().write(0, id); // set mob id to the one of the previously created mob

        Entity as = l.getWorld().spawnEntity(new Location(l.getWorld(), 0,-10,0), EntityType.ARMOR_STAND);
        WrappedDataWatcher watcher = new WrappedDataWatcher(as);

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20); // set invisible
        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(3, WrappedDataWatcher.Registry.get(Boolean.class)), true); // set name visible

        Optional<?> opt = Optional
                .of(WrappedChatComponent
                        .fromChatMessage(text)[0].getHandle());

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true)), opt); // set name

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(5, WrappedDataWatcher.Registry.get(Boolean.class)), true); // set no grav

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(14, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x10); // set marker\

        metadata.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        return metadata;

    }

    public static PacketContainer getFallingBlockMetadata(int id, Location l) {

        PacketContainer metadata = manager.createPacket(PacketType.Play.Server.ENTITY_METADATA);

        metadata.getModifier().writeDefaults();
        metadata.getIntegers().write(0, id); // set mob id to the one of the previously created mob

        Entity as = l.getWorld().spawnEntity(new Location(l.getWorld(), 0,-10,0), EntityType.FALLING_BLOCK);
        WrappedDataWatcher watcher = new WrappedDataWatcher(as);

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, WrappedDataWatcher.Registry.get(Byte.class)), (byte) 0x20); // set invisible

        watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(5, WrappedDataWatcher.Registry.get(Boolean.class)), true); // set no grav

        metadata.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
        return metadata;

    }

    public static PacketContainer getFallingBlockEntity(int id, Location location, Material m) {
        PacketContainer as = manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        as.getIntegers()
                .write(0, id) // unique mob id to be used later
                .write(1, m.getId()); // extra data, should be 0 I think

        as.getEntityTypeModifier().write(0, EntityType.FALLING_BLOCK);

        as.getUUIDs().write(0, UUID.randomUUID()); // yay random uuid

        as.getDoubles()
                .write(0, location.getX()) //obviously location
                .write(1, location.getY())
                .write(2, location.getZ());

        return as;
    }

    public static PacketContainer getVelocityPacket(int id, Vector velocity) {
        PacketContainer vel = manager.createPacket(PacketType.Play.Server.ENTITY_VELOCITY);

        vel.getIntegers().write(0, id);

        vel.getIntegers().write(1, velocity.getBlockX())
                .write(2, velocity.getBlockY())
                .write(3, velocity.getBlockZ());

        return vel;
    }

    public static PacketContainer getDefaultEntity(int id, Location location, EntityType et) {

        PacketContainer as = manager.createPacket(PacketType.Play.Server.SPAWN_ENTITY);

        as.getIntegers()
                .write(0, id) // unique mob id to be used later
                .write(1, 0); // extra data, should be 0 I think

        as.getEntityTypeModifier().write(0, et);

        as.getUUIDs().write(0, UUID.randomUUID()); // yay random uuid

        as.getDoubles()
                .write(0, location.getX()) //obviously location
                .write(1, location.getY())
                .write(2, location.getZ());

        return as;
    }

    public static PacketContainer getKillPacket(int[] ids) {
        PacketContainer delete = manager.createPacket(PacketType.Play.Server.ENTITY_DESTROY);

        //delete.getIntegers().write(0,ids.length); // for whatever reason this isn't needed?? but wiki.vg says it is so rip ig

        delete.getIntegerArrays().write(0, ids);

        return delete;
    }

    public static void sendBorder(Player p, Location center, int size) {

        PacketContainer worldBorder = manager.createPacket(PacketType.Play.Server.WORLD_BORDER);

        worldBorder.getWorldBorderActions().write(0, EnumWrappers.WorldBorderAction.INITIALIZE);

        worldBorder.getDoubles().
                write(0, center.getX()).
                write(1, center.getZ()).
                write(2, (double) size).
                write(3, (double) size);

        worldBorder.getLongs().
                write(0, 0L);

        worldBorder.getIntegers().
                write(0, 29999984).
                write(1, 0).
                write(2, 0);

        try {
            manager.sendServerPacket(p, worldBorder);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    
}
