package gscf;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class A {
	
	
	private static final String PATH = "src/main/resources/";

	
//	private static final String FILE_IN = PATH + "a.in";
//	private static final String FILE_OUT = PATH + "a.out";
	
	private static final String FILE_IN = PATH + "A-large-practice.in";
	private static final String FILE_OUT = PATH + "A-large-practice.out";
	
	private long allStart;
	
	private static final String OUT_FORMAT = "Case #%d: %s";
	
	private static final String FW = "Fegla Won";
	
	
	private SortedMap<Integer, String> outputLines = Collections.synchronizedSortedMap(new TreeMap<Integer, String>());

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 5, TimeUnit.DAYS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    });
	
	
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner ( new File ( FILE_IN ) );
		
		int testCount = in.nextInt();
		List<InputData> inputDataList = new ArrayList<>();
		for ( int test = 0; test < testCount; ++test ) {
			InputData inputData = new InputData(in);
			inputDataList.add(inputData);
		}
		in.close();
		A a = new A();
		a.run(inputDataList);
	}
	
	
	private void run(List<InputData> inputDataList) throws Exception {
		allStart = System.currentTimeMillis();
		
		Path outPath = Paths.get(FILE_OUT);
		Charset charset = Charset.forName("US-ASCII");

		List<Future<?>> tasks = new ArrayList<>(inputDataList.size());
		int i = 0;
		for (final InputData inputData : inputDataList){
			final int fi = i++;
			tasks.add(executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        runCase((fi+1), inputData);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }));
		}
		for (Future<?> task : tasks) {
            task.get();
        }
		long allEnd = System.currentTimeMillis();
		
		System.out.printf ( "%dms\n", allEnd-allStart );
		Files.write(outPath, outputLines.values(), charset, StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
	}

	 public void runCase(int caseNumber, InputData inputData) {
		 if (!inputData.isPossible()){
			 outputLines.put(caseNumber, String.format(Locale.ENGLISH, OUT_FORMAT, caseNumber, FW));
		 }else{
			 int n = inputData.n;
			 List<String> data = inputData.data;

			 Map<Integer, Map<Integer, Integer>> count = new HashMap<>();
			 for (String string : data){
				 Map<Integer, Integer> substringsCount = new HashMap<>();
				 int i = 0;
				 int sequence = 0;
				 char c = string.charAt(0);
				 int charCount = 0;
				 while (i < string.length()){
					 char charAtI = string.charAt(i);
					 if (charAtI == c){
						 charCount++;
						 i++;
						 if (i == string.length()){
							 substringsCount.put(sequence, charCount);
						 }
					 }else{
						 substringsCount.put(sequence, charCount);
						 sequence++;
						 c = charAtI;
						 charCount = 0;
					 }
					 
				 }
				 for (Map.Entry<Integer, Integer> entry : substringsCount.entrySet()){
					 int stringIndex = entry.getKey();
					 int charsCount = entry.getValue();
					 Map<Integer, Integer> maxCharsCount = count.get(stringIndex);
					 if (maxCharsCount == null){
						 maxCharsCount = new HashMap<>();
						 count.put(stringIndex, maxCharsCount);
					 }
					 Integer countCount = maxCharsCount.get(charsCount);
					 if (countCount == null){
						 countCount = 0;
					 }
					 countCount++;
					 maxCharsCount.put(charsCount, countCount);
				 }
			 }
			 int result = 0;
			 for (Map.Entry<Integer, Map<Integer, Integer>> entry : count.entrySet()){
				 int min = Integer.MAX_VALUE;
				 Map<Integer, Integer> stringCount = entry.getValue();
				 for (Map.Entry<Integer, Integer> entrySet : stringCount.entrySet()){
					 int size = entrySet.getKey();
					 int actions = 0;
					 for (Map.Entry<Integer, Integer> entrySet2 : stringCount.entrySet()){
						 int size2 = entrySet2.getKey();
						 int charCount2 = entrySet2.getValue();
						 actions += Math.abs(size-size2) * charCount2;
					 }
					 if (actions < min){
						 min = actions;
					 }
				 }
				 result += min;
			 }
			 outputLines.put(caseNumber, String.format(Locale.ENGLISH, OUT_FORMAT, caseNumber, result));
			 
		 }
	 }

	private static class InputData {
		
		private int n;
		private List<String> data;
		private Set<String> normalize;
		
		public InputData(Scanner in){
			data = new ArrayList<>();
			n = in.nextInt();
			for (int i = 0; i < n; i++){
				data.add(in.next());
			}
			// normilize
			normalize = new HashSet<>(data.size());
			for (String string : data){
				StringBuilder sb = new StringBuilder();
				char previous = 0;
				for (int i = 0; i < string.length(); i++){
					char c = string.charAt(i);
					if (previous != c){
						previous = c;
						sb.append(c);
					}
				}
				normalize.add(sb.toString());
			}
		}
		
		public boolean isPossible(){
			return normalize.size() == 1;
		}

		@Override
		public String toString() {
			return "InputData [n=" + n + ", data=" + data + "]";
		}
	}
}








