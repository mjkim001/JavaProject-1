package service;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dao.OilDao;
import dao.StockDao;
import util.ScanUtil;
import util.View;

public class OilService {

		String URL = "https://finance.naver.com/marketindex/?tabSel=gold#tab_section";
		
		OilDao oilDao = OilDao.getInstance();
		private OilService() { // 다른 클래스에서 객체생성을 막기 위해 생성자에 private를 붙인다
			
		}
		private static OilService instance; // 객체를 만들어서 보관 할 변수
		public static OilService getInstance() { // 다른 클래스에서 객체가 필요할 때 호출 할 메서드
			if(instance == null) {
				// 객체가 없으면 객체 생성
				instance = new OilService();
			}
			return instance;
		}
		
		public int Oil() {
			Document doc;
			try {
				doc = Jsoup.connect(URL).get();			
				
				
				Elements oil = doc.select("#content > div:nth-child(3) > table > tbody > tr:nth-child(1) > td");
				Elements high = doc.select("#content > div:nth-child(3) > table > tbody > tr:nth-child(2) > td");
				Elements light = doc.select("#content > div:nth-child(3) > table > tbody > tr:nth-child(3) > td");
				Elements Dubai = doc.select("#content > div:nth-child(3) > table > tbody > tr:nth-child(4) > td");
				Elements Brent = doc.select("#content > div:nth-child(3) > table > tbody > tr:nth-child(5) > td");
				Elements WTI = doc.select("#content > div:nth-child(3) > table > tbody > tr:nth-child(6) > td");
				
				Elements[] oilArr = {oil, high, light, Dubai,Brent,WTI};
				
				System.out.println("[시황정보] - [유가정보]");
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
				System.out.println("상품명\t\t단위\t\t현재가\t\t전일비\t\t등락율\t\t기준일");
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
				for(Elements item : oilArr) {
					for(int i = 0; i < item.size(); i++) {					
						System.out.printf("%-10s\t",item.get(i).text());
					}
					System.out.println();
					System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
					oilDao.UpdateOil(item);
				}

				System.out.print("1.[뒤로가기] 2.[메뉴] > ");
				int input = ScanUtil.nextInt();
				switch(input) {
				case 1: return View.CUR_INFO;
				case 2: return View.MAIN;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return View.CUR_INFO_OIL;
			
		}
		

}
