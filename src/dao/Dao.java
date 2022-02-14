package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import util.JDBCUtil;

public class Dao {
	
	private Dao() { // 다른 클래스에서 객체생성을 막기 위해 생성자에 private를 붙인다
		
	}
	private static Dao instance; // 객체를 만들어서 보관 할 변수
	public static Dao getInstance() { // 다른 클래스에서 객체가 필요할 때 호출 할 메서드
		if(instance == null) {
			// 객체가 없으면 객체 생성
			instance = new Dao();
		}
		return instance;
	}
	
	int stockNo = 101;
	public int insertStock(List<Object> param) {
		String insert = "INSERT INTO STOCK"
				+ "   VALUES("
				+ "   'K'||?,"
				+ "   ?,?,?,?,?,?,?,?,?,?,?)";
		

		List<Object> _param = new ArrayList<Object>();
		_param.add(String.valueOf(stockNo++));
		for(int i = 0; i < param.size(); i++) {
			_param.add(param.get(i));
		}
		
		
		return JDBCUtil.update(insert, _param);
	}
	
	
	public void deleteStock(String table) {
		String delete = "DELETE FROM "
				+ table;
		List<Object> param = new ArrayList<Object>();
		
		JDBCUtil.update(delete);
	}

}
