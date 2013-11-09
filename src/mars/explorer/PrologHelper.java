package mars.explorer;

import jpl.*;

import java.io.*;
import java.util.Hashtable;

/**
 * Creator: asgeir
 * Date: 08.11.13, 17:18
 */
public class PrologHelper{

    File knowledgeFile;
    File tempFile;

    String knowledgeFileName;
    String tempFilename;

    String identified;

    public PrologHelper(String identifiedInn) {
        this.identified = identifiedInn.toLowerCase();
        knowledgeFileName = "knowledge/" + identified + "_dynamic_knowledge.pl";
        tempFilename = "knowledge/" + identified + "_TempFile.pl";

        tempFile = new File(tempFilename);
        knowledgeFile = new File(knowledgeFileName);

        tempFile.delete();
        knowledgeFile.delete();

        tempFile = new File(tempFilename);
        knowledgeFile = new File(knowledgeFileName);

        try {
            knowledgeFile.createNewFile();
            tempFile.createNewFile();
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * assert a clause to the knowledge base. Use Prolog Syntax without .
     *
     *        Example:
     *          assertToKB("on(a,b)")
     * @param clause
     * @return true if clause is added to knowledgeFile or are present in the knowledgeFile from before
     */
    public boolean assertToKB(String clause) {
        clause = identified + clause;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(knowledgeFile.getAbsoluteFile(),
                    true
                    ));
           if(getTheKnowledgeFileWithoutTisClause(clause + '.') == null) {
                writer.write(clause + '.');
                writer.newLine();


           }
            writer.close();
        } catch (IOException e) {
            return false;
        }

        query("consult('" + knowledgeFileName + "')");
        return true;
    }

    /**
     * retract a clause to the knowledge base. Use Prolog Syntax without .
     *
     *        Example:
     *          retractToKB("on(a,b)")
     * @param clause
     * @return true if clause was removed or not pressent in knowledgeFile
     */
    public boolean retractFromKB(String clause) {
        clause = identified + clause;
        query("unload_file('" + knowledgeFileName + "')");
        File newFile = getTheKnowledgeFileWithoutTisClause(clause + '.');
        if (newFile != null) {
            knowledgeFile.delete();
            knowledgeFile = new File(knowledgeFileName);
            newFile.renameTo(knowledgeFile);
        }
        query("consult('" + knowledgeFileName + "')");
        return true;
    }

    /**
     * Used for agent specific knowledge
     * @param inQuery
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public Hashtable[] queryIndividual(String inQuery) {
        inQuery = identified + inQuery;
        Query q = new Query(inQuery);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
                return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     * Used for agent specific knowledge
     * @param s
     * @param terms
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public Hashtable[] queryIndividual(String s, Term[] terms) {
        s = identified + s;
        Query q = new Query(s, terms);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
                return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     * Used for agent specific knowledge
     * @param s
     * @param term
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public Hashtable[] queryIndividual(String s, Term term) {
        s = identified + s;
        Query q = new Query(s, term);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
                return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     *
     * @param inQuery
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public static Hashtable[] query(String inQuery) {
        Query q = new Query(inQuery);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
            return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     *
     * @param inTerm
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public static Hashtable[] query(Term inTerm) {
        Query q = new Query(inTerm);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
                return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     *
     * @param s
     * @param terms
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public static Hashtable[] query(String s, Term[] terms) {
        Query q = new Query(s, terms);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
                return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     *
     * @param s
     * @param term
     * @return hash-table with all solutions if query has solution, null otherwise
     */
    public static Hashtable[] query(String s, Term term) {
        Query q = new Query(s, term);
        try {
            Hashtable[] result = q.allSolutions();
            if(result.length >0) {
                return result;
            }
            else {
                return null;
            }
        }
        catch (PrologException e) {
            return null;
        }
    }

    /**
     *
     * @param linetoRemove
     * @return a new file with the same content as the knowledgeFile, except without the 'line',
     * or null if 'line' is not in knowledgeFile
     */
    private File getTheKnowledgeFileWithoutTisClause(String linetoRemove) {

        BufferedWriter writer = null;
        Boolean foundLine = false;
        try {
            writer = new BufferedWriter(new FileWriter(tempFile.getAbsoluteFile()));

        String currentLine;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(knowledgeFile.getAbsoluteFile()));
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(linetoRemove)) {
                    foundLine=true;
                    continue;
                }
                writer.write(currentLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(foundLine) {
            return tempFile;
        }
        else {
            return null;
        }

    }

    /**
     * Print a file (debugging)
     * @param file
     */
    public void printFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file.getAbsoluteFile()));

            String currentLine;
            System.out.println("FILE: " + file.getName() + " has content:");
            while((currentLine = reader.readLine()) != null) {
                System.out.println(currentLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Should be called before end
     */
    public void deleteFiles() {
        tempFile.delete();
        knowledgeFile.delete();
    }
}
