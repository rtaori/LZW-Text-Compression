import java.util.*;
 
public class Compressor {
	
	public static int BASE_DICT = 256;

    public static ArrayList<Integer> compress(String toCompress) {
        HashMap<String,Integer> dictionary = new HashMap<>();
        for (int x = 0; x < BASE_DICT; x++)
            dictionary.put("" + (char)x, x);
 
        String r = "";
        ArrayList<Integer> result = new ArrayList<>();
        for (char c : toCompress.toCharArray()) {
            String rc = r + c;
            if (dictionary.containsKey(rc))
                r = rc;
            else {
                result.add(dictionary.get(r));
                dictionary.put(rc, dictionary.size());
                r = "" + c;
            }
        }
        result.add(dictionary.get(r));
        
        return result;
    }
 
    public static String decompress(ArrayList<Integer> compressed) {
        HashMap<Integer,String> dictionary = new HashMap<>();
        for (int x = 0; x < BASE_DICT; x++)
            dictionary.put(x, "" + (char)x);
 
        String y = "" + (char)(int)compressed.remove(0);
        String end = y;
        for (int k : compressed) {
        	String entry = dictionary.containsKey(k) ? dictionary.get(k) : y + y.charAt(0);
            dictionary.put(dictionary.size(), y + entry.charAt(0));
            y = entry;
            end += entry;
        }
        
        return end;
    }
 
    public static void main(String[] args) {
        ArrayList<Integer> compressed = compress("ababababab");
        System.out.println(compressed);
        String decompressed = decompress(compressed);
        System.out.println(decompressed);
        System.out.println((char)500);
    }
}