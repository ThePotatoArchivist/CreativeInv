package archives.tater.subsidy.mixin.client;

import archives.tater.subsidy.Subsidy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.level.GameType;

@Mixin(MultiPlayerGameMode.class)
public class MultiPlayerGameModeMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(
            method = {
                    "handleCreativeModeItemAdd",
                    "handleCreativeModeItemDrop"
            },
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/GameType;isCreative()Z")
    )
    private boolean check(GameType instance, Operation<Boolean> original) {
        return original.call(instance) || Subsidy.hasCreativeInventory(minecraft.player);
    }
}
