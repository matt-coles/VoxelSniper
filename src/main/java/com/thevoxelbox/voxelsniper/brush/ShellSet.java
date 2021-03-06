package com.thevoxelbox.voxelsniper.brush;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.Undo;

/**
 * 
 * @author Piotr
 */
public class ShellSet extends Brush {

    protected Block b = null;

    private static int timesUsed = 0;

    public ShellSet() {
        this.setName("Shell Set");
    }

    @Override
    public final int getTimesUsed() {
        return ShellSet.timesUsed;
    }

    @Override
    public final void info(final Message vm) {
        this.b = null;
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        vm.replace();
    }

    @Override
    public final void setTimesUsed(final int tUsed) {
        ShellSet.timesUsed = tUsed;
    }

    private boolean set(final Block bl, final SnipeData v) {
        if (this.b == null) {
            this.b = bl;
            return true;
        } else {
            if (!this.b.getWorld().getName().equals(bl.getWorld().getName())) {
                v.sendMessage(ChatColor.RED + "You selected points in different worlds!");
                this.b = null;
                return true;
            }
            final int lowx = (this.b.getX() <= bl.getX()) ? this.b.getX() : bl.getX();
            final int lowy = (this.b.getY() <= bl.getY()) ? this.b.getY() : bl.getY();
            final int lowz = (this.b.getZ() <= bl.getZ()) ? this.b.getZ() : bl.getZ();
            final int highx = (this.b.getX() >= bl.getX()) ? this.b.getX() : bl.getX();
            final int highy = (this.b.getY() >= bl.getY()) ? this.b.getY() : bl.getY();
            final int highz = (this.b.getZ() >= bl.getZ()) ? this.b.getZ() : bl.getZ();
            if (Math.abs(highx - lowx) * Math.abs(highz - lowz) * Math.abs(highy - lowy) > 5000000) {
                v.sendMessage(ChatColor.RED + "Selection size above hardcoded limit, please use a smaller selection.");
            } else {
                final int bId = v.getVoxelId();
                final int brId = v.getReplaceId();
                final ArrayList<Block> blocks = new ArrayList<Block>(((Math.abs(highx - lowx) * Math.abs(highz - lowz) * Math.abs(highy - lowy)) / 2));
                for (int y = lowy; y <= highy; y++) {
                    for (int x = lowx; x <= highx; x++) {
                        for (int z = lowz; z <= highz; z++) {
                            if (this.getWorld().getBlockTypeIdAt(x, y, z) == brId) {
                                continue;
                            } else if (this.getWorld().getBlockTypeIdAt(x + 1, y, z) == brId) {
                                continue;
                            } else if (this.getWorld().getBlockTypeIdAt(x - 1, y, z) == brId) {
                                continue;
                            } else if (this.getWorld().getBlockTypeIdAt(x, y, z + 1) == brId) {
                                continue;
                            } else if (this.getWorld().getBlockTypeIdAt(x, y, z - 1) == brId) {
                                continue;
                            } else if (this.getWorld().getBlockTypeIdAt(x, y + 1, z) == brId) {
                                continue;
                            } else if (this.getWorld().getBlockTypeIdAt(x, y - 1, z) == brId) {
                                continue;
                            } else {
                                blocks.add(this.getWorld().getBlockAt(x, y, z));
                            }
                        }
                    }
                }

                final Undo h = new Undo(this.getTargetBlock().getWorld().getName());
                for (final Block blo : blocks) {
                    if (blo.getTypeId() != bId) {
                        h.put(blo);
                        blo.setTypeId(bId);
                    }
                }
                v.storeUndo(h);
                v.sendMessage(ChatColor.AQUA + "Shell complete.");
            }

            this.b = null;
            return false;
        }
    }

    @Override
    protected final void arrow(final com.thevoxelbox.voxelsniper.SnipeData v) { // Derp
        if (this.set(this.getTargetBlock(), v)) {
            v.owner().getPlayer().sendMessage(ChatColor.GRAY + "Point one");
        }
    }

    @Override
    protected final void powder(final com.thevoxelbox.voxelsniper.SnipeData v) {
        if (this.set(this.getLastBlock(), v)) {
            v.owner().getPlayer().sendMessage(ChatColor.GRAY + "Point one");
        }
    }
}
