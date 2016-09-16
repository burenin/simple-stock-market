import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class InterviewTask2 {

	public static void main(String[] args) {
		
		try (Scanner sc = new Scanner(System.in)) {
			String line = null;
			while (true) {
				System.out.print("Enter string: ");
				line = sc.nextLine();
				if ("DONE".equals(line)){
					System.out.println("Bye!");
					return;
				}
				System.out.println("Max number: " + getMaxNumberString(line));
			}
		}
	}
	
	private static String getMaxNumberString(String line){
		String[] intStrings = line.split("[\\s,;]+");
		List<String> numberStringList = Arrays.asList(intStrings);
		Collections.sort(numberStringList, Collections.reverseOrder(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				int length1 = o1.length();
				int length2 = o2.length();
				if (length1 > length2){
					o2 = extendString(o2, (length1 - length2));
				}else if (length1 < length2) {
					o1 = extendString(o1, (length2 - length1));
				} 
				return o1.compareTo(o2);
			}
			
			private String extendString(String string, int diff){
				char lastChar = string.charAt(string.length()-1);
				for (int i = 0; i < diff; i++) {
					string += lastChar;
				}
				return string;
			}
		}));
		StringBuilder sb = new StringBuilder();
		for (String numberString : numberStringList) {
			sb.append(numberString);
		}
		return sb.toString();
		
	}
}
