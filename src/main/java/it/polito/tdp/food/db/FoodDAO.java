package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDAO {
	public List<Food> listAllFoods(Map<Integer,Food> idFoods) {
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
			Food f=	new Food(res.getInt("food_code"),
							res.getString("display_name"));
                    list.add(f);
                    
					if(!idFoods.containsKey(res.getInt("food_code"))){
						idFoods.put(res.getInt("food_code"),f);	
					}	
					
					
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM porzione" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
		
		

		public List<Food> getVertici(Map<Integer,Food> idFoods, int numeroPorzioni){
			String sql = "SELECT food.food_code,COUNT(DISTINCT porzione.portion_id) AS porzioni " + 
					     "FROM food,porzione " + 
					     "WHERE porzione.food_code=food.food_code " + 
					     "GROUP BY food.food_code " + 
					     "HAVING porzioni>= ? "; 
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				st.setInt(1, numeroPorzioni);
				List<Food> list = new ArrayList<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
					list.add(idFoods.get(res.getInt("food_code")));
					
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				e.printStackTrace();
				return null ;
			}
		}
		
		
		
		public List<Arco> getArchi(Map<Integer,Food> idFoods, int numeroPorzioni){
			String sql = "SELECT p1.food_code AS cibo1,p2.food_code AS cibo2, AVG(p1.saturated_fats)-AVG(p2.saturated_fats) AS peso " + 
					     "FROM porzione AS p1,porzione AS p2 " + 
				         "WHERE p1.food_code> p2.food_code " + 
					     "GROUP BY p1.food_code,p2.food_code " + 
					     "HAVING AVG(p1.saturated_fats)-AVG(p2.saturated_fats)!=0" ;
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				List<Arco> list = new ArrayList<>() ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					try {
                 Food cibo1=idFoods.get(res.getInt("cibo1"));
                 Food cibo2=idFoods.get(res.getInt("cibo2"));
                 Double peso=res.getDouble("peso");
	 Arco arco=new Arco(cibo1,cibo2,peso);
	 list.add(arco);
						
					} catch (Throwable t) {
						t.printStackTrace();
					}
				}
				
				conn.close();
				return list ;

			} catch (SQLException e) {
				e.printStackTrace();
				return null ;
			}
		}
}
