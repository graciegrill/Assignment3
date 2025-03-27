import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class solution{

    public static ArrayList<String> prepareWords(String fileName){
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

    public static int binarySearch(ArrayList<String> dictionary, String target){
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

    public static String spell_Check_Version_0(String filename){
        String out = "";
        ArrayList<String> dictionary = prepareWords("dictionary.txt");
        ArrayList<String> testStrings = prepareWords("testing.txt");
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
                int found = binarySearch(dictionary, y);
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


    public static void main(String[] args) {
        System.out.println(spell_Check_Version_0("testing.txt"));
    }
}