import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class InterviewTask1 {
	
	private static Map<Character, Character> closeOpenBrackets = new HashMap<>();
	static {
		closeOpenBrackets.put(')', '(');
		closeOpenBrackets.put('}', '{');
		closeOpenBrackets.put(']', '[');
	}
	
	
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
				System.out.println("Is Valid: " + isValid(line));
			}
		}
	}
	
	private static boolean isValid(String line){
		
		
		Stack<Character> stack = new Stack<>();
		
		for (char c : line.toCharArray()) {
			if (closeOpenBrackets.containsKey(c)){		// close bracket
				char openChar = closeOpenBrackets.get(c);
				if (stack.isEmpty() || (openChar != stack.pop())) {
					return false;
				}
			}else if (closeOpenBrackets.containsValue(c)){ // open bracket
				stack.push(c);
			}
		}
		return stack.isEmpty();
	}
}
