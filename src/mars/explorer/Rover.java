package mars.explorer;

import jpl.Atom;
import jpl.Term;
import jpl.Variable;
import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import java.util.Hashtable;

/**
 * Creator: asgeir
 * Date: 06.11.13, 14:20
 */
public class Rover extends Robot {

    private LightSensor lightSensorLeft;
    private LightSensor lightSensorRight;
    private String name;

    public Rover(Vector3d position, String name) {
        super(position, name);
        this.name = name.toLowerCase();

        lightSensorLeft = RobotFactory.addLightSensorLeft(this);
        lightSensorRight = RobotFactory.addLightSensorRight(this);
    }

    public void initBehavior() {
        // this is the common knowledge for all rovers
        PrologHelper.query("consult('rover_knowledge.pl')");
        PrologHelper.assertToKB("hasRock(" + name + ") :- false"); // at start this rover does not
        // have a rock
    }

    public void performBehavior() {

        // get current position. Only used to check that agents don't leave the "world"
        Point3d coord = new Point3d();
        this.getCoords(coord);
        double[] position = new double[3];
        coord.get(position);

        // ask Prolog what to do
        Variable X = new Variable("X");
        Atom nameAtom = new Atom(name);
        Term arg[] = {nameAtom, X};
        Hashtable[] answer = PrologHelper.query("todo", arg);

        // if we have an answer
        if (answer != null) {
            Term toDoTerm = (Term) answer[0].get("X"); // take the first answer
            String toDoString = toDoTerm.name();

            if (toDoString.equals("home")) {
                setColor(new Color3f(0, 1, 0));
                if (anOtherAgentIsVeryNear()) {
                    SimpleAgent nearAgent = getVeryNearAgent();

                    if (nearAgent.getName().equals("spaceship")) {
                        PrologHelper.retractFromKB("hasRock(" + name + ")");
                        System.out.println("rock delivered");
                        setColor(new Color3f(0, 1, 0));

                    }
                }
                goTowardsLight();
            }

            else if (toDoString.equals("search")) {
                setColor(new Color3f(1, 1, 1));
                if (anOtherAgentIsVeryNear()) {
                    SimpleAgent agent = getVeryNearAgent();

                    if (agent.getName().equals("rock")) {
                        agent.detach();
                        PrologHelper.assertToKB("hasRock(" + name + ")");
                        System.out.println("rock picked! by " + name);
                    }
                }

                setTranslationalVelocity(0.5);
                if ((getCounter() % 100) == 0)
                    setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));


            }
        }
        // if the agent is leaving this world, turn back towards the light!
        if (position[0] >= 10 || position[0] <= -10 || position[2] >= 10 || position[2] <= -10) {
            goTowardsLight();
        }
    }

    private void goTowardsLight() {
        // progress at 0.5 m/s
        setTranslationalVelocity(0.5);
        // turn towards light
        float llum = lightSensorLeft.getAverageLuminance();
        float rlum = lightSensorRight.getAverageLuminance();
        //setRotationalVelocity((llum - rlum) *  Math.PI);
        setRotationalVelocity((llum - rlum) * Math.PI);
    }
}
