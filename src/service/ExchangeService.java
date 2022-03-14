package service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dao.OilDao;
import util.ScanUtil;
import util.View;

public class ExchangeService {
	String URL = "https://finance.naver.com/marketindex/worldExchangeList.naver?key=exchange";
	
	OilDao oilDao = OilDao.getInstance();
	private ExchangeService() { // 다른 클래스에서 객체생성을 막기 위해 생성자에 private를 붙인다
		
	}
	private static ExchangeService instance; // 객체를 만들어서 보관 할 변수
	public static ExchangeService getInstance() { // 다른 클래스에서 객체가 필요할 때 호출 할 메서드
		if(instance == null) {
			// 객체가 없으면 객체 생성
			instance = new ExchangeService();
		}
		return instance;
	}
	
	public int Exchange() {
		Document doc;
		try {
			doc = Jsoup.connect(URL).get();			
			
			
			Elements GBP = doc.select("body > div > table > tbody > tr:nth-child(1) > td");
			Elements EUR = doc.select("body > div > table > tbody > tr:nth-child(2) > td");
			Elements HKD = doc.select("body > div > table > tbody > tr:nth-child(4) > td");
			Elements JPY = doc.select("body > div > table > tbody > tr:nth-child(5) > td");
			Elements CNY = doc.select("body > div > table > tbody > tr:nth-child(14) > td");
			
			Elements[] exchangeArr = {GBP,EUR,HKD,JPY,CNY};
			
			System.out.println("[시황정보] - [환율]");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.printf("상품명\t\t심볼\t\t현재가\t\t전일비\t\t등락율");
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			int index = 1;
			for(Elements item : exchangeArr) {
				for(int i = 0; i < item.size(); i++) {					
					System.out.printf("%-10s\t",item.get(i).text());
				}
				System.out.println();
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
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
		return View.CUR_INFO_EXCHANGE;
		
	}
	
}
