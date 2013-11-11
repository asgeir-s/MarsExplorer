package test.prolog;

import jpl.Term;
import jpl.Util;
import jpl.Variable;
import junit.framework.Assert;
import mars.explorer.PrologHelper;
import org.junit.Before;

import java.io.File;
import java.util.Hashtable;

/**
 * Creator: asgeir
 * Date: 08.11.13, 18:40
 */
public class PrologHelperTest {
    String name;
    File knowledgeFile;
    File tempFile;
    @Before
    public void setUp() {
        name = "test";
        PrologHelper.query("['foo.pl']");
    }

    @org.junit.Test
    public void testAssertToKB() throws Exception {
        Assert.assertNull(PrologHelper.query("dyn(a)"));
        Assert.assertNull(PrologHelper.query("hasRock(" + name + ")"));

        PrologHelper.assertToKB("dyn(" + name + ", a)");
        PrologHelper.assertToKB("dyn(" + name + ", b)");
        PrologHelper.assertToKB("dyn(" + name + ", c)");
        PrologHelper.assertToKB("dyn(" + name + ", d)");
        PrologHelper.assertToKB("hasRock(" + name + ")");

        Assert.assertNotNull(PrologHelper.query("dyn(" + name + ", d)"));
        PrologHelper.retractFromKB("dyn(" + name + ", b)");
        Assert.assertNull(PrologHelper.query("dyn(b)"));

        Assert.assertNotNull(PrologHelper.query("dyn(" + name + ", a)"));
        Assert.assertNotNull(PrologHelper.query("dyn(" + name + ", c)"));
        Assert.assertNotNull(PrologHelper.query("hasRock(" + name + ")"));
        Assert.assertNull(PrologHelper.query("fake"));
    }

    @org.junit.Test
    public void testRetractFromKB() throws Exception {
        PrologHelper.assertToKB("dynamicX(" + name + ")");
        Assert.assertNotNull(PrologHelper.query("dynamicX(" + name + ")"));
        PrologHelper.retractFromKB("dynamicX(" + name + ")");
        Assert.assertNull(PrologHelper.query("dynamicX(" + name + ")"));
        PrologHelper.assertToKB("dynamicX(" + name + ")");
        Assert.assertNotNull(PrologHelper.query("dynamicX(" + name + ")"));

        PrologHelper.assertToKB("dynamicX(" + name + ")");
        Assert.assertNotNull(PrologHelper.query("dynamicX(" + name + ")"));
        PrologHelper.retractFromKB("dynamicX(" + name + ")");
        Assert.assertNull(PrologHelper.query("dynamicX(" + name + ")"));
        PrologHelper.assertToKB("dynamicX(" + name + ")");
        Assert.assertNotNull(PrologHelper.query("dynamicX(" + name + ")"));
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
