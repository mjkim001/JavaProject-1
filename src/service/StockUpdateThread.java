package service;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dao.StockDao;

public class StockUpdateThread implements Runnable{
	
	private static String Samsung = "https://finance.naver.com/item/main.naver?code=005930";
	private static String LGEnerge = "https://finance.naver.com/item/main.naver?code=373220";
	private static String SkHynix = "https://finance.naver.com/item/main.naver?code=000660";
	private static String SamsungWo = "https://finance.naver.com/item/main.naver?code=005935";
	private static String Naver = "https://finance.naver.com/item/main.naver?code=035420";
	private static String SamsungBio = "https://finance.naver.com/item/main.naver?code=207940";
	private static String LGChemical = "https://finance.naver.com/item/main.naver?code=051910";
	private static String HyunDae = "https://finance.naver.com/item/main.naver?code=005380";
	private static String SamsungSDI = "https://finance.naver.com/item/main.naver?code=006400";
	private static String Kakao = "https://finance.naver.com/item/main.naver?code=035720";
	private static String SeltHealth = "https://finance.naver.com/item/main.naver?code=091990";
	private static String EchoPro = "https://finance.naver.com/item/main.naver?code=247540";
	private static String PerAbis = "https://finance.naver.com/item/main.naver?code=263750";
	private static String LNF = "https://finance.naver.com/item/main.naver?code=066970";
	private static String KakaoGame = "https://finance.naver.com/item/main.naver?code=293490";
	private static String HLB = "https://finance.naver.com/item/main.naver?code=028300";
	private static String Wemade = "https://finance.naver.com/item/main.naver?code=112040";
	private static String SeltDrug = "https://finance.naver.com/item/main.naver?code=068760";
	private static String CheonBo = "https://finance.naver.com/item/main.naver?code=278280";
	private static String CJENM = "https://finance.naver.com/item/main.naver?code=035760";
	
	static String[] stockArr = {Samsung, LGEnerge, SkHynix, SamsungWo, Naver, SamsungBio, LGChemical, HyunDae, SamsungSDI, Kakao, SeltHealth,
			EchoPro, PerAbis, LNF, KakaoGame, HLB, Wemade, SeltDrug, CheonBo, CJENM};
	
	@Override
	public void run() {
		while(true) {
			try {
				for(String stock : stockArr) {
					stockUpdate(stock);
				}
				Thread.sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void stockUpdate(String stock) {
		Document doc;
		
		try {
			doc = Jsoup.connect(stock).get();
		
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
			String Compare = compare.get(0).text().equals("상승") ? compare.get(1).text() : "-" + compare.get(1).text();
			String TotalVol = totalVol.get(0).text();
			String TotalPrice = totalPrice.get(0).text();
			String MarketTotal = marketTotal.get(0).text();
			String StartPrice = startPrice.get(0).text();
			String[] StockClass = stockClass.get(0).text().split(" ");
			

			
			List<Object> param = new ArrayList<Object>();
			param.add(Integer.parseInt(CurPrice.replace(",", "")));
			param.add(Integer.parseInt(StartPrice.replace(",", "")));
			param.add(Integer.parseInt(BefPrice.replace(",", "")));
			param.add(Integer.parseInt(Compare.replace(",", "")));
			param.add(Integer.parseInt(HighPrice.replace(",", "")));
			param.add(Integer.parseInt(LowPrice.replace(",", "")));
			param.add(Integer.parseInt(TotalVol.replace(",", "")));
			param.add(Integer.parseInt(TotalPrice.replace(",", "")));
			param.add(MarketTotal);
			param.add(StockName);

			StockDao.getInstance().upDateStock(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		

}
