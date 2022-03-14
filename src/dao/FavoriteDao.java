package dao;

import java.util.*;

import service.*;
import util.JDBCUtil;

public class FavoriteDao {

	private FavoriteDao() { // 다른 클래스에서 객체생성을 막기 위해 생성자에 private를 붙인다

	}

	private static FavoriteDao instance; // 객체를 만들어서 보관 할 변수

	public static FavoriteDao getInstance() { // 다른 클래스에서 객체가 필요할 때 호출 할 메서드
		if (instance == null) {
			// 객체가 없으면 객체 생성
			instance = new FavoriteDao();
		}
		return instance;
	}
	

	public int addFavorite(int index, String kind) {
		String sql = "INSERT INTO FAVORIT(MEM_ID, STOCK_NO)"
						+" VALUES(\'"+ MemberService.loginMember.get("MEM_ID") +"\',"
								+ " (SELECT A.STOCK_NO"
								+ "	  FROM (SELECT STOCK_NO, ROWNUM AS rownumber FROM " + kind + " ) A "
								+ "	 WHERE A.rownumber = "+ index +"))";

		return JDBCUtil.update(sql);
	}

	public List<Map<String, Object>> favoriteList() {
		String sql = "SELECT A.STOCK_CLASS,"
				+ "			 A.STOCK_NAME,"
				+ "			 A.CUR_PRICE,"
				+ "			 A.START_PRICE,"
				+ "			 A.BEF_PRICE,"
				+ "			 A.COMPARE,"
				+ "			 A.HIGH_PRICE,"
				+ "			 A.LOW_PRICE,"
				+ "          A.TOTAL_VOL,"
				+ "          A.TOTAL_PRICE,"
				+ "          A.MARKET_TOTAL"
				+ "		FROM STOCK A, FAVORIT B"
				+ "	   WHERE B.MEM_ID = ? "
				+ "	 	 AND A.STOCK_NO = B.STOCK_NO"
				+ "    ORDER BY A.STOCK_NO";
		
		List<Object> param = new ArrayList<Object>();
		param.add(MemberService.loginMember.get("MEM_ID"));
		
		return JDBCUtil.selectList(sql,param);
	}
	
	public int deleteFavorite(int index) {
		String sql = "DELETE FROM FAVORIT"
				+ " WHERE STOCK_NO = ( SELECT A.STOCK_NO"
				+ "           FROM (SELECT ROWNUM AS rownumber, STOCK_NO FROM FAVORIT WHERE MEM_ID = ?) A"
				+ "           WHERE A.rownumber = ?)";
		
		List<Object> param = new ArrayList<Object>();
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(index);
		
		return JDBCUtil.update(sql,param);
	}


	public Map<String, Object> checkFavorite(int index, String kind) {
		String sql = "SELECT B.STOCK_NO"
				+ "  FROM (SELECT STOCK_NO,  "
				+ "               ROWNUM AS rownumber "
				+ "          FROM " + kind +") A, FAVORIT B"
				+ " WHERE A.STOCK_NO = B.STOCK_NO"
				+ "   AND A.rownumber = ?"
				+ "   AND B.MEM_ID = ? ";
		
		List<Object> param = new ArrayList<Object>();
		param.add(index);
		param.add(MemberService.loginMember.get("MEM_ID"));
		
		return JDBCUtil.selectOne(sql, param);
	}


	public Map<String, Object> infoFavorite(int index) {
		String sql = " SELECT C.STOCK_CLASS,"
				+ "           C.STOCK_NAME,"
				+ "           C.CUR_PRICE,"
				+ "           C.START_PRICE,"
				+ "           C.BEF_PRICE,"
				+ "           C.COMPARE,"
				+ "           C.HIGH_PRICE,"
				+ "           C.LOW_PRICE,"
				+ "           C.TOTAL_VOL,"
				+ "           C.TOTAL_PRICE,"
				+ "           C.MARKET_TOTAL"
				+ "   FROM (SELECT A.STOCK_NO AS BNO"
				+ "           FROM (SELECT STOCK_NO, ROWNUM AS rownumber FROM FAVORIT WHERE MEM_ID = ?) A"
				+ "          WHERE A.rownumber = ?)B, STOCK C"
				+ "  WHERE B.BNO = C.STOCK_NO";
		
		List<Object> param = new ArrayList<Object>();
		param.add(MemberService.loginMember.get("MEM_ID"));
		param.add(index);
		
		return JDBCUtil.selectOne(sql, param);
	}
}
