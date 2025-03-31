import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class solution{
    /**
     * 
     * @param fileName
     * @return
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

    public static int binary_Search(ArrayList<String> dictionary, String target){
        int low = 0;
        int high = dictionary.size()-1;
        while(low<=high){
            int mid = low + (high - low)/2;
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
        return -1;

    }

    public static String spell_Check_Version_1(String filename){
        String out = "";
        ArrayList<String> dictionary = prepare_Words("dictionary.txt");
        ternarySearchTree<Integer> trie = new ternarySearchTree<Integer>();
        for(int i = 0; i<dictionary.size(); i++){
            trie.put(dictionary.get(i), i);
        }
        ArrayList<String> testStrings = prepare_Words(filename);
        int lineNumber = 1;
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
        return out;
    }

    public static String spell_Check_Version_0(String filename){
        String out = "";
        ArrayList<String> dictionary = prepare_Words("dictionary.txt");
        ArrayList<String> testStrings = prepare_Words(filename);
        int lineNumber = 1;
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
        return out;
    }
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
        /**System.out.println(spell_Check_Version_0("random1000words.txt"));
        System.out.println(spell_Check_Version_1("random1000words.txt"));
        System.out.println(spell_Check_Version_0("test_0.txt"));
        System.out.println(spell_Check_Version_1("test_0.txt"));**/
    }
}