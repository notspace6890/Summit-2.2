// 
// Decompiled by Procyon v0.5.36
// 

package com.salhack.summit.util.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;

public class ItemUtil
{
    public static boolean Is32k(final ItemStack p_Stack) {
        if (p_Stack.func_77986_q() != null) {
            final NBTTagList tags = p_Stack.func_77986_q();
            for (int i = 0; i < tags.func_74745_c(); ++i) {
                final NBTTagCompound tagCompound = tags.func_150305_b(i);
                if (tagCompound != null && Enchantment.func_185262_c((int)tagCompound.func_74771_c("id")) != null) {
                    final Enchantment enchantment = Enchantment.func_185262_c((int)tagCompound.func_74765_d("id"));
                    final short lvl = tagCompound.func_74765_d("lvl");
                    if (enchantment != null) {
                        if (!enchantment.func_190936_d()) {
                            if (lvl >= 1000) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
