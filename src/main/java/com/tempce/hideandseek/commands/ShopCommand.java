package com.tempce.hideandseek.commands;

import com.tempce.hideandseek.core.game.GameState;
import com.tempce.hideandseek.core.game.Shop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.tempce.hideandseek.core.game.GameMaster.gameState;

public class ShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player player) {
            if (gameState != GameState.STARTED) {
                commandSender.sendMessage("§cこのコマンドはゲーム中のみ実行できます。");
            } else {
                Shop.show(player, false);
            }
        } else {
            commandSender.sendMessage("§cこのコマンドはコンソールから実行できません。");
        }

        return true;
    }
}
