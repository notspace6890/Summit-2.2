// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.salhack.summit.command.Command;

public class SoundReloadCommand extends Command
{
    public SoundReloadCommand() {
        super("SoundReload", "Reloads the sound system");
    }
    
    @Override
    public void processCommand(final String input, final String[] args) {
        this.mc.func_147118_V().field_147694_f.func_148596_a();
        this.SendToChat(ChatFormatting.GREEN + "Reloaded the SoundSystem!");
    }
    
    @Override
    public String getHelp() {
        return "Reloads the sound manager sound system";
    }
}
