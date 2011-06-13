import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class is responsible for all use of the shortest path algorithm
 * 
 * @author Kenni, Anders, Nicolai
 * 
 */
public class Algorithm {

	// Variable
	static ArrayList<Intersection> mapList = new ArrayList<Intersection>(); // HENT
	static ArrayList<Intersection> mapX = new ArrayList<Intersection>();
	static ArrayList<Intersection> mapY = new ArrayList<Intersection>();

	static MapDAO mapDAO = new MapDAO();

	public static void main(String[] args) {
		mapList = mapDAO.getMap();
		mapX = mapDAO.getMapXsorted();
		mapY = mapDAO.getMapYsorted();
		
		int amount = 5000;
		
		int ID = 0;
		String coord = "0503,1785";

		System.out.println("Coordinate: " + coord);
		
		long ts = 0;

		ts = System.currentTimeMillis();
		for (int i = 0; i < amount; i++) {
			ID = findClosestPoint(coord);
		}
		long option1 = System.currentTimeMillis() - ts;

		ts = System.currentTimeMillis();
		for (int i = 0; i < amount; i++) {
			ID = findClosestPoint2(coord);
		}
		long option2 = System.currentTimeMillis() - ts;
		
		ts = System.currentTimeMillis();
		for (int i = 0; i < amount; i++) {
			ID = findClosestPoint3(coord);
		}
		long option3 = System.currentTimeMillis() - ts;
		
		/*
		ts = System.currentTimeMillis();
		for (int i = 0; i < amount; i++) {
			ID = findClosestPoint4(coord);
		}
		long option4 = System.currentTimeMillis() - ts;
		*/
		
		System.out.println("\nOption 1:");
		System.out.println(option1 + "ms.");
		System.out.println("Found: " + ID);
		System.out.println("- compared to 2: " + (1.0*option1/option2));
		System.out.println("- compared to 3: " + (1.0*option1/option3));
		
		System.out.println("\nOption 2:");
		System.out.println(option2 + "ms.");
		System.out.println("Found: " + ID);
		System.out.println("- compared to 1: " + (1.0*option2/option1));
		System.out.println("- compared to 3: " + (1.0*option2/option3));
		
		System.out.println("\nOption 3:");
		System.out.println(option3 + "ms.");
		System.out.println("Found: " + ID);
		System.out.println("- compared to 1: " + (1.0*option3/option1));
		System.out.println("- compared to 2: " + (1.0*option3/option2));
		
		/*
		System.out.println("Option 4:");
		System.out.println(option4 + "ms.");
		System.out.println("Found: " + ID);
		System.out.println("- compared to 1: " + (1.0*option4/option1)*100 + "%");
		System.out.println("- compared to 2: " + (1.0*option4/option2)*100 + "%");
		System.out.println("- compared to 3: " + (1.0*option4/option3)*100 + "%");
		*/
	}

	/**
	 * 
	 * Finds the intersection closest to a coordinate This is done using
	 * pythagoras
	 * 
	 * @param coordinate
	 * @return Intersection ID of the closest intersection
	 */
	public static int findClosestPoint(String coordinate) {
		int thisX = Integer.parseInt(coordinate.substring(0, 4));
		int thisY = Integer.parseInt(coordinate.substring(5, 9));
		int nodeID = 9999;
		double close = 9999.9;
		double tempValue, xLength, yLength;

		for (int i = 0; i < mapList.size(); i++) {
			xLength = Math.abs(thisX - mapList.get(i).getXCoord());
			yLength = Math.abs(thisY - mapList.get(i).getYCoord());

			tempValue = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));

