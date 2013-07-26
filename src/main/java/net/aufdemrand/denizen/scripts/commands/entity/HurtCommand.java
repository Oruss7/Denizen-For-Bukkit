package net.aufdemrand.denizen.scripts.commands.entity;

import java.util.List;

import net.aufdemrand.denizen.exceptions.CommandExecutionException;
import net.aufdemrand.denizen.exceptions.InvalidArgumentsException;
import net.aufdemrand.denizen.objects.Element;
import net.aufdemrand.denizen.objects.aH;
import net.aufdemrand.denizen.objects.dEntity;
import net.aufdemrand.denizen.objects.dList;
import net.aufdemrand.denizen.scripts.ScriptEntry;
import net.aufdemrand.denizen.scripts.commands.AbstractCommand;

/**
 * Hurts an entity.
 *
 * @author Jeremy Schroeder, Mason Adkins, Morphan1
 */

public class HurtCommand extends AbstractCommand {

    @Override
    public void parseArgs(ScriptEntry scriptEntry) throws InvalidArgumentsException {

    	for (aH.Argument arg : aH.interpret(scriptEntry.getArguments())) {

            if (!scriptEntry.hasObject("amount")
            		&& (arg.matchesPrimitive(aH.PrimitiveType.Double)
            		|| arg.matchesPrimitive(aH.PrimitiveType.Integer)))
            	scriptEntry.addObject("amount", arg.asElement());

        	else if (!scriptEntry.hasObject("entities")
                	&& arg.matchesPrefix("entity, entities, e, target, targets")) {
                scriptEntry.addObject("entities", ((dList) arg.asType(dList.class)).filter(dEntity.class));
            }
        }
        
        if (!scriptEntry.hasObject("amount"))
        	scriptEntry.addObject("amount", 1.0);
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(ScriptEntry scriptEntry) throws CommandExecutionException {

    	Double amount = ((Element) scriptEntry.getObject("amount")).asDouble();
    
    	if (!scriptEntry.hasObject("entities"))
        	scriptEntry.getPlayer().getDenizenEntity().getLivingEntity().damage(amount);
    	else {
			List<dEntity> entities = (List<dEntity>) scriptEntry.getObject("entities");
    		for (dEntity entity : entities)
    			entity.getLivingEntity().damage(amount);
    	}
    	
    }
}
