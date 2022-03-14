package dao;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.select.Elements;

import util.JDBCUtil;

public class OilDao {
	
private OilDao() {
		
	}
	
	private static OilDao instance;
	public static OilDao getInstance() {
		if(instance == null) {
			instance = new OilDao();
		}
		return instance;
	}
	
	public int UpdateOil(Elements item) {
		String sql ="UPDATE OIL"
				+ "     SET OIL_CURPRICE = ?, "
				+ "     OIL_COMPARE = ?, OIL_UNIT = ?, OIL_UPDOWN = ?"
				+ "  WHERE OIL_NAME = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(item.get(2).text());
		param.add(item.get(3).text());
		param.add(item.get(1).text());
		param.add(item.get(4).text());
		param.add(item.get(0).text());
		return JDBCUtil.update(sql, param);
		
	}


	
}
