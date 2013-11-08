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

    public PrologHelper() {
        knowledgeFileName = "dynamic_knowledge.pl";
        tempFilename = "myTempFile.pl";

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
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(knowledgeFile.getAbsoluteFile(),
                    true
                    ));
           //if(getTheKnowledgeFileWithoutTisClause(clause + '.') == null) {
                System.out.println("Writes to file: " + clause + '.');
                writer.write(clause + '.');
                writer.newLine();


          // }
            writer.close();
            printFile(knowledgeFile);
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
        query("unload_file('" + knowledgeFileName + "')");
        File newFile = getTheKnowledgeFileWithoutTisClause(clause + '.');
        if (newFile != null) {
            knowledgeFile.delete();
            knowledgeFile = new File(knowledgeFileName);
            newFile.renameTo(knowledgeFile);
        }
        printFile(knowledgeFile);
        query("consult('" + knowledgeFileName + "')");
        return true;
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
                System.out.println("Skriver til fil " + currentLine);
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

            printFile(tempFile);
            return tempFile;
        }
        else {
            return null;
        }

    }
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
}
