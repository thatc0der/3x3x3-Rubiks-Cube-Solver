import java.util.Arrays;

public class lowestm8 {

	public static void main(String[] args) {
		
		int[] allInts = {23,130,4040,12,40,309,49,2,7,92,213,1,41,43};
		
		int[] lowest8 = findLowest8(allInts);
		System.out.println(Arrays.toString(lowest8));
		
	}
	
	
	public static int [] findLowest8(int arr[]){
        int [] lowest8 = new int[8];
        Arrays.fill(lowest8, Integer.MAX_VALUE);
        
        for(int i : arr){
            if(i < lowest8[7]){
                lowest8[7] = i;
                Arrays.sort(lowest8);
            }
        }    
        return lowest8;
    }
	
}