			if (tempValue < close) {
				close = tempValue;
				nodeID = mapList.get(i).getID();
			}
		}

		return nodeID;

	}

	public static int findClosestPoint2(String coordinate) {

		int thisX = Integer.parseInt(coordinate.substring(0, 4));
		int thisY = Integer.parseInt(coordinate.substring(5, 9));
		int nodeID = 9999;
		double close = 9999.9;
		double tempValue, xLength, yLength;
		
		int current = mapX.size() / 2;
		int start = 0;
		int end = mapX.size() - 1;
		
		while (!(mapX.get(current).getXCoord() >= (thisX-50) && mapX.get(current-1).getXCoord() < (thisX-50))) {
			
			if (mapX.get(current).getXCoord() < (thisX-50)) {
				start = current;
			} else if (mapX.get(current).getXCoord() >= (thisX-50) && mapX.get(current-1).getXCoord() >= (thisX-50)) {
				end = current;
			}

			current = ((end - start) / 2) + start;
		}

		int minX = current;
		
		current = mapX.size() / 2;
		start = 0;
		end = mapX.size() - 1;
		
		while (!(mapX.get(current).getXCoord() <= (thisX+50) && mapX.get(current+1).getXCoord() > (thisX+50))) {
			
			if (mapX.get(current).getXCoord() <= (thisX+50) && mapX.get(current+1).getXCoord() <= (thisX+50)) {
				start = current;
			} else if (mapX.get(current).getXCoord() > (thisX+50)) {
				end = current;
			}

			current = ((end - start) / 2) + start;
		}
		
		int maxX = current;
		
		List<Intersection> tempMapList = mapX.subList(minX, maxX);
		
		for (int i = 0; i < tempMapList.size(); i++) {
			xLength = Math.abs(thisX - tempMapList.get(i).getXCoord());
			yLength = Math.abs(thisY - tempMapList.get(i).getYCoord());

			tempValue = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));

			if (tempValue < close) {
				close = tempValue;
				nodeID = tempMapList.get(i).getID();
			}
		}
		
		return nodeID;
	}
	
	public static int findClosestPoint3(String coordinate) {

		int thisX = Integer.parseInt(coordinate.substring(0, 4));
		int thisY = Integer.parseInt(coordinate.substring(5, 9));
		int nodeID = 9999;
		double close = 9999.9;
		double tempValue, xLength, yLength;
		
		int current = mapX.size() / 2;
		int start = 0;
		int end = mapX.size() - 1;
		
		while (!(mapX.get(current).getXCoord() >= (thisX-50) && mapX.get(current-1).getXCoord() < (thisX-50))) {
			
			if (mapX.get(current).getXCoord() < (thisX-50)) {
				start = current;
			} else if (mapX.get(current).getXCoord() >= (thisX-50) && mapX.get(current-1).getXCoord() >= (thisX-50)) {
				end = current;
			}

			current = ((end - start) / 2) + start;
		}

		int minX = current;
		
		current = mapX.size() / 2;
		start = 0;
		end = mapX.size() - 1;
		
		while (!(mapX.get(current).getXCoord() <= (thisX+50) && mapX.get(current+1).getXCoord() > (thisX+50))) {
			
			if (mapX.get(current).getXCoord() <= (thisX+50) && mapX.get(current+1).getXCoord() <= (thisX+50)) {
				start = current;
			} else if (mapX.get(current).getXCoord() > (thisX+50)) {
				end = current;
			}

			current = ((end - start) / 2) + start;
		}
		
		int maxX = current;
		
		List<Intersection> tempXMapList = mapX.subList(minX, maxX);
		
		current = mapY.size() / 2;
		start = 0;
		end = mapY.size() - 1;
		
		while (!(mapY.get(current).getYCoord() >= (thisY-50) && mapY.get(current-1).getYCoord() < (thisY-50))) {
			
			if (mapY.get(current).getYCoord() < (thisY-50)) {
				start = current;
			} else if (mapY.get(current).getYCoord() >= (thisY-50) && mapY.get(current-1).getYCoord() >= (thisY-50)) {
				end = current;
			}

			current = ((end - start) / 2) + start;
		}

		int minY = current;
		
		current = mapY.size() / 2;
		start = 0;
		end = mapY.size() - 1;
		
		while (!(mapY.get(current).getYCoord() <= (thisY+50) && mapY.get(current+1).getYCoord() > (thisY+50))) {
			
			if (mapY.get(current).getYCoord() <= (thisY+50) && mapY.get(current+1).getYCoord() <= (thisY+50)) {
				start = current;
			} else if (mapY.get(current).getYCoord() > (thisY+50)) {
				end = current;
			}

			current = ((end - start) / 2) + start;
		}
		
		int maxY = current;
		
		List<Intersection> tempYMapList = mapY.subList(minY, maxY);
		
		ArrayList<Intersection> tempMapList = new ArrayList<Intersection>();
		
		for(int i=0; i<tempXMapList.size(); i++) {
			for (int k=0; k<tempYMapList.size(); k++) {
				if(tempXMapList.get(i).getID() == tempYMapList.get(k).getID()) {
					tempMapList.add(tempXMapList.get(i));
				}
			}
		}
		
		/*
		System.out.println("X: " + tempXMapList.size());
		System.out.println("Y: " + tempYMapList.size());
		System.out.println("FINAL: " + tempMapList.size());
		*/
		
		for (int i = 0; i < tempMapList.size(); i++) {
			xLength = Math.abs(thisX - tempMapList.get(i).getXCoord());
			yLength = Math.abs(thisY - tempMapList.get(i).getYCoord());

			//System.out.println(tempMapList.get(i).getXCoord() + "," + tempMapList.get(i).getYCoord());
			
			tempValue = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));

			if (tempValue < close) {
				close = tempValue;
				nodeID = tempMapList.get(i).getID();
			}
		}
		
		return nodeID;
	}
	
	public static int findClosestPoint4(String coordinate) {

		int thisX = Integer.parseInt(coordinate.substring(0, 4));
		int thisY = Integer.parseInt(coordinate.substring(5, 9));
		int nodeID = 9999;
		double close = 9999.9;
		double tempValue, xLength, yLength;
		
		ArrayList<Intersection> tempMapList = mapDAO.getFinalMap(coordinate);

		//System.out.println("FINAL: " + tempMapList.size());

		for (int i = 0; i < tempMapList.size(); i++) {
			xLength = Math.abs(thisX - tempMapList.get(i).getXCoord());
			yLength = Math.abs(thisY - tempMapList.get(i).getYCoord());

			//System.out.println(tempMapList.get(i).getXCoord() + "," + tempMapList.get(i).getYCoord());
			
			tempValue = Math.sqrt(Math.pow(xLength, 2) + Math.pow(yLength, 2));

			if (tempValue < close) {
				close = tempValue;
				nodeID = tempMapList.get(i).getID();
			}
		}
		
		return nodeID;
	}
}