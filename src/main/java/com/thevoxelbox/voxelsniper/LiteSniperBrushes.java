package com.thevoxelbox.voxelsniper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thevoxelbox.voxelsniper.brush.Ball;
import com.thevoxelbox.voxelsniper.brush.BlendBall;
import com.thevoxelbox.voxelsniper.brush.BlendDisc;
import com.thevoxelbox.voxelsniper.brush.BlendVoxel;
import com.thevoxelbox.voxelsniper.brush.BlendVoxelDisc;
import com.thevoxelbox.voxelsniper.brush.Blob;
import com.thevoxelbox.voxelsniper.brush.Brush;
import com.thevoxelbox.voxelsniper.brush.CheckerVoxelDisc;
import com.thevoxelbox.voxelsniper.brush.CloneStamp;
import com.thevoxelbox.voxelsniper.brush.CopyPasta;
import com.thevoxelbox.voxelsniper.brush.Disc;
import com.thevoxelbox.voxelsniper.brush.DiscFace;
import com.thevoxelbox.voxelsniper.brush.Dome;
import com.thevoxelbox.voxelsniper.brush.Drain;
import com.thevoxelbox.voxelsniper.brush.Eraser;
import com.thevoxelbox.voxelsniper.brush.Erode;
import com.thevoxelbox.voxelsniper.brush.Line;
import com.thevoxelbox.voxelsniper.brush.Overlay;
import com.thevoxelbox.voxelsniper.brush.Painting;
import com.thevoxelbox.voxelsniper.brush.RandomErode;
import com.thevoxelbox.voxelsniper.brush.Ring;
import com.thevoxelbox.voxelsniper.brush.Ruler;
import com.thevoxelbox.voxelsniper.brush.Scanner;
import com.thevoxelbox.voxelsniper.brush.Snipe;
import com.thevoxelbox.voxelsniper.brush.SplatterBall;
import com.thevoxelbox.voxelsniper.brush.SplatterDisc;
import com.thevoxelbox.voxelsniper.brush.SplatterOverlay;
import com.thevoxelbox.voxelsniper.brush.SplatterVoxel;
import com.thevoxelbox.voxelsniper.brush.SplatterVoxelDisc;
import com.thevoxelbox.voxelsniper.brush.StencilList;
import com.thevoxelbox.voxelsniper.brush.TreeSnipe;
import com.thevoxelbox.voxelsniper.brush.Triangle;
import com.thevoxelbox.voxelsniper.brush.Underlay;
import com.thevoxelbox.voxelsniper.brush.VoltMeter;
import com.thevoxelbox.voxelsniper.brush.Voxel;
import com.thevoxelbox.voxelsniper.brush.VoxelDisc;
import com.thevoxelbox.voxelsniper.brush.VoxelDiscFace;

/**
 * 
 * @author Voxel
 */
