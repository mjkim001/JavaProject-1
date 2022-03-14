package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JDBCUtil {
	
	private static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private static String user = "JMS";
	private static String password = "java";

	private static Connection con = null;
	private static PreparedStatement ps = null;
	private static ResultSet rs = null;
	
	/*
	 * Map<String, Object> selectOne(String sql) : 조회결과가 한 줄인 경우에 사용
	 * Map<String, Object> selectOne(String sql, List<Object> param) : 조회결과가 한 줄인 경우에 쿼리안에 ?가 있을때 사용
	 * List<Map<String,Object>> selectList(String sql) : 조회결과가 여러 줄이 가능한 경우에 사용
	 * List<Map<String,Object>> selectList(String sql, List<Object> param) : 조회결과가 여러 줄이 가능하고, 쿼리안에 ?가 있을때 사용
	 * int update(String sql)
	 * int update(String sql, List<Obejct> param)
	 */
	
	public static Map<String,Object> selectOne(String sql){
		Map<String,Object> map = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();

			if(rs.next()) {
				map = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
	
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (Exception e) {}
			if(ps != null) try { ps.close(); } catch (Exception e) {}
			if(con != null) try { con.close(); } catch (Exception e) {}
		}
		return map;
	}
	
	
	public static Map<String,Object> selectOne(String sql, List<Object> param){
		Map<String,Object> map = null;
		try {
			con = DriverManager.getConnection(url, user, password);		
			ps = con.prepareStatement(sql);
			
			for(int i = 0;  i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			if(rs.next()) {
				map = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (Exception e) {}
			if(ps != null) try { ps.close(); } catch (Exception e) {}
			if(con != null) try { con.close(); } catch (Exception e) {}
		}
		return map;
	}
	
	
	public static List<Map<String,Object>> selectList(String sql){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (Exception e) {}
			if(ps != null) try { ps.close(); } catch (Exception e) {}
			if(con != null) try { con.close(); } catch (Exception e) {}
		}
		return list;
	}
	
	
	public static List<Map<String,Object>> selectList(String sql, List<Object> param){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);
			for(int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			rs = ps.executeQuery();
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for(int i = 1; i <= columnCount; i++) {
					map.put(metaData.getColumnName(i), rs.getObject(i));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (Exception e) {}
			if(ps != null) try { ps.close(); } catch (Exception e) {}
			if(con != null) try { con.close(); } catch (Exception e) {}
		}
		return list;
	}
	
	public static int update(String sql) {
		int count = 0;
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);

			count = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (Exception e) {}
			if(ps != null) try { ps.close(); } catch (Exception e) {}
			if(con != null) try { con.close(); } catch (Exception e) {}
		}
		return count;
	}
	
	
	public static int update(String sql, List<Object> param) {
		int count = 0;
		try {
			con = DriverManager.getConnection(url, user, password);
			
			ps = con.prepareStatement(sql);
			
			for(int i = 0; i < param.size(); i++) {
				ps.setObject(i + 1, param.get(i));
			}
			count = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) try { rs.close(); } catch (Exception e) {}
			if(ps != null) try { ps.close(); } catch (Exception e) {}
			if(con != null) try { con.close(); } catch (Exception e) {}
		}
		return count;
	}
	
}
