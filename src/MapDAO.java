
import java.sql.*;
import java.util.ArrayList;

/**
 * Class responsible for getting information of the map
 * from the database
 * 
 * @author Anders, Nicolai
 *
 */
public class MapDAO {

	Connection con = null;
	ResultSet resultSet = null;

	ArrayList<Intersection> mapList = new ArrayList<Intersection>();
	int id, xCoordinate, yCoordinate, links, n1, n2, n3, n4, n5;

	/**
	 * 
	 * Get all information of the map and organizes
	 * it in an arrayList of intersection objects
	 * 
	 * @return ArrayList<Intersection> of map
	 */
	public ArrayList<Intersection> getMap() {
		mapList = new ArrayList<Intersection>();
		ResultSet resultSet = null;
		
		try {
			con = PostgresqlConnectionFactory.createConnection();
		
			Statement statement = con.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM coordinates ORDER BY id ASC");

			while (resultSet.next()) {
				id = resultSet.getInt(1);
				xCoordinate = resultSet.getInt(2);
				yCoordinate = resultSet.getInt(3);
				links = resultSet.getInt(4);
				n1 = resultSet.getInt(5);
				
				n2 = resultSet.getInt(6);
				if (resultSet.wasNull()) n2 = 9999;
				
				n3 = resultSet.getInt(7);
				if (resultSet.wasNull()) n3 = 9999;
				
				n4 = resultSet.getInt(8);
				if (resultSet.wasNull()) n4 = 9999;
				
				n5 = resultSet.getInt(9);
				if (resultSet.wasNull()) n5 = 9999;

				mapList.add(new Intersection(id, xCoordinate, yCoordinate, links, n1, n2, n3, n4, n5));
			}
			
			statement.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception g) {
			g.printStackTrace();
		}

		return mapList;
	}
	
	public ArrayList<Intersection> getMapXsorted() {
		mapList = new ArrayList<Intersection>();
		ResultSet resultSet = null;
		
		try {
			con = PostgresqlConnectionFactory.createConnection();
		
			Statement statement = con.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM coordinates ORDER BY latitude ASC");

			while (resultSet.next()) {
				id = resultSet.getInt(1);
				xCoordinate = resultSet.getInt(2);
				yCoordinate = resultSet.getInt(3);
				links = resultSet.getInt(4);
				n1 = resultSet.getInt(5);
				
				n2 = resultSet.getInt(6);
				if (resultSet.wasNull()) n2 = 9999;
				
				n3 = resultSet.getInt(7);
				if (resultSet.wasNull()) n3 = 9999;
				
				n4 = resultSet.getInt(8);
				if (resultSet.wasNull()) n4 = 9999;
				
				n5 = resultSet.getInt(9);
				if (resultSet.wasNull()) n5 = 9999;

				mapList.add(new Intersection(id, xCoordinate, yCoordinate, links, n1, n2, n3, n4, n5));
			}
			
			statement.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception g) {
			g.printStackTrace();
		}

		return mapList;
	}
	
	public ArrayList<Intersection> getMapYsorted() {
		mapList = new ArrayList<Intersection>();
		ResultSet resultSet = null;
		
		try {
			con = PostgresqlConnectionFactory.createConnection();
		
			Statement statement = con.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM coordinates ORDER BY longitude ASC");

			while (resultSet.next()) {
				id = resultSet.getInt(1);
				xCoordinate = resultSet.getInt(2);
				yCoordinate = resultSet.getInt(3);
				links = resultSet.getInt(4);
				n1 = resultSet.getInt(5);
				
				n2 = resultSet.getInt(6);
				if (resultSet.wasNull()) n2 = 9999;
				
				n3 = resultSet.getInt(7);
				if (resultSet.wasNull()) n3 = 9999;
				
				n4 = resultSet.getInt(8);
				if (resultSet.wasNull()) n4 = 9999;
				
				n5 = resultSet.getInt(9);
				if (resultSet.wasNull()) n5 = 9999;

				mapList.add(new Intersection(id, xCoordinate, yCoordinate, links, n1, n2, n3, n4, n5));
			}
			
			statement.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception g) {
			g.printStackTrace();
		}

		return mapList;
	}
	
	public ArrayList<Intersection> getFinalMap(String coordinate) {
		mapList = new ArrayList<Intersection>();
		ResultSet resultSet = null;
		
		int thisX = Integer.parseInt(coordinate.substring(0, 4));
		int thisY = Integer.parseInt(coordinate.substring(5, 9));
		
		try {
			String Query = "SELECT * FROM coordinates WHERE latitude >= ? AND latitude <= ? AND longitude >= ? AND longitude <= ?";

			con = PostgresqlConnectionFactory.createConnection();
		
			PreparedStatement statement = con.prepareStatement(Query);
			statement.setInt(1, thisX-50);
			statement.setInt(2, thisX+50);
			statement.setInt(3, thisY-50);
			statement.setInt(4, thisY+50);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				id = resultSet.getInt(1);
				xCoordinate = resultSet.getInt(2);
				yCoordinate = resultSet.getInt(3);
				links = resultSet.getInt(4);
				n1 = resultSet.getInt(5);
				
				n2 = resultSet.getInt(6);
				if (resultSet.wasNull()) n2 = 9999;
				
				n3 = resultSet.getInt(7);
				if (resultSet.wasNull()) n3 = 9999;
				
				n4 = resultSet.getInt(8);
				if (resultSet.wasNull()) n4 = 9999;
				
				n5 = resultSet.getInt(9);
				if (resultSet.wasNull()) n5 = 9999;

				mapList.add(new Intersection(id, xCoordinate, yCoordinate, links, n1, n2, n3, n4, n5));
			}
			
			statement.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception g) {
			g.printStackTrace();
		}

		return mapList;
	}

}
