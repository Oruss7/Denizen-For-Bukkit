package net.aufdemrand.denizen.objects.properties.entity;

import net.aufdemrand.denizen.objects.dEntity;
import net.aufdemrand.denizencore.objects.Element;
import net.aufdemrand.denizencore.objects.Mechanism;
import net.aufdemrand.denizencore.objects.dObject;
import net.aufdemrand.denizencore.objects.properties.Property;
import net.aufdemrand.denizencore.tags.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public class EntityGravity implements Property {

    public static boolean describes(dObject entity) {
        return entity instanceof dEntity && ((dEntity) entity).getBukkitEntityType() == EntityType.ARMOR_STAND;
    }

    public static EntityGravity getFrom(dObject entity) {
        if (!describes(entity)) {
            return null;
        }

        else {
            return new EntityGravity((dEntity) entity);
        }
    }

    ///////////////////
    // Instance Fields and Methods
    /////////////

    private EntityGravity(dEntity entity) {
        dentity = entity;
    }

    dEntity dentity;

    /////////
    // Property Methods
    ///////

    @Override
    public String getPropertyString() {
        if (((ArmorStand) dentity.getBukkitEntity()).hasGravity()) {
            return null;
        }
        else {
            return "false";
        }
    }

    @Override
    public String getPropertyId() {
        return "gravity";
    }

    ///////////
    // dObject Attributes
    ////////

    @Override
    public String getAttribute(Attribute attribute) {

        if (attribute == null) {
            return null;
        }

        // <--[tag]
        // @attribute <e@entity.gravity>
        // @returns Element(Boolean)
        // @mechanism dEntity.gravity
        // @group properties
        // @description
        // If the entity is an armor stand, returns whether the armor stand has gravity.
        // -->
        if (attribute.startsWith("gravity")) {
            return new Element(((ArmorStand) dentity.getBukkitEntity()).hasGravity())
                    .getAttribute(attribute.fulfill(1));
        }

        return null;
    }

    @Override
    public void adjust(Mechanism mechanism) {

        // <--[mechanism]
        // @object dEntity
        // @name gravity
        // @input Element(Boolean)
        // @description
        // Changes the gravity state of an armor stand.
        // @tags
        // <e@entity.gravity>
        // -->

        if (mechanism.matches("gravity") && mechanism.requireBoolean()) {
            ((ArmorStand) dentity.getBukkitEntity()).setGravity(mechanism.getValue().asBoolean());
        }
    }
}
