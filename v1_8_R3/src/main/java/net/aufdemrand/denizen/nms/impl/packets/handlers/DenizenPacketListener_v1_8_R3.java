package net.aufdemrand.denizen.nms.impl.packets.handlers;

import net.aufdemrand.denizen.nms.NMSHandler;
import net.aufdemrand.denizen.nms.impl.packets.PacketInResourcePackStatus_v1_8_R3;
import net.aufdemrand.denizen.nms.impl.packets.PacketInSteerVehicle_v1_8_R3;
import net.aufdemrand.denizen.nms.interfaces.packets.PacketHandler;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInResourcePackStatus;
import net.minecraft.server.v1_8_R3.PacketPlayInSteerVehicle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DenizenPacketListener_v1_8_R3 extends AbstractListenerPlayIn_v1_8_R3 {

    private static PacketHandler packetHandler;

    public DenizenPacketListener_v1_8_R3(NetworkManager networkManager, EntityPlayer entityPlayer) {
        super(networkManager, entityPlayer, entityPlayer.playerConnection);
    }

    public static void enable(PacketHandler handler) {
        packetHandler = handler;
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerEventListener(), NMSHandler.getJavaPlugin());
    }

    @Override
    public void a(final PacketPlayInSteerVehicle packet) {
        if (!packetHandler.receivePacket(player.getBukkitEntity(), new PacketInSteerVehicle_v1_8_R3(packet))) {
            super.a(packet);
        }
    }

    @Override
    public void a(PacketPlayInResourcePackStatus packet) {
        packetHandler.receivePacket(player.getBukkitEntity(), new PacketInResourcePackStatus_v1_8_R3(packet));
        super.a(packet);
    }

    // For compatibility with other plugins using Reflection weirdly...
    @Override
    public void sendPacket(Packet packet) {
        super.sendPacket(packet);
    }

    public static class PlayerEventListener implements Listener {
        @EventHandler(priority = EventPriority.LOWEST)
        public void onPlayerJoin(PlayerJoinEvent event) {
            DenizenNetworkManager_v1_8_R3.setNetworkManager(event.getPlayer(), packetHandler);
        }
    }
}
