package codility;

public class BinaryGap {
	
	public int solution(int n) {
		int result = 0;
		String s = Integer.toBinaryString(n);
		int start = s.indexOf('1');
		//1000010001
		if (start >= 0){
			int end = s.lastIndexOf('1');
			if (end > 0 && start < end) {
				s = s.substring(start+1, end);
				int count = 0;
				for (int i = 0; i < s.length(); i++){
					if (s.charAt(i) == '1'){
						result = Math.max(count, result);
						count = 0;
					} else{
						count++;
						if (i == (s.length()-1)){
							result = Math.max(count, result);
						}
					}
				}
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		BinaryGap binaryGap = new BinaryGap();
		System.err.println(binaryGap.solution(0));	// 0
		System.err.println(binaryGap.solution(1));	// 1
		System.err.println(binaryGap.solution(9));	// 1001
		System.err.println(binaryGap.solution(529));// 1000010001 
		System.err.println(binaryGap.solution(20));	// 10100
		System.err.println(binaryGap.solution(15));	// 1111
	}

}
