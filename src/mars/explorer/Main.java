package mars.explorer;

import simbad.gui.Simbad;

/**
 * Creator: asgeir
 * Date: 06.11.13, 13:37
 */
public class Main {

    public static void main(String[] args) {
        Simbad frame = new Simbad(new MarsEnvironment() ,false);
    }

}
