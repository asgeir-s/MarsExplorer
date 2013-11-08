package test.prolog;

import jpl.Term;
import jpl.Util;
import jpl.Variable;
import junit.framework.Assert;
import mars.explorer.PrologHelper;
import org.junit.Before;

import java.util.Hashtable;

/**
 * Creator: asgeir
 * Date: 08.11.13, 18:40
 */
public class PrologHelperTest {
    PrologHelper prolog;
    @Before
    public void setUp() {
        PrologHelper.query("['foo.pl']");
        prolog = new PrologHelper();
    }

    @org.junit.Test
    public void testAssertToKB() throws Exception {
        Assert.assertNull(PrologHelper.query("dyn(a)"));
        Assert.assertNull(PrologHelper.query("hasRock"));

        prolog.assertToKB("dyn(a)");
        prolog.assertToKB("dyn(b)");
        prolog.assertToKB("dyn(c)");
        prolog.assertToKB("dyn(d)");
        prolog.assertToKB("hasRock");

        Assert.assertNotNull(PrologHelper.query("dyn(b)"));
        prolog.retractFromKB("dyn(b)");
        Assert.assertNull(PrologHelper.query("dyn(b)"));

        Assert.assertNotNull(PrologHelper.query("dyn(a)"));
        Assert.assertNotNull(PrologHelper.query("dyn(c)"));
        Assert.assertNotNull(PrologHelper.query("hasRock"));
        Assert.assertNull(PrologHelper.query("fake"));
    }

    @org.junit.Test
    public void testRetractFromKB() throws Exception {
        prolog.assertToKB("dynamic");
        Assert.assertNotNull(PrologHelper.query("dynamic"));
        prolog.retractFromKB("dynamic");
        Assert.assertNull(PrologHelper.query("dynamic"));
        prolog.assertToKB("dynamic");
        Assert.assertNotNull(PrologHelper.query("dynamic"));

        prolog.assertToKB("dynamic2");
        Assert.assertNotNull(PrologHelper.query("dynamic2"));
        prolog.retractFromKB("dynamic2");
        Assert.assertNull(PrologHelper.query("dynamic2"));
        prolog.assertToKB("dynamic2");
        Assert.assertNotNull(PrologHelper.query("dynamic2"));
    }

    @org.junit.Test
    public void testQueryString() throws Exception {
        Assert.assertNotNull(PrologHelper.query("solution"));

    }

    @org.junit.Test
    public void testQueryTerm() throws Exception {
        Assert.assertNotNull(PrologHelper.query(Util.textToTerm("solution")));
        Assert.assertNull(PrologHelper.query(Util.textToTerm("fake")));

    }

    @org.junit.Test
    public void testQueryStringTerm() throws Exception {
        Assert.assertNotNull(PrologHelper.query("p", Util.textToTerm("a")));

    }

    @org.junit.Test
    public void testQueryStringTerms() throws Exception {
        Variable X = new Variable("X");
        Term arg[] = { X };
        Hashtable[] answer = PrologHelper.query("p", arg);

        for(int i = 0; i<answer.length; i++){
            Term valueOfX = (Term) answer[i].get("X");
            if(i==0) {
                Assert.assertTrue(valueOfX.toString().equals("a"));
            }
            else if(i==1) {
                Assert.assertTrue(valueOfX.toString().equals("b"));
            }
        }
    }

    @org.junit.Test
    public void unLOadTest() throws Exception {
        Assert.assertNotNull(PrologHelper.query("p(a)"));
        PrologHelper.query("unload_file('foo.pl')");
        Assert.assertNull(PrologHelper.query("p(a)"));
    }
}
