import java.util.Arrays;

public class ColorAndIndex implements Comparable<ColorAndIndex>{

	public double[] labArray;
	public int index;
	public double distance;
	public double [] allDistances;
	
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
	
	public int getIndex() { return index ;}
	
	public double []getColor() { return labArray; }
	
	public String toString(){
		return "Distance " + distance + " | Color " + Arrays.toString(labArray) + " | index: " + index + "\n";
	}
	
	
}
