package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.MemberService;
import util.JDBCUtil;

public class MemberDao {

	private MemberDao() {
		
	}
	private static MemberDao instance;
	public static MemberDao getInstance() {
		if(instance == null) {
			instance = new MemberDao();
		}
		return instance;
	}
	
	public int insertMember(Map<String, Object> param) {
		String sql = " INSERT INTO MEMBER"
				+ " VALUES(?, ?, ?, 0, 0)";
		List<Object> _param = new ArrayList<Object>();
		_param.add(param.get("MEM_ID"));
		_param.add(param.get("PASSWORD"));
		_param.add(param.get("MEM_NAME"));
		
		return JDBCUtil.update(sql, _param);
	}
	
	public Map<String, Object> loginMember(Object memId, Object password){ //int
		String sql = " SELECT MEM_ID"
				+ "          ,PASSWORD"
				+ "          ,MEM_NAME"
				+ "          ,MY_MONEY"
				+ "          ,TOTAL_ASSET"
				+ "      FROM MEMBER"
				+ "     WHERE MEM_ID = ?"
				+ "       AND PASSWORD = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(memId);
		param.add(password);
		
		return JDBCUtil.selectOne(sql, param);
	}
	
	public Map<String, Object> getMember(Object memId){
		String sql = "SELECT MEM_ID,"
				+ "          MEM_NAME,"
				+ "          MY_MONEY,"
				+ "          TOTAL_ASSET"
				+ "     FROM MEMBER"
				+ "    WHERE MEM_ID = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(memId);
		
		return JDBCUtil.selectOne(sql, param);
	}
	
	public int updateAsset(int totalAsset) {
		String sql = "UPDATE MEMBER"
				+ "   SET TOTAL_ASSET = MY_MONEY + ?"
				+ "   WHERE MEM_ID = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(totalAsset);
		param.add(MemberService.loginMember.get("MEM_ID"));
		
		return JDBCUtil.update(sql, param);
	}
	
	public Map<String, Object> getTotalAsset(){
		String sql = "SELECT SUM(B.CUR_PRICE * A.MY_QTY) AS ASSET"
				+ "  FROM INFORMATION A, STOCK B"
				+ " WHERE A.MEM_ID = '" + MemberService.loginMember.get("MEM_ID")
				+ "'  AND A.STOCK_NO = B.STOCK_NO"
				+ " GROUP BY A.MEM_ID";
		
		return JDBCUtil.selectOne(sql);
	}
	
	public Map<String, Object> checkMember(String memId){
		String sql = "SELECT * FROM MEMBER"
				+ "   WHERE MEM_ID = '" + memId + "'";
		
		return JDBCUtil.selectOne(sql);
	}

}
