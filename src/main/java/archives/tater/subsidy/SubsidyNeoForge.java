package archives.tater.subsidy;

//? if neoforge {
/*import com.mojang.serialization.MapCodec;

import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(Subsidy.MOD_ID)
public class SubsidyNeoForge {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Subsidy.MOD_ID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Unit>> CREATIVE_INVENTORY =
            ATTACHMENT_TYPES.register("creative_inventory", () -> AttachmentType.builder(() -> Unit.INSTANCE)
                    .serialize(MapCodec.unit(Unit.INSTANCE))
                    // Sync only to the owning player, like Fabric's AttachmentSyncPredicate.targetOnly().
                    // The codec must write at least one byte: NeoForge drops zero-byte payloads
                    // (e.g. Unit.STREAM_CODEC) from the initial login sync, which would lose the
                    // flag on the client after every reconnect.
                    .sync((holder, player) -> holder == player, ByteBufCodecs.BOOL.map(present -> Unit.INSTANCE, unit -> Boolean.TRUE))
                    .copyOnDeath()
                    .build());

    public SubsidyNeoForge(IEventBus modEventBus) {
        ATTACHMENT_TYPES.register(modEventBus);
        NeoForge.EVENT_BUS.addListener((RegisterCommandsEvent event) -> Subsidy.registerCommands(event.getDispatcher()));
    }
}
*///?}
