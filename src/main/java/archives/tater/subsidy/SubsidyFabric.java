package archives.tater.subsidy;

//? if fabric {
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import net.minecraft.util.Unit;

@SuppressWarnings("UnstableApiUsage")
public class SubsidyFabric implements ModInitializer {
    public static final AttachmentType<Unit> CREATIVE_INVENTORY = AttachmentRegistry.create(Subsidy.id("creative_inventory"), builder -> builder
            .persistent(Unit.CODEC)
            .syncWith(Unit.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
            .copyOnDeath());

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                Subsidy.registerCommands(dispatcher));
    }
}
//?}
