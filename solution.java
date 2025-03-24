import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class solution{


    public static ArrayList<String> prepareDictionary(String fileName){
        ArrayList<String> dictionary = new ArrayList<String>();
        //Read dictionary words into a linear data structure (ArrayList)
        try{
            File data = new File(fileName);
            Scanner sophon = new Scanner(data);
            while (sophon.hasNextLine()){
                dictionary.add(sophon.nextLine());
            }
            sophon.close();
        }
        //File error handling
        catch(FileNotFoundException x){
            System.out.println("File not found");
        }
        //Ensure dictionary actually sorted
        Collections.sort(dictionary);
        return dictionary;
    }

    public static ArrayList<String> prepareTesting(String fileName){
        ArrayList<String> testing = new ArrayList<String>();
        try{
            File data = new File(fileName);
            Scanner sophon = new Scanner(data);
            while (sophon.hasNextLine()){
                testing.add(sophon.nextLine());
            }
            sophon.close();
        }
        //File error handling
        catch(FileNotFoundException x){
            System.out.println("File not found");
        }
        return testing;
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

    public static String organizedSearch(String filename){
        String out = "";
        ArrayList<String> dictionary = prepareDictionary("dictionary.txt");
        ArrayList<String> testStrings = prepareTesting("testing.txt");
        for(String x:testStrings){
            x = x.toLowerCase();
            x = x.replaceAll("[^a-zA-Z\s]", "");
            x = x.replaceAll("\\s+", " ");
            x = x.trim();
            String[] words = x.split(" ");
            for(String y:words){
                int found = binarySearch(dictionary, y);
                if(found ==-1){
                    out+=" "+y;
                }
                else{
                    continue;
                }
            }
        }
        return out;
    }


    public static void main(String[] args) {
        System.out.println(organizedSearch("testing.txt"));
    }
}