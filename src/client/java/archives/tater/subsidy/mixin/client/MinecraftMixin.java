package archives.tater.subsidy.mixin.client;

import archives.tater.subsidy.Subsidy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import org.objectweb.asm.Opcodes;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Abilities;

import org.jetbrains.annotations.Nullable;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow
    @Nullable
    public LocalPlayer player;

    @WrapOperation(
            method = "pickBlock",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/entity/player/Abilities;instabuild:Z", opcode = Opcodes.GETFIELD)
    )
    private boolean check(Abilities instance, Operation<Boolean> original) {
        return original.call(instance) || Subsidy.hasCreativeInventory(player);
    }
}
