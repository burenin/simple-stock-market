package gscf;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Handle input data - text provided by an input file. Collects words appearance statistics for a input text.
 * @author andrejs.burenins
 */
public class WordsStats {
	
	private static final String PATH = "src/main/resources/";
	private static final Charset CHARSET = Charset.forName("US-ASCII");
	
	private static final String FILE_IN = PATH + "input2.txt";
	private static final String FILE_OUT = PATH + "input2-out.txt";
	
	private static final String PUNCTUATION_DELIMETERS_RE 	= "[.,!?\\s]+";					// word delimiters (.,!? and whitespace characters			
	private static final String VALID_WORD_RE 				= "\\b\\w+?[\\-]{0,1}?\\w*?\\b";	// valid words containing alphanumeric characters or hyphen in the middle, e.g: "quasi-bird" 
	
	
	public static void main(String[] args) {
		Path inPath = Paths.get(FILE_IN);
		Path outPath = Paths.get(FILE_OUT);
		System.out.println("Start process \"" + inPath.toAbsolutePath() + "\" input file");
		try (Scanner in = new Scanner ( new File ( FILE_IN ) )) {
			WordsStats wordsStats = new WordsStats();
			List<String> stats = wordsStats.handleInput(in);
			Files.write(outPath, stats, CHARSET, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			System.out.println("Output results are written to \"" + outPath.toAbsolutePath() + "\" file");
		} catch (Exception e) {
			System.err.println("Unable to read file \"" + inPath.toAbsolutePath() + "\". " + e.getMessage());
		}
	}
	
	/**
	 * @param in {@link Scanner} initialized by input data file
	 * @return a list of word stats in form of: <word>  - <number of occurrences>. The list is sorted by by number of occurrences starting from the highest. 
	 * If two words appeared the same number of times show them in alphabetical order
	 */
	private List<String> handleInput(Scanner in){
		Map<String, Integer> stats = new HashMap<>();
		while (in.hasNext()){
			String line = in.nextLine();
			String[] words = line.split(PUNCTUATION_DELIMETERS_RE);			// split by punctuation characters and whitespace characters
			for (String word : words) {
				if (word.matches(VALID_WORD_RE)){							// ignore words containing non-alphanumeric characters except hyphen
					word = word.trim().toLowerCase();
					Integer count = stats.get(word);
					if (count == null){
						stats.put(word, 1);
					}else{
						stats.put(word, count + 1);
					}
				}
			}
		}
		final Comparator<Map.Entry<String, Integer>> byWordCounts = 
	            Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder());
	    final Comparator<Map.Entry<String, Integer>> byWordName = 
	            Comparator.comparing(Map.Entry::getKey);
	    
	    List<String> result = stats
	    		.entrySet()
	    		.stream()
	    		.sorted(byWordCounts.thenComparing(byWordName))
	    		.map(e -> {
	    						return new String(e.getKey() + " - " + e.getValue() );
	    					})
	    		.collect(Collectors.toList());
	    
	    return result;
	}
	
}
