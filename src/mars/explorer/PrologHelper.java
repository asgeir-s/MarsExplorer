package mars.explorer;

import jpl.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Creator: asgeir
 * Date: 08.11.13, 17:18
 */
public class PrologHelper{

    static File knowledgeFile = newFile("knowledge/dynamic_knowledge.pl");
    static File tempFile = newFile("knowledge/TempFile.pl");

    public static File newFile(String filePath) {
        File file = new File(filePath);
        file.delete();
        file = new File(filePath);

        try {
            file.createNewFile();
        }  catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * assert a clause to the knowledge base. Use Prolog Syntax without .
     *
     *        Example:
     *          assertToKB("on(a,b)")
     * @param clause
     * @return true if clause is added to knowledgeFile or are present in the knowledgeFile from before
     */
    public static boolean assertToKB(String clause) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(knowledgeFile.getAbsoluteFile(),
                    true
                    ));
           if(getTheKnowledgeFileWithoutTisClause(knowledgeFile, tempFile,
                   clause + '.') == null) {
                writer.write(clause + '.');
                writer.newLine();


           }
            writer.close();
        } catch (IOException e) {
            return false;
        }

        query("consult('" + knowledgeFile.getPath() + "')");
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
    public static boolean retractFromKB(String clause) {
        query("unload_file('" + knowledgeFile.getPath() + "')");
        File newFile = getTheKnowledgeFileWithoutTisClause(knowledgeFile, tempFile, clause + '.');
        if (newFile != null) {
            knowledgeFile.delete();
            knowledgeFile = new File(knowledgeFile.getPath());
            newFile.renameTo(knowledgeFile);
        }
        query("consult('" + knowledgeFile.getPath() + "')");
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
    private static File getTheKnowledgeFileWithoutTisClause(File knowledgeFile, File tempFile,
                                                     String linetoRemove) {

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


    public static ArrayList<ArrayList<String>> hashtableTableToArraylist(Hashtable[] hw){
        ArrayList<ArrayList<String>> newArray = new ArrayList<ArrayList<String>>();
        for(int i = 0; i< hw.length; i++) {
            newArray.add(i,  new ArrayList<String>(hw[i].values()));
        }
        return newArray;
    }
}
