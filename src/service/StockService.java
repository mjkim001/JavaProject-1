package service;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import controller.Controller;
import dao.FavoriteDao;
import dao.InfoDao;
import dao.MemberDao;
import dao.StockDao;
import util.ScanUtil;
import util.View;

public class StockService {
	
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
	
	static String[] kospy = {Samsung, LGEnerge, SkHynix, SamsungWo, Naver, SamsungBio, LGChemical, HyunDae, SamsungSDI, Kakao};
	static String[] kosdaq = {SeltHealth,
			EchoPro, PerAbis, LNF, KakaoGame, HLB, Wemade, SeltDrug, CheonBo, CJENM};
	
	
	private StockDao stockDao = StockDao.getInstance();
	private FavoriteService favoriteService = FavoriteService.getInstance();
	
	private StockService(){}
	
	private static StockService instance; 
	
	public static StockService getInstance() { 
		if(instance == null) {
			// 객체가 없으면 객체 생성
			instance = new StockService();
		}
		return instance;
	}
	
	
	public int kospy() {
		return viewStock(kospy,"KOSPY");
	}
	
	public int kosdaq() {
		return viewStock(kosdaq,"KOSDAQ");
		
	}
	
	private int viewStock(String[] stockList, String kind) {
		
		System.out.println("=================================================================================================================================");
		System.out.printf("    분류\t%10s\t     현재가격\t 시가\t    전일\t     전일대비\t  고가\t     저가\t        거래량\t  거래대금\t\t  시가총액","종목명");
		System.out.println();
		
		int index = 1;

		for(int i = 0; i < stockList.length; i++) {
			if(kind.equals("KOSPY")) {					
					stockRead("코스피",index++);
			}else {
					stockRead("코스닥",index++);
			}
		}
		System.out.println("=================================================================================================================================");
		System.out.print("1.[주식상세정보 확인] 0.[메뉴] > ");


		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: return viewStockInfo(kind);
		case 0: return View.MAIN;
		}
		return View.MAIN;
	}
	
	
	
	private void stockRead(String kind, int index) {
		Map<String, Object> stock = stockDao.readStock(kind, index);
		System.out.printf("%-3d",index);
		stockFormat(stock);

	}


	public int viewStockInfo(String kind) {
		System.out.print("종목번호를 입력해주세요. > ");
		int index = ScanUtil.nextInt();
		if(kind.equals("FAVORIT")) {
			favStockInfo(index);
			
		}else {			
			stockInfo(index, kind);
		}

		System.out.print("1.[매수] 2.[매도] 3.[즐겨찾기] 4.[뒤로가기] 0.[메뉴] > ");

		int input = ScanUtil.nextInt();
		switch(input) {
		case 1: return buyStock(index,kind);
		case 2: return sellStock(index,kind);
		case 3: return favoriteService.addFavorite(index, kind);
		case 4: 
			int view;
			view = kind.equals("KOSPY") ? View.STOCK_KOSPY : kind.equals("KOSDAQ") ? View.STOCK_KOSDAQ : kind.equals("FAVORIT") ? View.FAVORITES 
					: View.USER_INFO;
			return view;
		case 0: return View.MAIN;
		}
		return View.MAIN;
	}

	
	private int sellStock(int index, String kind) {

		
		while(true) {			
			if(stockDao.getSellStock(index, kind) == null) {
				System.out.println("보유하고 있는 종목이 아닙니다.");
				return View.MAIN;
			}
			
			Map<String, Object> stock = stockDao.getSellStock(index, kind);
			int qty = Integer.parseInt(String.valueOf(stock.get("MY_QTY")));
			String stockNo = String.valueOf(stock.get("STOCK_NO"));
			int curPrice = Integer.parseInt(String.valueOf(stock.get("CUR_PRICE")));
			
			System.out.println("보유수량:" + qty);
			System.out.print("판매랑 수량을 입력해주세요 > ");
			int sellQty = ScanUtil.nextInt();
			if(sellQty > qty) {
				System.out.print("보유 수량보다 많습니다. 다시 입력해주세요 > ");
				continue;
			}else if(sellQty <= 0){
				System.out.print("판매랑 수량을 다시 입력해주세요 > ");
				continue;
			}
			int result = stockDao.sellStock(curPrice,stockNo,sellQty);
			if(result > 0) {			
				System.out.println(sellQty + "개를 판매하였습니다.");	
				MemberService.loginMember =  MemberDao.getInstance().getMember(MemberService.loginMember.get("MEM_ID"));
				if(sellQty == qty) {
					InfoDao.getInstance().infoDelete(stockNo);
				}else {					
					InfoDao.getInstance().infoSellUpdate(stockNo,curPrice,-sellQty);
				}
				return View.MAIN;
			}else {
				System.out.println("판매를 실패했습니다.");
				return View.MAIN;
			}
		}
	}


	private int buyStock(int index, String kind) {
		Map<String, Object> stock = stockDao.getBuyStock(index, kind);
		String stockNo = String.valueOf(stock.get("STOCK_NO"));
		int myMoney = Integer.parseInt(String.valueOf(MemberService.loginMember.get("MY_MONEY")));
		int curPrice = Integer.parseInt(String.valueOf(stock.get("CUR_PRICE")));
		System.out.println("보유금액:" + myMoney + "원  최대구매가능 개수:" + 
				myMoney/curPrice + "개");
		while(true) {			
			if(myMoney/curPrice == 0) {
				System.out.print("보유 금액이 부족하여 매수하실수 없습니다. 충전 하시겠습니까? (y/n) ");
				String input = ScanUtil.nextLine();
				if(input.equals("y")) return View.CHARGE;
				return View.MAIN;
			}
			System.out.print("구매할 수량을 입력해주세요 > ");
			int qty = ScanUtil.nextInt();		
			if(qty > myMoney/curPrice) continue;
			else if(qty <= 0) {
				System.out.print("구매할 수량을 다시 입력해주세요 > ");
				continue;
			}
			int result = stockDao.buyStock(stockNo,curPrice,qty);
			if(result > 0) {			
				System.out.println(qty + "개를 구매하였습니다.");	
				MemberService.loginMember =  MemberDao.getInstance().getMember(MemberService.loginMember.get("MEM_ID"));
				if(InfoDao.getInstance().infoCheck(stockNo) == null) {					
					InfoDao.getInstance().infoInsert(stockNo,curPrice,qty);
				}else {
					InfoDao.getInstance().infoBuyUpdate(stockNo,curPrice,qty);
				}
				return View.MAIN;
			}else {
				System.out.println("구매를 실패했습니다.");
				return View.MAIN;
			}
		}
	}


	private void stockInfo(int input, String kind) {
		Map<String, Object> stock;
		if(stockDao.infoStock(input, kind) == null) {
			System.out.println("확인할 종목이 없습니다.");
			
		}else {
			stock = stockDao.infoStock(input, kind);
			System.out.printf("분류\t%10s\t     현재가격\t 시가\t    전일\t     전일대비\t  고가\t     저가\t        거래량\t  거래대금\t\t  시가총액","종목명");
			System.out.println();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
			stockFormat(stock);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
		}
		
	}
	
	private void favStockInfo(int input) {
		Map<String,Object> stock;
		if(FavoriteDao.getInstance().infoFavorite(input) == null) {
			System.out.println("확인할 종목이 없습니다.");
		}else {
			stock = FavoriteDao.getInstance().infoFavorite(input);
			System.out.printf("분류\t%10s\t     현재가격\t 시가\t    전일\t     전일대비\t  고가\t     저가\t      거래량\t거래대금\t\t시가총액","종목명");
			System.out.println();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
			stockFormat(stock);
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
		}
	}
	
	private void stockFormat(Map<String, Object> stock) {
		int curPrice = Integer.parseInt(String.valueOf(stock.get("CUR_PRICE")));
		int startPrice = Integer.parseInt(String.valueOf(stock.get("START_PRICE")));
		int befPrice = Integer.parseInt(String.valueOf(stock.get("BEF_PRICE")));
		int compare = Integer.parseInt(String.valueOf(stock.get("COMPARE")));
		int highPrice = Integer.parseInt(String.valueOf(stock.get("HIGH_PRICE")));
		int lowPrice = Integer.parseInt(String.valueOf(stock.get("LOW_PRICE")));
		int totalVol = Integer.parseInt(String.valueOf(stock.get("TOTAL_VOL")));
		int totalPrice = Integer.parseInt(String.valueOf(stock.get("TOTAL_PRICE")));
		
		System.out.print(stock.get("STOCK_CLASS") + "\t");
		System.out.printf("%-10s\t%10s",stock.get("STOCK_NAME"), NumberFormat.getInstance().format(curPrice));
		System.out.printf("%10s %10s %10s %10s %10s",NumberFormat.getInstance().format(startPrice), NumberFormat.getInstance().format(befPrice),
				NumberFormat.getInstance().format(compare),NumberFormat.getInstance().format(highPrice),NumberFormat.getInstance().format(lowPrice));
		System.out.printf("  %10s %10s %15s",NumberFormat.getInstance().format(totalVol), NumberFormat.getInstance().format(totalPrice),stock.get("MARKET_TOTAL"));
		System.out.println();
	}
}
