import java.util.Arrays;

public class ColorAndIndex implements Comparable<ColorAndIndex>{

	public double[] labArray;
	public int index;
	public double distance;
	public double[][] lowestDistances;
	
	/*public ColorAndIndex(){
		this.index = index;
		this.labArray = labArray;
		this.distance = distance;
		this.lowestDistances = lowestDistances;
	}*/

	@Override
	public int compareTo(ColorAndIndex indexToCompare) {
		
		
		return (int) (this.distance - indexToCompare.distance); 
	}
	
	
	public String toString(){
		return "Distance: " + distance + " : Color" + Arrays.toString(labArray) + "\n";
	}
	
	
}
