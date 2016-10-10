package gscf;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class WallpaperCalculation3 {
	
	private static final String PATH = "src/main/resources/";
	private static final Charset CHARSET = Charset.forName("US-ASCII");
	
	private static final String FILE_IN = PATH + "input1.txt";
	private static final String FILE_OUT = PATH + "input1-out.txt";
	
	
	public static void main(String[] args) {
		Path inPath = Paths.get(FILE_IN);
		System.out.println("Start process: " + FILE_OUT + " input file");
		Path outPath = Paths.get(FILE_OUT);
		try (Stream<String> stream = Files.lines(inPath)) {
			RoomDimensions dimensions = stream.collect(RoomDimensions::new, RoomDimensions::accept, RoomDimensions::combine);
			Files.write(outPath, dimensions.getTotals(), CHARSET, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
			System.out.println("Output results are written to: " + FILE_OUT + " file");
		} catch (Exception e) {
			System.err.println("Unable to read file : " + FILE_IN + ". " + e.getMessage());
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
			String[] dimensionString = line.split(DELIMITER);
			int min = Integer.MAX_VALUE;
			int width = Integer.parseInt(dimensionString[0]);
			int length = Integer.parseInt(dimensionString[1]);
			int height = Integer.parseInt(dimensionString[2]);
			int wl = width * length;
			int wh = width * height;
			int lh = length * height;
			if (wl < min) {
				min = wl;
			}
			if (wh < min) {
				min = wh;
			}
			if (lh < min){
				min = lh;
			}
			
			int total = 2 * (wl + wh + lh) + min;
			System.out.println("Room " + line + ". Total wallpapers required: " + total);
			totals.add(String.valueOf(total));
		}
		
		public void combine(RoomDimensions other) {
	    }
	}
}
