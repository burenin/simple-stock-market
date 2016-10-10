package gscf;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author andrejs.burenins
 *
 */
public class WallpaperCalculation {
	
	private static final String PATH = "src/main/resources/";
	private static final Charset CHARSET = Charset.forName("US-ASCII");
	
//	private static final String FILE_IN = PATH + "input1-example.txt";
	private static final String FILE_IN = PATH + "input1.txt";
	
//	private static final String FILE_OUT = PATH + "input1-example-out.txt";
	private static final String FILE_OUT = PATH + "input1-out.txt";
	
	
	public static void main(String[] args) {
		Path inPath = Paths.get(FILE_IN);
		Path outPath = Paths.get(FILE_OUT);
		System.out.println("Start process \"" + inPath.toAbsolutePath() + "\" input file");
		// use Java 8 Stream API
		try (Stream<String> stream = Files.lines(inPath)) {
			RoomDimensions dimensions = stream.collect(RoomDimensions::new, RoomDimensions::accept, RoomDimensions::combine);
			Files.write(outPath, dimensions.getTotals(), CHARSET, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			System.out.println("Output results are written to \"" + outPath.toAbsolutePath() + "\" file");
		} catch (Exception e) {
			System.err.println("Unable to read file \"" + inPath.toAbsolutePath() + "\". " + e.getMessage());
		}
	}
	
	private static class RoomDimensions implements Consumer<String> {
		
		private final static String DELIMITER = "x";
		
		private List<String> totals = new ArrayList<>();
		
	    public List<String> getTotals() {
	        return totals;
	    }

		@Override
		public void accept(String line) {
			String[] dimensions = line.split(DELIMITER);
			int[] size = Arrays.stream(dimensions).map(String::trim).mapToInt(Integer::parseInt).toArray();
			Arrays.sort(size);
			int min = size[0]*size[1];
			int total = 2 * (min + size[0]*size[2] + size[1]*size[2]) + min;
			System.out.println("Room " + line + ". Total wallpapers required: " + total);
			totals.add(String.valueOf(total));
		}
		
		public void combine(RoomDimensions other) {
	    }
	}
}
