package mars.explorer;

import simbad.sim.*;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Creator: asgeir
 * Date: 06.11.13, 14:15
 */
public class MarsEnvironment extends EnvironmentDescription {

    Random random = new Random();
    public MarsEnvironment(){
        light1IsOn = true;


        light2IsOn = false;
        ambientLightColor = darkgray;
        light1Color = white;
        light2Color = white;
        wallColor = blue;
        archColor = green;
        boxColor = white;
        floorColor = red;
        backgroundColor = black;
        hasAxis = true;

        // landing the mother ship at a random location
        int homeX = random.nextInt(10);
        if(random.nextBoolean()) {
            homeX = -homeX;
        }

        int homeY = random.nextInt(10);
        if(random.nextBoolean()) {
            homeY = -homeY;
        }
        light1SetPosition(homeX,1,homeY);
        light2SetPosition(homeX,1,homeY);
        // adding the mars rovers
        add(new Rover(new Vector3d(homeX,0,homeY),"Rover1"));
        add(new Rover(new Vector3d(homeX,0,homeY),"Rover2"));
        add(new Rover(new Vector3d(homeX,0,homeY),"Rover3"));

        //places rocks in random clusters in the world
        ArrayList<CherryAgent> rocks = getRocks();
        for(CherryAgent rock: rocks) {
            add(rock);
        }
    }

    private ArrayList<CherryAgent> getRocks() {
        ArrayList<CherryAgent> rocks = new ArrayList<CherryAgent>();
        int clusters = random.nextInt(4);
        clusters = clusters +20;
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
        numberOfRocks = numberOfRocks +2;
        System.out.println("number of rocks:" + numberOfRocks);
        for (int i=0; i<numberOfRocks; i++) {
            float carryX = (float) random.nextInt(250)/(float)100;
            if(random.nextBoolean()){
                carryX = -carryX;
            }
            carryX=carryX+x;
            if (carryX > 10 ) {
                carryX = 10;
            }
            if (carryX < -10 ) {
                carryX = -10;
            }
            float carryY = (float) random.nextInt(250)/(float)100;
            if(random.nextBoolean()){
                carryY = -carryY;
            }
            carryY=carryY+y;
            if (carryY > 10 ) {
                carryY = 10;
            }
            if (carryY < -10 ) {
                carryY = -10;
            }

            rocks.add(new CherryAgent(new Vector3d(carryX, z, carryY),"rock",.3f));
            System.out.println("        -adding sock on: " + carryX + " - " + carryY);
        }
        return rocks;
    }
}
