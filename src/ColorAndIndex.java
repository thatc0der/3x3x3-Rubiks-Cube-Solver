public class ColorAndIndex implements Comparable<ColorAndIndex>{

	public double[] labArray;
	public int index;
	public double distance;
	public double [] allDistances;
	public String colorString;
	public int numberRepresentation;
	
	
	
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
	
	public String getString(){ return colorString; };
	
	/* "Distance " + distance + " | Color " + Arrays.toString(labArray) +*/
	public String toString(){
		return "Indx: " + index + " | Color: " + colorString + " | Num: "+ numberRepresentation + "\n";
	}
	
	
}
