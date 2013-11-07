package examples.prolog;

import jpl.*;

import java.util.Hashtable;

public class MainJPL {

    public static void main(String[] args) {
        Query q;
        q = new Query("consult('foo.pl')");
        System.out.println(q.hasSolution());
        q = new Query("p(a)");
        System.out.println(q.hasSolution());


        q = new Query("consult('foo.pl')");

        q = new Query("p(a)");
        System.out.println(q.hasSolution());

        Variable X = new Variable("X");
        Term arg[] = { X };
        q= new Query("p", arg);

        while (q.hasMoreElements()){
            Term bound_to_x = (Term) ((Hashtable) q.nextElement()).get("X");
            System.out.println(bound_to_x);
        }

        q = new Query("p(a)");
        System.out.println(q.hasSolution());

        Variable Y = new Variable("Y");
        Term arg2[] = { new Atom("mary"), Y };
        q= new Query("descendent_of", arg2);

        while (q.hasMoreElements()){
            Term bound_to_x = (Term) ((Hashtable) q.nextElement()).get("Y");
            System.out.println(bound_to_x);
        }



    }
}