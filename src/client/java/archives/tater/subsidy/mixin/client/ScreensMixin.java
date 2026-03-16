package archives.tater.subsidy.mixin.client;

import archives.tater.subsidy.Subsidy;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.network.chat.Component;

@Mixin({
        InventoryScreen.class,
        CreativeModeInventoryScreen.class,
        AbstractContainerScreen.class
})
public abstract class ScreensMixin extends Screen {
    protected ScreensMixin(Component component) {
        super(component);
    }

    @WrapOperation(
            method = {
                    "init",
                    "containerTick",
                    "mouseClicked"
            },
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasInfiniteItems()Z")
    )
    private boolean check(MultiPlayerGameMode instance, Operation<Boolean> original) {
        return original.call(instance) || Subsidy.hasCreativeInventory(minecraft.player);
    }
}
