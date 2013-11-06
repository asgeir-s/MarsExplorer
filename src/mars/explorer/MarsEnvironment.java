package mars.explorer;

import simbad.sim.EnvironmentDescription;


import javax.vecmath.Vector3d;

/**
 * Creator: asgeir
 * Date: 06.11.13, 14:15
 */
public class MarsEnvironment extends EnvironmentDescription {
    public MarsEnvironment(){
        //add(new Arch(new Vector3d(3,0,-3),this));
        add(new Rover(new Vector3d(0, 0, 0),"My Rover"));
    }
}
