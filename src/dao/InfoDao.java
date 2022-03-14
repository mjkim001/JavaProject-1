package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.InfoService;
import service.MemberService;
import util.JDBCUtil;

public class InfoDao {
	
private static InfoDao instance; 
	
	public static InfoDao getInstance() { 
		if(instance == null) {
			// 객체가 없으면 객체 생성
			instance = new InfoDao();
		}
		return instance;
	}
	
	public Map<String, Object> infoCheck(String stockNo){
		String sql = "SELECT STOCK_NO FROM INFORMATION"
				+ "   WHERE STOCK_NO = '" + stockNo + "'"; 
		
		return JDBCUtil.selectOne(sql);
	}

	public void infoInsert(String stockNo, int curPrice, int qty) {
		String sql = "INSERT INTO INFORMATION"
				+ "   VALUES(?,?,?,?)";
		
		List<Object> param = new ArrayList<Object>();
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(stockNo);
		param.add(curPrice);
		param.add(qty);
		
		JDBCUtil.update(sql, param);
	}

	public void infoBuyUpdate(String stockNo, int curPrice, int qty) {
		String sql = "UPDATE  INFORMATION"
				+ "   SET AVG_PRICE = ?, MY_QTY = MY_QTY + ?"
				+ "   WHERE MEM_ID = ?"
				+ "     AND STOCK_NO = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(getAvg().get("AVGPRICE"));
		param.add(qty);
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(stockNo);
		
		JDBCUtil.update(sql, param);
	}
	
	public void infoSellUpdate(String stockNo, int curPrice, int qty) {
		String sql = "UPDATE  INFORMATION"
				+ "   SET MY_QTY = MY_QTY + ?"
				+ "   WHERE MEM_ID = ?"
				+ "     AND STOCK_NO = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(qty);
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(stockNo);
		
		JDBCUtil.update(sql, param);
	}
	
	
	private Map<String, Object> getAvg(){
		String sql = "SELECT ((B.AVG_PRICE * B.MY_QTY) + (A.BUY_PRICE * A.STOCK_QTY)) / (B.MY_QTY + A.STOCK_QTY) AS AVGPRICE"
				+ "  FROM BUY_STOCK A, INFORMATION B, (SELECT NVL(MAX(BUY_NO), 0) AS CNO FROM BUY_STOCK) C"
				+ " WHERE A.STOCK_NO = B.STOCK_NO"
				+ "   AND A.BUY_NO = C.CNO";
		return JDBCUtil.selectOne(sql);
	}
	
	public List<Map<String, Object>> viewInfo(Object memId){
		String sql = "SELECT A.STOCK_NAME,"
				+ "          B.AVG_PRICE,"
				+ "          A.CUR_PRICE,"
				+ "          B.MY_QTY,"
				+ "          ROUND((A.CUR_PRICE / B.AVG_PRICE) * 100 - 100 , 2) AS PROFIT"
				+ "     FROM STOCK A, INFORMATION B"
				+ "    WHERE A.STOCK_NO = B.STOCK_NO"
				+ "      AND B.MEM_ID = '" + memId + "'";
		
		return JDBCUtil.selectList(sql);
	}

	public void infoDelete(String stockNo) {
		String sql = "DELETE FROM INFORMATION WHERE MEM_ID = ? AND STOCK_NO = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(stockNo);
		
		JDBCUtil.update(sql, param);
	}


}
