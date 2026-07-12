package archives.tater.subsidy;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.player.Player;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

/**
 * Loader-independent mod logic; the per-loader entrypoints are
 * {@link SubsidyFabric} and {@link SubsidyNeoForge}.
 */
public class Subsidy {
    public static final String MOD_ID = "subsidy";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    // This logger is used to write text to the console and the log file.
    // It is considered best practice to use your mod id as the logger's name.
    // That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static boolean hasCreativeInventory(Player player) {
        //? if fabric {
        return player.hasAttached(SubsidyFabric.CREATIVE_INVENTORY) && !player.isSpectator();
        //?} else {
        /*return player.hasData(SubsidyNeoForge.CREATIVE_INVENTORY.get()) && !player.isSpectator();*/
        //?}
    }

    public static void enableCreativeInventory(ServerPlayer player) {
        //? if fabric {
        player.setAttached(SubsidyFabric.CREATIVE_INVENTORY, Unit.INSTANCE);
        //?} else {
        /*player.setData(SubsidyNeoForge.CREATIVE_INVENTORY.get(), Unit.INSTANCE);*/
        //?}
    }

    public static void disableCreativeInventory(ServerPlayer player) {
        //? if fabric {
        player.removeAttached(SubsidyFabric.CREATIVE_INVENTORY);
        //?} else {
        /*player.removeData(SubsidyNeoForge.CREATIVE_INVENTORY.get());*/
        //?}
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(literal("creative_inv")
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                .then(literal("enable")
                        .executes(command -> {
                            enableCreativeInventory(command.getSource().getPlayerOrException());
                            command.getSource().sendSuccess(() -> Component.translatable("commands.subsidy.enable.self"), true);
                            return 1;
                        })
                        .then(argument("target", EntityArgument.player())
                                .executes(command -> {
                                    var target = EntityArgument.getPlayer(command, "target");
                                    enableCreativeInventory(target);
                                    command.getSource().sendSuccess(() -> Component.translatable("commands.subsidy.enable", target.getDisplayName()), true);
                                    return 1;
                                })
                        )
                )
                .then(literal("disable")
                        .executes(command -> {
                            disableCreativeInventory(command.getSource().getPlayerOrException());
                            command.getSource().sendSuccess(() -> Component.translatable("commands.subsidy.disable.self"), true);
                            return 1;
                        })
                        .then(argument("target", EntityArgument.player())
                                .executes(command -> {
                                    var target = EntityArgument.getPlayer(command, "target");
                                    disableCreativeInventory(target);
                                    command.getSource().sendSuccess(() -> Component.translatable("commands.subsidy.disable", target.getDisplayName()), true);
                                    return 1;
                                })
                        )
                )
        );
    }
}
