package net.aufdemrand.denizen.scripts.commands.npc;

import net.aufdemrand.denizen.exceptions.CommandExecutionException;
import net.aufdemrand.denizen.exceptions.InvalidArgumentsException;
import net.aufdemrand.denizen.objects.*;
import net.aufdemrand.denizen.scripts.ScriptEntry;
import net.aufdemrand.denizen.scripts.commands.AbstractCommand;
import net.aufdemrand.denizen.utilities.debugging.dB;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.DespawnReason;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.Trait;

import org.bukkit.Location;

/**
 * Creates a NPC.
 *
 *
 */

public class CreateCommand extends AbstractCommand {

    @Override
    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {

        for (aH.Argument arg : aH.interpret(scriptEntry.getArguments())) {

            if (!scriptEntry.hasObject("entity_type")
                    && arg.matchesArgumentType(dEntity.class)) {
                dEntity ent = dEntity.valueOf(arg.getValue());
                if (!ent.isGeneric())
                    throw new InvalidArgumentsException("Entity supplied must be generic!");
                scriptEntry.addObject("entity_type", ent);
            }

            else if (!scriptEntry.hasObject("spawn_location")
                    && arg.matchesArgumentType(dLocation.class))
                scriptEntry.addObject("spawn_location", arg.asType(dLocation.class));

            else if (!scriptEntry.hasObject("name"))
                scriptEntry.addObject("name", arg.asElement());

            else if (!scriptEntry.hasObject("traits")
                    && arg.matchesPrefix("t, trait, traits")
                    && arg.matchesArgumentType(dList.class))
                scriptEntry.addObject("traits", arg.asType(dList.class));

            else arg.reportUnhandled();
        }

    }

    @Override
    public void execute(final ScriptEntry scriptEntry) throws CommandExecutionException {

        Element name = (Element) scriptEntry.getObject("name");
        dEntity type = (dEntity) scriptEntry.getObject("entity_type");
        dLocation loc = (dLocation) scriptEntry.getObject("spawn_location");
        dList traits = (dList) scriptEntry.getObject("traits");

        dB.report(scriptEntry, getName(), name.debug() + type.debug() + (loc != null ? loc.debug() : "")
                + (traits != null ? traits.debug() : ""));

        // Add the created NPC into the script entry so it can be utilized if need be.
        scriptEntry.addObject("created_npc", dNPC.mirrorCitizensNPC(CitizensAPI.getNPCRegistry()
                .createNPC(type.getEntityType(), name.asString())));

        if (loc != null)
            ((dNPC) scriptEntry.getObject("created_npc")).getCitizen().spawn(loc);
        if (traits != null) {
            for (String trait_name : traits) {
                Trait trait = CitizensAPI.getTraitFactory().getTrait(trait_name);
                if (trait != null)
                    ((dNPC) scriptEntry.getObject("created_npc")).getCitizen().addTrait(trait);
                else
                    dB.echoError("Could not add trait to NPC: " + trait_name);
            }
        }
    }

}
