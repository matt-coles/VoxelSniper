package com.thevoxelbox.voxelsniper.brush;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.thevoxelbox.voxelsniper.Message;

/**
 * 
 * @author Voxel
 */
public class WarpInStyle extends Brush {

    private static int timesUsed = 0;

    public WarpInStyle() {
        this.setName("Warp Like a Boss");
    }

    @Override
    public final int getTimesUsed() {
        return WarpInStyle.timesUsed;
    }

    @Override
    public final void info(final Message vm) {
        vm.brushName(this.getName());
    }

    @Override
    public final void setTimesUsed(final int tUsed) {
        WarpInStyle.timesUsed = tUsed;
    }

    @Override
    protected final void arrow(final com.thevoxelbox.voxelsniper.SnipeData v) {
        this.tp(v.owner().getPlayer(), v.owner().getPlayer().getLocation()); // arrow just warps you, which is still useful and not annoying. Powder does the
        // effects. -GJ
    } // Ah, nice touch --prz

    @Override
    protected final void powder(final com.thevoxelbox.voxelsniper.SnipeData v) {
        this.getWorld().strikeLightning(v.owner().getPlayer().getLocation());
        this.tp(v.owner().getPlayer(), v.owner().getPlayer().getLocation());
        this.getWorld().strikeLightning(this.getTargetBlock().getLocation());
    }

    protected final void tp(final Player p, final Location l) {
        p.teleport(new Location(p.getWorld(), this.getLastBlock().getX(), this.getLastBlock().getY(), this.getLastBlock().getZ(), l.getYaw(), l.getPitch()));
    }
}
