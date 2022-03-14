package dao;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;

import util.JDBCUtil;

public class GoldDao {

	private GoldDao() {
		
	}
	
	private static GoldDao instance;
	public static GoldDao getInstance() {
		if(instance == null) {
			instance = new GoldDao();
		}
		return instance;
	}
	

	public int UpdateGold(Elements item) {
		String sql ="UPDATE METAL"
				+ "     SET METAL_CURPRICE = ?, "
				+ "     METAL_COMPARE = ?, METAL_UNIT = ?, METAL_UPDOWN = ?"
				+ "  WHERE METAL_NAME = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(item.get(2).text());
		param.add(item.get(3).text());
		param.add(item.get(1).text());
		param.add(item.get(4).text());
		param.add(item.get(0).text());
		return JDBCUtil.update(sql, param);
	}
	


}
