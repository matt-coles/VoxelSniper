package com.thevoxelbox.voxelsniper.brush;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;

import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.brush.perform.PerformBrush;

/**
 * 
 * @author Voxel
 */
public class FillDown extends PerformBrush {

    private int bsize;

    double trueCircle = 0;

    private static int timesUsed = 0;

    public FillDown() {
        this.setName("Fill Down");
    }

    @Override
    public final int getTimesUsed() {
        return FillDown.timesUsed;
    }

    @Override
    public final void info(final Message vm) {
        vm.brushName(this.getName());
        vm.size();
        // voxelMessage.voxel();
    }

    @Override
    public final void parameters(final String[] par, final com.thevoxelbox.voxelsniper.SnipeData v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Fill Down Parameters:");
            v.sendMessage(ChatColor.AQUA
                    + "/b fd true -- will use a true circle algorithm instead of the skinnier version with classic sniper nubs. /b b false will switch back. (false is default)");
            return;
        }
        for (int x = 1; x < par.length; x++) {
            if (par[x].startsWith("true")) {
                this.trueCircle = 0.5;
                v.sendMessage(ChatColor.AQUA + "True circle mode ON.");
                continue;
            } else if (par[x].startsWith("false")) {
                this.trueCircle = 0;
                v.sendMessage(ChatColor.AQUA + "True circle mode OFF.");
                continue;
            } else {
                v.sendMessage(ChatColor.RED + "Invalid brush parameters! use the info parameter to display parameter info.");
            }
        }
    }

    @Override
    public final void setTimesUsed(final int tUsed) {
        FillDown.timesUsed = tUsed;
    }

    private void fillDown(final Block b) {
        this.setBlockPositionX(b.getX());
        this.setBlockPositionY(b.getY());
        this.setBlockPositionZ(b.getZ());

        final double bpow = Math.pow(this.bsize + this.trueCircle, 2);
        for (int x = 0 - this.bsize; x <= this.bsize; x++) {
            final double xpow = Math.pow(x, 2);
            for (int z = 0 - this.bsize; z <= this.bsize; z++) {
                if (xpow + Math.pow(z, 2) <= bpow) {
                    if (this.getWorld().getBlockTypeIdAt(this.getBlockPositionX() + x, this.getBlockPositionY(), this.getBlockPositionZ() + z) == 0) { // why is this if statement here? You don't want to fill anything in
                                                                                           // the whole column if there is a single block at the level of your
                                                                                           // disc? Are you sure? -gavjenks
                        int y = this.getBlockPositionY();
                        while (--y >= 0) {
                            if (this.getWorld().getBlockTypeIdAt(this.getBlockPositionX() + x, y, this.getBlockPositionZ() + z) != 0) {
                                break;
                            }
                        }
                        for (int yy = y; yy <= this.getBlockPositionY(); yy++) {
                            final Block bl = this.clampY(this.getBlockPositionX() + x, yy, this.getBlockPositionZ() + z);
                            this.current.perform(bl);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected final void arrow(final com.thevoxelbox.voxelsniper.SnipeData v) {
        this.bsize = v.getBrushSize();
        this.fillDown(this.getTargetBlock());
        v.storeUndo(this.current.getUndo());
    }

    @Override
    protected final void powder(final com.thevoxelbox.voxelsniper.SnipeData v) {
        this.bsize = v.getBrushSize();
        this.fillDown(this.getLastBlock());
        v.storeUndo(this.current.getUndo());
    }
}
