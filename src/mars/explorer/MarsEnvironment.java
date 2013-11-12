package mars.explorer;

import mars.explorer.knowledge.Spaceship;
import simbad.sim.*;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import java.util.ArrayList;
import java.util.Random;

/**
 * Creator: asgeir
 * Date: 06.11.13, 14:15
 */
public class MarsEnvironment extends EnvironmentDescription {

    Random random = new Random();
    public MarsEnvironment(){
        final int NUMBEROFROVERS = 5;

        light1IsOn = true;
        light2IsOn = false;
        ambientLightColor = darkgray;
        light1Color = white;
        light2Color = white;
        wallColor = white;
        archColor = green;
        boxColor = white;
        floorColor = red;
        backgroundColor = black;
        hasAxis = true;

        // landing the spaceship  at a random location
        int homeX = random.nextInt(6);
        if(random.nextBoolean()) {
            homeX = -homeX;
        }

        int homeY = random.nextInt(6);
        if(random.nextBoolean()) {
            homeY = -homeY;
        }
        light1SetPosition(homeX,1.5,homeY);
        light2SetPosition(homeX,1.5,homeY);

        // adding the mars rovers
        Rover newRover;
        for (int i = 0; i< NUMBEROFROVERS; i++) {
            newRover = new Rover(new Vector3d(homeX+(i*0.5),0,homeY+(i*0.5)),"Rover" + i);
            add(newRover);
        }

        //places rocks in random clusters in the world
        ArrayList<CherryAgent> rocks = getRocks();
        for(CherryAgent rock: rocks) {
            add(rock);
        }
        Spaceship spaceship = new Spaceship(new Vector3d(homeX, -1f, homeY), "spaceship", 1f);
        spaceship.setColor(new Color3f(0, 0, 1));
        add(spaceship);

    }

    private ArrayList<CherryAgent> getRocks() {
        ArrayList<CherryAgent> rocks = new ArrayList<CherryAgent>();
        int clusters = random.nextInt(4);
        clusters = clusters +2;
        System.out.println("number of clusters:" + clusters);
        for (int i=0; i<clusters; i++) {
            rocks.addAll(getRockCluster());
        }
        return rocks;
    }

    private ArrayList<CherryAgent> getRockCluster() {
        ArrayList<CherryAgent> rocks = new ArrayList<CherryAgent>();
        float x = (float) random.nextInt(1000)/(float)100;
        if(random.nextBoolean()){
            x = -x;
        }
        float y = (float) random.nextInt(1000)/(float)100;
        if(random.nextBoolean()){
            y = -y;
        }
        float z = .1f;

        int numberOfRocks = random.nextInt(3);
        numberOfRocks = numberOfRocks +4;
        System.out.println("number of rocks:" + numberOfRocks);
        for (int i=0; i<numberOfRocks; i++) {
            float carryX = (float) random.nextInt(250)/(float)100;
            if(random.nextBoolean()){
                carryX = -carryX;
            }
            carryX=carryX+x;
            if (carryX > 9.5f ) {
                carryX = 9.5f;
            }
            if (carryX < -9.5f ) {
                carryX = -9.5f;
            }
            float carryY = (float) random.nextInt(250)/(float)100;
            if(random.nextBoolean()){
                carryY = -carryY;
            }
            carryY=carryY+y;
            if (carryY > 9.5f ) {
                carryY = 9.5f;
            }
            if (carryY < -9.5f ) {
                carryY = -9.5f;
            }

            rocks.add(new CherryAgent(new Vector3d(carryX, z, carryY),"rock",.3f));
            System.out.println("        -adding sock on: " + carryX + " - " + carryY);
        }
        return rocks;
    }

}
