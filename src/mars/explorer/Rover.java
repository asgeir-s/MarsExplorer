package mars.explorer;

import simbad.sim.Agent;
import simbad.sim.CherryAgent;
import simbad.sim.SimpleAgent;

import javax.vecmath.Vector3d;

/**
 * Creator: asgeir
 * Date: 06.11.13, 14:20
 */
public class Rover extends Agent {

    PrologHelper prolog; // to have a dynamically knowledge base (individual knowledge) we have to
    // initialize this class

    public Rover(Vector3d position, String name) {
        super(position, name);
        prolog = new PrologHelper(name);
    }

    public void initBehavior() {
        // this is the static common knowledg for all rovers
        PrologHelper.query("consult('rover_knowledge.pl')");
    }

    public void performBehavior() {
        if (collisionDetected()) {
            // stop the robot
            setTranslationalVelocity(0.0);
            setRotationalVelocity(0);
        } else {
            // progress at 0.5 m/s
            setTranslationalVelocity(0.5);
            // frequently change orientation
            if ((getCounter() % 100) == 0)
                setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
        }
        // Test if there is an agent near . */
        if (anOtherAgentIsVeryNear()) {
            boolean hasSolution = false;
            ;

            if (prolog.queryIndividual("hasRock") == null) {
                SimpleAgent agent = getVeryNearAgent();

                if (agent instanceof CherryAgent) {
                    agent.detach();
                    prolog.assertToKB("hasRock");
                    System.out.println("rock picked! by " + name);

                }
            }
            else {
                System.out.println("Found a rock, but is already carrying a rock");
            }
        }
    }
}
