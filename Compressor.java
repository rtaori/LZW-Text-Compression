import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		StringBuilder end = new StringBuilder(y);
		for (int k : compressed) {
			String entry = dictionary.containsKey(k) ? dictionary.get(k) : y + y.charAt(0);
			dictionary.put(dictionary.size(), y + entry.charAt(0));
			y = entry;
			end.append(entry);
		}

		return end.toString();
	}
	
	public static String readWords(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		StringBuilder text = new StringBuilder();
		while(reader.hasNextLine())
			text.append(reader.nextLine());
		reader.close();
		return text.toString();
	}
	
	public static void writeCompressed(ArrayList<Integer> compressed, String file) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		for(int x : compressed) {
			writer.println(x);
		}
		writer.close();
	}
	
	public static ArrayList<Integer> readCompressed(String file) throws FileNotFoundException {
		Scanner reader = new Scanner(new File(file));
		ArrayList<Integer> compressed = new ArrayList<>();
		while(reader.hasNextInt())
			compressed.add(reader.nextInt());
		reader.close();
		return compressed;
	}
	
	public static void writeWords(String text, String file) throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
		writer.println(text);
		writer.close();
	}

//	public static void main(String[] args) {
//		try {
//			String text = readWords("inputWords2.txt");
//			ArrayList<Integer> compressed = compress(text);
//			writeCompressed(compressed, "outputCompressed.txt");
//			ArrayList<Integer> outFile = readCompressed("outputCompressed.txt");
//			String words = decompress(outFile);
//			writeWords(words, "outputWords.txt");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
}