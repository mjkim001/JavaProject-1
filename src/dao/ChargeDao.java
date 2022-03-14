package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.MemberService;
import util.JDBCUtil;

public class ChargeDao {
	
private ChargeDao() {
		
	}
	
	private static ChargeDao instance;
	public static ChargeDao getInstance() {
		if(instance == null) {
			instance = new ChargeDao();
		}
		return instance;
	}
	
	public int chargemoney(int money) {
		String sql = "UPDATE MEMBER "
				+ " SET MY_MONEY = MY_MONEY + ?,"
				+ "     TOTAL_ASSET = TOTAL_ASSET + ?"
				+ " WHERE MEM_ID = ?" ;
		
		List<Object> param = new ArrayList<Object>();
		param.add(money);
		param.add(money);
		param.add(MemberService.loginMember.get("MEM_ID"));
		return JDBCUtil.update(sql, param);
	}
	
}
