package net.aufdemrand.denizen.nms.impl.entities;

import net.aufdemrand.denizen.nms.NMSHandler;
import net.aufdemrand.denizen.nms.interfaces.FakePlayer;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CraftFakePlayer_v1_8_R3 extends CraftPlayer implements FakePlayer {

    private final CraftServer server;
    public String fullName;

    public CraftFakePlayer_v1_8_R3(CraftServer server, EntityFakePlayer_v1_8_R3 entity) {
        super(server, entity);
        this.server = server;
        setMetadata("NPC", new FixedMetadataValue(NMSHandler.getJavaPlugin(), true));
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        this.server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return this.server.getEntityMetadata().getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return this.server.getEntityMetadata().hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        this.server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
    }

    @Override
    public String getEntityTypeName() {
        return "FAKE_PLAYER";
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public int _INVALID_getLastDamage() {
        return 0;
    }

    @Override
    public void _INVALID_setLastDamage(int i) {
    }

    @Override
    public void _INVALID_damage(int i) {
    }

    @Override
    public void _INVALID_damage(int i, Entity entity) {
    }

    @Override
    public int _INVALID_getHealth() {
        return 0;
    }

    @Override
    public void _INVALID_setHealth(int i) {
    }

    @Override
    public int _INVALID_getMaxHealth() {
        return 0;
    }

    @Override
    public void _INVALID_setMaxHealth(int i) {
    }
}
