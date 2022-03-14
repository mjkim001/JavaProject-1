package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.MemberService;
import util.JDBCUtil;

public class StockDao {
	
	private StockDao() { // 다른 클래스에서 객체생성을 막기 위해 생성자에 private를 붙인다
		
	}
	private static StockDao instance; // 객체를 만들어서 보관 할 변수
	public static StockDao getInstance() { // 다른 클래스에서 객체가 필요할 때 호출 할 메서드
		if(instance == null) {
			// 객체가 없으면 객체 생성
			instance = new StockDao();
		}
		return instance;
	}
	
	public int upDateStock(List<Object> param) {
		String insert = "UPDATE STOCK"
				+ "         SET CUR_PRICE = ?, START_PRICE = ?, BEF_PRICE = ?, COMPARE = ?, "
				+ "             HIGH_PRICE = ?, LOW_PRICE = ?, TOTAL_VOL = ?, TOTAL_PRICE = ?, MARKET_TOTAL = ?"
				+ " WHERE STOCK_NAME = ?";
		

		List<Object> _param = new ArrayList<Object>();
		
		for(int i = 0; i < param.size(); i++) {
			_param.add(param.get(i));
		}
		
		
		return JDBCUtil.update(insert, _param);
	}
	
	
	public int deleteStock(String kind) {
		String delete = "DELETE FROM STOCK"
				+ " WHERE STOCK_CLASS = ?";
		List<Object> param = new ArrayList<Object>();
		if(kind.equals("KOSPY")) {			
			param.add("코스피");
		}else {
			param.add("코스닥");
		}

		
		return JDBCUtil.update(delete,param);
	}
	
	public Map<String, Object> readStock(String kind, int index) {
		String sql = "SELECT B.STOCK_CLASS,"
				+ "        B.STOCK_NAME, "
				+ "        B.CUR_PRICE, "
				+ "        B.START_PRICE, "
				+ "        B.BEF_PRICE, "
				+ "        B.COMPARE, "
				+ "        B.HIGH_PRICE, "
				+ "        B.LOW_PRICE,"
				+ "        B.TOTAL_VOL,"
				+ "        B.TOTAL_PRICE,"
				+ "        B.MARKET_TOTAL"
				+ "  FROM (SELECT STOCK_NO, ROWNUM AS rownumber FROM STOCK WHERE STOCK_CLASS = ? ORDER BY 1) A, STOCK B"
				+ " WHERE A.STOCK_NO = B.STOCK_NO"
				+ "   AND A.rownumber = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(kind);
		param.add(index);
		
		return JDBCUtil.selectOne(sql, param);
	}

	public Map<String, Object> infoStock(int index, String kind) {
		String sql = "SELECT B.STOCK_CLASS,"
				+ "          B.STOCK_NAME,"
				+ "          B.CUR_PRICE,"
				+ "          B.START_PRICE,"
				+ "          B.BEF_PRICE,"
				+ "          B.COMPARE,"
				+ "          B.HIGH_PRICE,"
				+ "          B.LOW_PRICE,"
				+ "          B.TOTAL_VOL,"
				+ "          B.TOTAL_PRICE,"
				+ "          B.MARKET_TOTAL "
				+ "FROM ("
				+ "SELECT STOCK_NO,  ROWNUM AS rownumber FROM "
				+ kind
				+ ") A, STOCK B"
				+ " WHERE A.STOCK_NO = B.STOCK_NO"
				+ "   AND A.rownumber = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(index);
		
		return JDBCUtil.selectOne(sql, param);
	}

	
	public Map<String, Object> getBuyStock(int index, String kind) {
		String sql = "SELECT STOCK_NO,"
				+ "          CUR_PRICE"
				+ "  FROM (SELECT A.STOCK_NO AS BNO"
				+ "          FROM (SELECT STOCK_NO, ROWNUM AS rownumber FROM "+ kind +") A"
				+ "         WHERE A.rownumber = ?) B, STOCK C"
				+ " WHERE B.BNO = C.STOCK_NO";
		List<Object> param = new ArrayList<Object>();
		param.add(index);
		
		return JDBCUtil.selectOne(sql, param);
		
	}

	public int buyStock(String stockNo, int curPrice, int qty) {
		String sql = "INSERT INTO BUY_STOCK"
				+ "   VALUES("
				+ "(SELECT NVL(MAX(BUY_NO), 0)+ 1 FROM BUY_STOCK)"
				+ ",?,?,?,?, SYSDATE"
				+ ")";
		List<Object> param = new ArrayList<Object>();
		param.add(curPrice);
		param.add(qty);
		param.add(stockNo);
		param.add(MemberService.loginMember.get("MEM_ID"));
		
		return JDBCUtil.update(sql, param);
	}

	public Map<String, Object> getSellStock(int index, String kind) {
		String sql = "SELECT C.MY_QTY,"
				+ "          C.STOCK_NO,"
				+ "          D.CUR_PRICE"
				+ "  FROM (SELECT A.STOCK_NO AS BNO"
				+ "          FROM (SELECT STOCK_NO, ROWNUM AS rownumber FROM "+ kind +") A"
				+ "         WHERE A.rownumber = ?) B, INFORMATION C, STOCK D"
				+ " WHERE B.BNO = C.STOCK_NO"
				+ "   AND C.STOCK_NO = D.STOCK_NO";
		List<Object> param = new ArrayList<Object>();
		param.add(index);
		
		return JDBCUtil.selectOne(sql, param);
	}


	public int sellStock(int curPrice, String stockNo, int sellQty) {
		String sql = "INSERT INTO SELL_STOCK"
				+ "   VALUES("
				+ "(SELECT NVL(MAX(SELL_NO), 0)+ 1 FROM SELL_STOCK)"
				+ ",?,?,?,?, SYSDATE"
				+ ")";
		List<Object> param = new ArrayList<Object>();
		param.add(sellQty);
		param.add(curPrice);
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(stockNo);
		
		return JDBCUtil.update(sql, param);
		
	}
}
