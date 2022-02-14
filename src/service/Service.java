package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dao.Dao;

public class Service {

	public static void main(String[] args) {
			new Service().start();
		
			
	}
	
	private Dao testDao = Dao.getInstance();
	
	private void start() {
		stock();
	}
	
	private void stock() {
		String Samsung = "https://finance.naver.com/item/main.naver?code=005930";
		String LGEnerge = "https://finance.naver.com/item/main.naver?code=373220";
		String SkHynix = "https://finance.naver.com/item/main.naver?code=000660";
		String SamsungWo = "https://finance.naver.com/item/main.naver?code=005935";
		String Naver = "https://finance.naver.com/item/main.naver?code=035420";
		String SamsungBio = "https://finance.naver.com/item/main.naver?code=207940";
		String LGChemical = "https://finance.naver.com/item/main.naver?code=051910";
		String HyunDae = "https://finance.naver.com/item/main.naver?code=005380";
		String SamsungSDI = "https://finance.naver.com/item/main.naver?code=006400";
		String Kakao = "https://finance.naver.com/item/main.naver?code=035720";
		String SeltHealth = "https://finance.naver.com/item/main.naver?code=091990";
		String EchoPro = "https://finance.naver.com/item/main.naver?code=247540";
		String PerAbis = "https://finance.naver.com/item/main.naver?code=263750";
		String LNF = "https://finance.naver.com/item/main.naver?code=066970";
		String KakaoGame = "https://finance.naver.com/item/main.naver?code=293490";
		String HLB = "https://finance.naver.com/item/main.naver?code=028300";
		String Wemade = "https://finance.naver.com/item/main.naver?code=112040";
		String SeltDrug = "https://finance.naver.com/item/main.naver?code=068760";
		String CheonBo = "https://finance.naver.com/item/main.naver?code=278280";
		String CJENM = "https://finance.naver.com/item/main.naver?code=035760";
		
		
		String[] stockArr = {Samsung, LGEnerge, SkHynix, SamsungWo, Naver, SamsungBio, LGChemical, HyunDae, SamsungSDI, Kakao,
				SeltHealth, EchoPro, PerAbis, LNF, KakaoGame, HLB, Wemade, SeltDrug, CheonBo, CJENM};
		
		testDao.deleteStock("KOSPY");
		testDao.deleteStock("KOSDAQ");
		testDao.deleteStock("STOCK");
		
		for(int i = 0; i < stockArr.length; i++) {
			stockUpdate(stockArr[i]);
		}
	}
	
	
	
	void stockUpdate(String url) {
		Document doc;
		
		try {
			doc = Jsoup.connect(url).get();
			//rtydeturtfux
			Elements curPrice = doc.select("#chart_area > div.rate_info > div > p.no_today > em > span");
			Elements stockName = doc.select("#middle > div.h_company > div.wrap_company > h2 > a");
			Elements befPrice = doc.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(1) > td.first > em > span");
			Elements highPrice = doc.select("table > tbody > tr:nth-child(1) > td:nth-child(2) > em:nth-child(2) > span");
			Elements lowPrice = doc.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(2) > td:nth-child(2) > em:nth-child(2) > span");
			Elements compare = doc.select("#chart_area > div.rate_info > div > p.no_exday > em:nth-child(2) > span");
			Elements totalVol = doc.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(1) > td:nth-child(3) > em > span");
			Elements totalPrice = doc.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(2) > td:nth-child(3) > em > span");
			Elements marketTotal = doc.select("#_market_sum");
			Elements startPrice = doc.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(2) > td.first > em > span");
			Elements stockClass = doc.select("#tab_con1 > div.first > table > tbody > tr:nth-child(2) > td");
			
			String CurPrice = curPrice.get(0).text();
			String StockName = stockName.get(0).text();
			String BefPrice = befPrice.get(0).text();
			String HighPrice = highPrice.get(0).text();
			String LowPrice = lowPrice.get(0).text();
			String Compare = compare.get(1).text();
			String TotalVol = totalVol.get(0).text();
			String TotalPrice = totalPrice.get(0).text();
			String MarketTotal = marketTotal.get(0).text();
			String StartPrice = startPrice.get(0).text();
			String[] StockClass = stockClass.get(0).text().split(" ");
			

		
			
			List<Object> param = new ArrayList<Object>();
			param.add(StockClass[0]);
			param.add(StockName);
			param.add(Integer.parseInt(CurPrice.replace(",", "")));
			param.add(Integer.parseInt(StartPrice.replace(",", "")));
			param.add(Integer.parseInt(BefPrice.replace(",", "")));
			param.add(Integer.parseInt(Compare.replace(",", "")));
			param.add(Integer.parseInt(HighPrice.replace(",", "")));
			param.add(Integer.parseInt(LowPrice.replace(",", "")));
			param.add(Integer.parseInt(TotalVol.replace(",", "")));
			param.add(Integer.parseInt(TotalPrice.replace(",", "")));
			param.add(MarketTotal);


		
			int insertSuccess = testDao.insertStock(param);
			
			if(insertSuccess != 0) {
				System.out.println("업데이트 성공");
			}else {
				System.out.println("업데이트 실패");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
