package service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import dao.FavoriteDao;
import service.StockService;
import util.ScanUtil;
import util.View;

public class FavoriteService {
	private FavoriteService() {

	}

	private static FavoriteService instance;

	public static FavoriteService getInstance() {
		if (instance == null) {
			instance = new FavoriteService();
		}
		return instance;
	}

	private FavoriteDao favoriteDao = FavoriteDao.getInstance();
	
	public int addFavorite(int index, String kind) {
		if(favoriteDao.checkFavorite(index, kind) == null) {			
			favoriteDao.addFavorite(index, kind);
			System.out.println("즐겨찾기 목록에 추가되었습니다.");
			return View.MAIN;
		}else {
			System.out.println("이미 즐겨찾기 목록에 있는 종목입니다.");
			if(kind.equals("KOSPY")) {
				return View.STOCK_KOSPY;
			}else {
				return View.STOCK_KOSDAQ;
			}
		}
		
		
	}
	
	
	public int favoriteList() {
		List<Map<String, Object>> favoriteList = favoriteDao.favoriteList();
		
		//즐겨찾기 리스트 출력
		//1.즐겨찾기삭제 2.주식상세정보확인 0.돌아가기
		int index = 1;
		System.out.println("=================================================================================================================================");
		System.out.println("즐겨찾기");
		System.out.printf("    분류\t%10s\t     현재가격\t 시가\t    전일\t     전일대비\t  고가\t     저가\t      거래량\t거래대금\t\t시가총액","종목명");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
		for (Map<String, Object> f : favoriteList) {
			int curPrice = Integer.parseInt(String.valueOf(f.get("CUR_PRICE")));
			int startPrice = Integer.parseInt(String.valueOf(f.get("START_PRICE")));
			int befPrice = Integer.parseInt(String.valueOf(f.get("BEF_PRICE")));
			int compare = Integer.parseInt(String.valueOf(f.get("COMPARE")));
			int highPrice = Integer.parseInt(String.valueOf(f.get("HIGH_PRICE")));
			int lowPrice = Integer.parseInt(String.valueOf(f.get("LOW_PRICE")));
			int totalVol = Integer.parseInt(String.valueOf(f.get("TOTAL_VOL")));
			int totalPrice = Integer.parseInt(String.valueOf(f.get("TOTAL_PRICE")));
			System.out.printf("%-3d", index++);
			System.out.print(f.get("STOCK_CLASS") + "\t");
			System.out.printf("%-10s\t%10s",f.get("STOCK_NAME"), NumberFormat.getInstance().format(curPrice));
			System.out.printf("%10s %10s %10s %10s %10s",NumberFormat.getInstance().format(startPrice), NumberFormat.getInstance().format(befPrice),
					NumberFormat.getInstance().format(compare),NumberFormat.getInstance().format(highPrice),NumberFormat.getInstance().format(lowPrice));
			System.out.printf("  %10s %10s %15s",NumberFormat.getInstance().format(totalVol), NumberFormat.getInstance().format(totalPrice),f.get("MARKET_TOTAL"));
			System.out.println();
		}
		System.out.println("=================================================================================================================================");
		
		System.out.print("1.[주식상세정보확인] 2.[즐겨찾기 삭제] 0.[돌아가기] > ");
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1: return StockService.getInstance().viewStockInfo("FAVORIT");
		case 2: deleteFavorite(); break;
		case 0: return View.MAIN;
		}
		return View.MAIN;
		
	}


	public void deleteFavorite() {
		
		System.out.print("종목번호를 입력해주세요. > ");
		int index = ScanUtil.nextInt();
		
		int result = favoriteDao.deleteFavorite(index);
		if(result > 0) {
			System.out.println("즐겨찾기 목록에서 삭제되었습니다.");
		}else {
			System.out.println("해당번호가 존재하지 않습니다.");
		}
		

	}	
	
	
}
