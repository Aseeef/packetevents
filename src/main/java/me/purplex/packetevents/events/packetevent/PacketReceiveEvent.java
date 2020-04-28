package me.purplex.packetevents.events.packetevent;


import me.purplex.packetevents.PacketEvents;
import me.purplex.packetevents.enums.ServerVersion;
import me.purplex.packetevents.events.PacketEvent;
import org.bukkit.entity.Player;

public class PacketReceiveEvent extends PacketEvent {
    private final Player player;
    private final String name;
    private final Object packet;
    private final long timestamp;
    private boolean cancelled;
    public final ServerVersion version;

    public PacketReceiveEvent(Player player, String packetName, Object packet) {
        this.version = PacketEvents.getServerVersion();
        this.player = player;
        this.name = packetName;
        this.packet = packet;
        this.timestamp = (System.nanoTime() / 1000000);
        this.cancelled = false;
    }

    /**
     * Get the player sending the packet
     *
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the packet's name (NMS packet class simple name)
     *
     * @return
     */
    public String getPacketName() {
        return this.name;
    }

    /**
     * Get the raw packet object
     *
     * @return packet
     */
    public Object getPacket() {
        return this.packet;
    }

    /**
     * The time the packet was received(ms)
     *
     * @return
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * Cancel the packet
     *
     * @param cancelled
     */
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Returns if the packet has been cancelled
     *
     * @return cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }
}
