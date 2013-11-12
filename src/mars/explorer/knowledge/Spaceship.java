package mars.explorer.knowledge;

import simbad.sim.CherryAgent;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.ArrayList;

/**
 * Creator: asgeir
 * Date: 11.11.13, 22:53
 */
public class Spaceship extends CherryAgent {
    //private ArrayList<Point3d> pickupLocations
    /**
     * Construct an AppleAgent.
     *
     * @param pos
     * @param name
     */
    public Spaceship(Vector3d pos, String name, float radius) {
        super(pos, name, radius);
    }
}
