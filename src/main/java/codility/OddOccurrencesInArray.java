package codility;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class OddOccurrencesInArray {

	public static void main(String[] args) {
		int [] a =  {9, 3, 9, 3, 9, 7, 9, 7, 5};
		System.err.println(solution(a));
	}
	
	private static int solution(int[] a){
		Map<Integer, Integer> countByNumber = new HashMap<>();
		for (int i = 0; i < a.length; i++){
			int n = a[i];
			Integer count = countByNumber.get(n);
			if (count == null){
				count = new Integer(0);
			}
			count++;
			countByNumber.put(n, count);
		}
		for (Entry<Integer, Integer> entry : countByNumber.entrySet()){
			if (entry.getValue() % 2 > 0){
				return entry.getKey();
			}
		}
		return 0;
	}

}
