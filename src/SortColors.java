import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class SortColors implements Comparable< SortColors> {
	

	   public int  x, y;
	   public Color color;

	   @Override
	   public int compareTo(SortColors other) {
	      if (null==other) throw new NullPointerException();
	      // implement the logic, like:
	      // this will compare X, unless difference in Y is more than EG 10
	      return Math.abs(y - other.y) > 10 ? y - other.y : x - other.x;
	   }   
	   
	   
	   public Color getColor() {return color;}
	   
	   /*@Override
	   public String toString(){
		   return color.toString();
	   }
	   */
	   
	   public List<SortColors> colorsOfclass = new ArrayList<>();

}