public enum LiteSniperBrushes {
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~przerwap~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \\
    SNIPE(Snipe.class, "s", "snipe"), // [ 1 ] \\
    DISC(Disc.class, "d", "disc"), // [ 2 ] \\
    DISC_FACE(DiscFace.class, "df", "discface"), // [ 3 ] \\
    BALL(Ball.class, "b", "ball"), // [ 6 ] \\
    VOXEL(Voxel.class, "v", "voxel"), // [ 8 ] \\
    VOXEL_DISC(VoxelDisc.class, "vd", "voxeldisc"), // [ 9 ] \\
    VOXEL_DISC_FACE(VoxelDiscFace.class, "vdf", "voxeldiscface"), // [ 11 ] \\
    CLONE_STAMP(CloneStamp.class, "cs", "clonestamp"), // [ 22 ] \\
    ERODE(Erode.class, "e", "erode"), // [ 23 ] \\
    PAINTING(Painting.class, "paint", "painting"), // [ 25 ] \\
    RING(Ring.class, "ri", "ring"), // [ 41 ] \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Giltwist~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \\
    SPLATTER_DISC(SplatterDisc.class, "sd", "splatdisc"), // [ 1 ] \\
    SPLATTER_VOXEL_DISC(SplatterVoxelDisc.class, "svd", "splatvoxeldisc"), // [ 2 ] \\
    SPLATTER_BALL(SplatterBall.class, "sb", "splatball"), // [ 3 ] \\
    SPLATTER_VOXEL(SplatterVoxel.class, "sv", "splatvoxel"), // [ 4 ] \\
    SPLATTER_OVERLAY(SplatterOverlay.class, "sover", "splatteroverlay"), // [ 7 ] \\
    BLOB(Blob.class, "blob", "splatblob"), // [ 5 ] \\
    BLEND_VOXEL_DISC(BlendVoxelDisc.class, "bvd", "blendvoxeldisc"), // [ 8 ] \\
    BLEND_VOXEL(BlendVoxel.class, "bv", "blendvoxel"), // [ 9 ] \\
    BLEND_DISC(BlendDisc.class, "bd", "blenddisc"), // [ 10 ] \\
    BLEND_BALL(BlendBall.class, "bb", "blendball"), // [ 11 ] \\
    LINE(Line.class, "l", "line"), // [ 12 ] \\
    RANDOM_ERODE(RandomErode.class, "re", "randomerode"), // [ 16 ] \\
    ERASER(Eraser.class, "erase", "eraser"), // [ 20 ] \\
    COPYPASTA(CopyPasta.class, "cp", "copypasta"), // [ 22 ] \\
    TRIANGLE(Triangle.class, "tri", "triangle"), // [ 19 ] \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~DivineRage~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \\
    SCANNER(Scanner.class, "sc", "scanner"), // [ 5 ] \\
    GENERATE_TREE(TreeSnipe.class, "t", "treesnipe"), // [ 2 ] \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Gavjenks~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \\
    DRAIN(Drain.class, "drain", "drain"), // [ 10 ] \\
    DOME(Dome.class, "dome", "domebrush"), // [ 6 ] \\
    OVERLAY(Overlay.class, "over", "overlay"), // [ 4 ] \\
    RULER(Ruler.class, "r", "ruler"), // [ 7 ] \\
    VOLT_METER(VoltMeter.class, "volt", "voltmeter"), // [ 8 ] \\
    STENCILLIST(StencilList.class, "sl", "stencillist"), // [ 24 ] \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Jmck95~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \\
    UNDERLAY(Underlay.class, "under", "underlay"), // [ 1 ] \\

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~MikeMatrix~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ \\
    CHECKER_VOXEL_DISC(CheckerVoxelDisc.class, "cvd", "checkervoxeldisc"); // [ 1 ] \\

    private static final Map<String, LiteSniperBrushes> BRUSHES;

    /**
     * @return HashMap<String, Brush>
     */
    public static HashMap<String, String> getBrushAlternates() {
        final HashMap<String, String> _temp = new HashMap<String, String>();

        for (final LiteSniperBrushes _vb : LiteSniperBrushes.BRUSHES.values()) {
            _temp.put(_vb.getLong(), _vb.getShort());
        }

        return _temp;
    }

    /**
     * @return HashMap<String, Brush>
     */
    public static HashMap<String, Brush> getSniperBrushes() {
        final HashMap<String, Brush> _temp = new HashMap<String, Brush>();

        for (final Entry<String, LiteSniperBrushes> _set : LiteSniperBrushes.BRUSHES.entrySet()) {
            _temp.put(_set.getKey(), _set.getValue().getBrush());
        }

        return _temp;
    }

    private Class<? extends Brush> brush;

    private String shortName;

    private String longName;

    static {
        BRUSHES = new HashMap<String, LiteSniperBrushes>();

        for (final LiteSniperBrushes _vb : LiteSniperBrushes.values()) {
            LiteSniperBrushes.BRUSHES.put(_vb.getShort(), _vb);
        }
    }

    private LiteSniperBrushes(final Class<? extends Brush> brush, final String shortName, final String longName) {
        this.brush = brush;
        this.shortName = shortName;
        this.longName = longName;
    }

    private Brush getBrush() {
        Brush _b;
        try {
            try {
                _b = this.brush.getConstructor().newInstance();
                return _b;
            } catch (final InstantiationException _ex) {
                Logger.getLogger(SniperBrushes.class.getName()).log(Level.SEVERE, null, _ex);
            } catch (final IllegalAccessException _ex) {
                Logger.getLogger(SniperBrushes.class.getName()).log(Level.SEVERE, null, _ex);
            } catch (final IllegalArgumentException _ex) {
                Logger.getLogger(SniperBrushes.class.getName()).log(Level.SEVERE, null, _ex);
            } catch (final InvocationTargetException _ex) {
                Logger.getLogger(SniperBrushes.class.getName()).log(Level.SEVERE, null, _ex);
            }
        } catch (final NoSuchMethodException _ex) {
            Logger.getLogger(SniperBrushes.class.getName()).log(Level.SEVERE, null, _ex);
        } catch (final SecurityException _ex) {
            Logger.getLogger(SniperBrushes.class.getName()).log(Level.SEVERE, null, _ex);
        }
        return null;
    }

    private String getLong() {
        return this.longName;
    }

    private String getShort() {
        return this.shortName;
    }
}
