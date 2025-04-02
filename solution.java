import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class solution{

    public static class Stopwatch { 

        private final long start;
    
        /**
         * Initializes a new stopwatch.
         */
        public Stopwatch() {
            start = System.currentTimeMillis();
        } 
    
    
        /**
         * Returns the elapsed CPU time (in seconds) since the stopwatch was created.
         *
         * @return elapsed CPU time (in seconds) since the stopwatch was created
         */
        public double elapsedTime() {
            long now = System.currentTimeMillis();
            return (now - start) / 1000.0;
        }
    
    } 
    /**
     * This method takes a given text file and structures the contents into an array list of strings.
     * @param fileName - string representation of the filename
     * @return - words - an ArrayList of strings
     */
    public static ArrayList<String> prepare_Words(String fileName){
        ArrayList<String> words = new ArrayList<String>();
        try{
            File data = new File(fileName);
            Scanner sophon = new Scanner(data);
            while (sophon.hasNextLine()){
                words.add(sophon.nextLine());
            }
            sophon.close();
        }
        //File error handling
        catch(FileNotFoundException x){
            System.out.println("File not found");
        }
        return words;
    }
    /**
     * This method conducts a binary search for a string target in an ArrayList dictionary.
     * It also prints out the word at which the search stopped if no match was found.
     * @param dictionary - array list of dictionary words
     * @param target - string that is the word target
     * @return - mid if word is found, and -1 if word is not found
     */
    public static int binary_Search(ArrayList<String> dictionary, String target){
        int low = 0;
        int high = dictionary.size()-1;
        int mid = -1;
        while(low<=high){
            mid = low + (high - low)/2;
            int result = dictionary.get(mid).compareTo(target);
            if(result == 0){
                return mid;
            }
            else if(result<0){
                low = mid + 1;
            }
            else{
                high = mid - 1;            
            }
        }
        //Suggest a word - wherever search stopped
        if(mid != -1) {
            System.out.println("Suggested word is " + dictionary.get(mid));
        }
        return -1;

    }
    /**
     * This is the ternary tries version of the spell checker
     * @param filename - string representation of the filename to be checked
     * @return - string with line number of incorrect words and incorrect word
     */
    public static String spell_Check_Version_1(String filename){
        String out = "";
        ArrayList<String> dictionary = prepare_Words("dictionary.txt");
        ternarySearchTree<Integer> trie = new ternarySearchTree<Integer>();
        for(int i = 0; i<dictionary.size(); i++){
            trie.put(dictionary.get(i), i);
        }
        ArrayList<String> testStrings = prepare_Words(filename);
        int lineNumber = 1;
        Stopwatch timer1 = new Stopwatch();
        for(String x:testStrings){
            x = x.toLowerCase();
            x = x.replaceAll("[^a-zA-Z\s]", " ");
            x = x.replaceAll("\\s+", " ");
            x = x.trim();
            String[] words = x.split(" ");
            for(String y:words){
                if(y.isEmpty()){
                    continue;
                }
                Integer found = trie.get(y);
                if(found == null){
                    out+=y+" ";
                    out+=lineNumber+" ";
                }
                else{
                    continue;
                }
            }
            lineNumber++;
        }
        double time1 = timer1.elapsedTime();
        System.out.println("This took "+time1+" seconds with TST");
        return out;
    }
    /**
     * This is the binary search version of the spell checker
     * @param filename - string representation of file to be checked
     * @return - string with line of incorrect word and line number
     */
    public static String spell_Check_Version_0(String filename){
        String out = "";
        ArrayList<String> dictionary = prepare_Words("dictionary.txt");
        ArrayList<String> testStrings = prepare_Words(filename);
        int lineNumber = 1;
        Stopwatch timer0 = new Stopwatch();
        for(String x:testStrings){
            //Adjust for case issues
            x = x.toLowerCase();
            //Replace anything not alphabetical with space - address possessive s
            x = x.replaceAll("[^a-zA-Z\s]", " ");
            //Replace multiple blank spaces with one
            x = x.replaceAll("\\s+", " ");
            //Trim leading and trail spaces to address new line character issues
            x = x.trim();
            String[] words = x.split(" ");
            for(String y:words){
                if(y.isEmpty()){
                    continue;
                }
                int found = binary_Search(dictionary, y);
                if(found ==-1){
                    out+=y+" ";
                    out+=lineNumber+" ";
                }
                else{
                    continue;
                }
            }
            lineNumber++;
        }
        double time0 = timer0.elapsedTime();
        System.out.println("This took "+ time0+" seconds with binary search");
        return out;
    }
    /**
     * Checks if two strings are equal and prints out interpretation of condition
     * @param s1 - string one
     * @param s2 - string two
     */
    public static void checkPrecision(String s1, String s2){
        if(s1.equals(s2)){
            System.out.println("Both versions of the spell checker found the same words");
        }
        else{
            System.out.println("Different misspelled words were identified");
        }
    }


    public static void main(String[] args) {
        String s1 = spell_Check_Version_0("testing.txt");
        System.out.println(s1);
        String s2 = spell_Check_Version_1("testing.txt");
        checkPrecision(s1, s2);
        String s3 = spell_Check_Version_0("random1000words.txt");
        String s4 = spell_Check_Version_1("random1000words.txt");
        System.out.println(s3);
        checkPrecision(s3, s4);
        String s5 = spell_Check_Version_0("test_0.txt");
        String s6 = spell_Check_Version_1("test_0.txt");
        System.out.println(s5);
        checkPrecision(s5, s6);
    }
}