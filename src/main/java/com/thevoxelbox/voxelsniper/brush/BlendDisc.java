package com.thevoxelbox.voxelsniper.brush;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.thevoxelbox.voxelsniper.SnipeData;
import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.Undo;

/**
 *
 */
public class BlendDisc extends Brush {

    protected String ablendmode = "exclude";
    protected String wblendmode = "exclude";

    private static int timesUsed = 0;

    public BlendDisc() {
        this.setName("Blend Disc");
    }

    public final void dblend(final SnipeData v) {
        final int bsize = v.getBrushSize();
        final int[][] oldmats = new int[2 * (bsize + 1) + 1][2 * (bsize + 1) + 1]; // Array that holds the original materials plus a buffer
        final int[][] newmats = new int[2 * bsize + 1][2 * bsize + 1]; // Array that holds the blended materials
        int maxblock = 0; // What is the highest material ID that is a block?

        // Log current materials into oldmats
        for (int x = 0; x <= 2 * (bsize + 1); x++) {
            for (int z = 0; z <= 2 * (bsize + 1); z++) {
                oldmats[x][z] = this.getBlockIdAt(this.getBlockPositionX() - bsize - 1 + x, this.getBlockPositionY(), this.getBlockPositionZ() - bsize - 1 + z);
            }
        }

        // Log current materials into newmats
        for (int x = 0; x <= 2 * bsize; x++) {
            for (int z = 0; z <= 2 * bsize; z++) {
                newmats[x][z] = oldmats[x + 1][z + 1];
            }
        }

        // Find highest placeable block ID
        for (int i = 0; i < Material.values().length; i++) {
            if (Material.values()[i].isBlock() && Material.values()[i].getId() > maxblock) {
                maxblock = Material.values()[i].getId();
            }
        }

        // Blend materials
        for (int x = 0; x <= 2 * bsize; x++) {
            for (int z = 0; z <= 2 * bsize; z++) {
                final int[] matfreq = new int[maxblock + 1]; // Array that tracks frequency of materials neighboring given block
                int modematcount = 0;
                int modematid = 0;
                boolean tiecheck = true;

                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        if (!(m == 0 && n == 0)) {
                            matfreq[oldmats[x + 1 + m][z + 1 + n]]++;
                        }
                    }
                }

                // Find most common neighboring material.
                for (int i = 0; i <= maxblock; i++) {
                    if (matfreq[i] > modematcount && !(this.ablendmode.equalsIgnoreCase("exclude") && i == 0)
                            && !(this.wblendmode.equalsIgnoreCase("exclude") && (i == 8 || i == 9))) {
                        modematcount = matfreq[i];
                        modematid = i;
                    }
                }
                // Make sure there'world not a tie for most common
                for (int i = 0; i < modematid; i++) {
                    if (matfreq[i] == modematcount && !(this.ablendmode.equalsIgnoreCase("exclude") && i == 0)
                            && !(this.wblendmode.equalsIgnoreCase("exclude") && (i == 8 || i == 9))) {
                        tiecheck = false;
                    }
                }

                // Record most common neighbor material for this block
                if (tiecheck) {
                    newmats[x][z] = modematid;
                }
            }
        }

        // Make the changes
        final Undo h = new Undo(this.getTargetBlock().getWorld().getName());
        final double rpow = Math.pow(bsize + 1, 2);
        for (int x = 2 * bsize; x >= 0; x--) {
            final double xpow = Math.pow(x - bsize - 1, 2);
            for (int z = 2 * bsize; z >= 0; z--) {

                if (xpow + Math.pow(z - bsize - 1, 2) <= rpow) {
                    if (!(this.ablendmode.equalsIgnoreCase("exclude") && newmats[x][z] == 0)
                            && !(this.wblendmode.equalsIgnoreCase("exclude") && (newmats[x][z] == 8 || newmats[x][z] == 9))) {
                        if (this.getBlockIdAt(this.getBlockPositionX() - bsize + x, this.getBlockPositionY(), this.getBlockPositionZ() - bsize + z) != newmats[x][z]) {
                            h.put(this.clampY(this.getBlockPositionX() - bsize + x, this.getBlockPositionY(), this.getBlockPositionZ() - bsize + z));
                        }
                        this.setBlockIdAt(newmats[x][z], this.getBlockPositionX() - bsize + x, this.getBlockPositionY(), this.getBlockPositionZ() - bsize + z);
                    }
                }
            }
        }
        v.storeUndo(h);
    }

    @Override
    public final int getTimesUsed() {
        return BlendDisc.timesUsed;
    }

    @Override
    public final void info(final Message vm) {
        /*
         * if (!ablendmode.equalsIgnoreCase("exclude") && !ablendmode.equalsIgnoreCase("include")) { ablendmode = "exclude"; }
         */
        if (!this.wblendmode.equalsIgnoreCase("exclude") && !this.wblendmode.equalsIgnoreCase("include")) {
            this.wblendmode = "exclude";
        }
        vm.brushName(this.getName());
        vm.size();
        vm.voxel();
        // voxelMessage.custom(ChatColor.BLUE + "Air Mode: " + ablendmode);
        vm.custom(ChatColor.BLUE + "Water Mode: " + this.wblendmode);
    }

    @Override
    public final void parameters(final String[] par, final com.thevoxelbox.voxelsniper.SnipeData v) {
        if (par[1].equalsIgnoreCase("info")) {
            v.sendMessage(ChatColor.GOLD + "Blend Disc Parameters:");
            // v.sendMessage(ChatColor.AQUA + "/b bd air -- toggle include or exclude (default) air");
            v.sendMessage(ChatColor.AQUA + "/b bd water -- toggle include or exclude (default) water");
            return;
        }
        /*
         * if (par[1].equalsIgnoreCase("air")) { if (ablendmode.equalsIgnoreCase("exclude")){ ablendmode="include"; } else { ablendmode="exclude"; }
         * v.sendMessage(ChatColor.AQUA + "Air Mode: " + ablendmode);
         * 
         * return; }
         */
        if (par[1].equalsIgnoreCase("water")) {
            if (this.wblendmode.equalsIgnoreCase("exclude")) {
                this.wblendmode = "include";
            } else {
                this.wblendmode = "exclude";
            }
            v.sendMessage(ChatColor.AQUA + "Water Mode: " + this.wblendmode);
        }
    }

    @Override
    public final void setTimesUsed(final int tUsed) {
        BlendDisc.timesUsed = tUsed;
    }

    @Override
    protected final void arrow(final com.thevoxelbox.voxelsniper.SnipeData v) {
        this.setBlockPositionX(this.getTargetBlock().getX());
        this.setBlockPositionY(this.getTargetBlock().getY());
        this.setBlockPositionZ(this.getTargetBlock().getZ());

        this.ablendmode = "include";
        this.dblend(v);
    }

    @Override
    protected final void powder(final com.thevoxelbox.voxelsniper.SnipeData v) {
        this.setBlockPositionX(this.getTargetBlock().getX());
        this.setBlockPositionY(this.getTargetBlock().getY());
        this.setBlockPositionZ(this.getTargetBlock().getZ());

        this.ablendmode = "exclude";
        this.dblend(v);
    }
}
