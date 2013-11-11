package mars.explorer;

import jpl.*;
import jpl.Float;
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

        /**
         *
         * tell Prolog whats the situation and ask what to do
         *
         * syntax: todo(Agent, xPos, yPos, AgentNearType, Answer).
         *
         * Possible answers:
         * home (and drop crums)
         * pickup
         * search
         * drop
         */

        // tell Prolog whats the situation and ask what to do
        Variable X = new Variable("X");
        Atom nameAtom = new Atom(name);
        Float xPos = new Float(position[0]);
        Float yPos = new Float(position[2]);
        Atom neraAgent = new Atom("non");

        if(anOtherAgentIsVeryNear()) {
            neraAgent = new Atom(getVeryNearAgent().getName());
        }

        Term arg[] = {nameAtom,xPos, yPos, neraAgent, X};
        Hashtable[] answer = PrologHelper.query("todo", arg);

        // if we have an answer
        if (answer != null) {
            Term toDoTerm = (Term) answer[0].get("X"); // take the first answer
            String toDoString = toDoTerm.name();

            if (toDoString.equals("home")) {
                setColor(new Color3f(0, 1, 0));
                goTowardsLight();
            }

            else if (toDoString.equals("pickup")) {
                getVeryNearAgent().detach();
                PrologHelper.assertToKB("hasRock(" + name + ")");
                System.out.println("rock picked! by " + name);
            }

            else if (toDoString.equals("search")) {
                setColor(new Color3f(1, 1, 1));
                setTranslationalVelocity(0.5);
                if ((getCounter() % 100) == 0)
                    setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
            }

            else if (toDoString.equals("drop")) {
                PrologHelper.retractFromKB("hasRock(" + name + ")");
                System.out.println("rock delivered");
                setColor(new Color3f(0, 1, 0));
            }
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