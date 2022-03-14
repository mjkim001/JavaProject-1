package controller;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import dao.StockDao;
//import util.*;
import service.*;
import util.ScanUtil;
import util.View;



public class Controller {
	
	public static void main(String[] args) {
		Thread thread = new Thread(new StockUpdateThread());
		thread.start();
		new Controller().start();
	}
	
	private void start() {
		int view = View.HOME;
		
		
		while(true) {
			switch(view) {
			case View.HOME: view = home(); break; //로그인 & 회원가입 창
			case View.LOGIN: view = memberService.login(); break; //로그인
			case View.JOIN: view = memberService.join(); break; //회원가입
			
			case View.MAIN: view = printMenu(); break; //메인메뉴
			
			case View.STOCK_KOSPY: view = stockService.kospy(); break; //1,코스피
			case View.STOCK_KOSDAQ: view = stockService.kosdaq();break; //2,코스닥
			case View.CUR_INFO: view = curInfoService.viewCurInfo(); break; //3,시황정보
			case View.CUR_INFO_METAL: view = goldService.Gold(); break; //귀금속
			case View.CUR_INFO_OIL: view = oilService.Oil(); break; //유가
			case View.CUR_INFO_EXCHANGE: view = exchangeService.Exchange(); break; //환율
			
			case View.NEWS: view = newsService.getNews(); break; //4,뉴스 
			case View.NEWS_CUR: view = newsService.newsCur(); break; //시황
			case View.NEWS_ENTER: view = newsService.newsEnter(); break; //기업분석
			case View.NEWS_OVERSEAS: view = newsService.newsOverseas(); break; //해외증시
			
			
			case View.BOARD_LIST: view = boardService.boardList(); break; //5,토론 게시판 
			case View.BOARD_READ: view = boardService.boardRead(); break; //게시글 읽기
			case View.BOARD_INSERT: view = boardService.boardInsert(); break; //게시글 작성
			case View.BOARD_UPDATE: view = boardService.boardUpdate(); break; //게시글 수정
			case View.BOARD_DELETE: view = boardService.boardDelete(); break; //게시글 삭제
			
			
			
			case View.FAVORITES: view = favoriteService.favoriteList(); break; //7,즐겨찾기 목록
			
			case View.CHARGE: view = chargeService.chargemoney(); break; //8,충전  
			case View.USER_INFO: view = InfoService.getInstance().viewUserInfo(); break; //9,사용자 정보
			
			
			}
		}
	}
	private ChargeService chargeService = ChargeService.getInstance();
	private BoardService boardService = BoardService.getInstance();
	private MemberService memberService = MemberService.getInstance();
	private StockService stockService = StockService.getInstance();
	private FavoriteService favoriteService = FavoriteService.getInstance();
	private NewsService newsService = NewsService.getInstance();
	private OilService oilService = OilService.getInstance();
	private CurInfoService curInfoService = CurInfoService.getInstance();
	private GoldService goldService = GoldService.getInstance();
	private ExchangeService exchangeService = ExchangeService.getInstance();
	
	private int home() {

		System.out.println("----------------------------------------");
		System.out.println("1.로그인   2.회원가입   0.프로그램 종료 >");
		System.out.println("----------------------------------------");
		int input = ScanUtil.nextInt();
		switch(input) { //메서드 호출하지 않고 return을 통해 구성
		case 1: return View.LOGIN;
		case 2: return View.JOIN;
		case 0:
			System.out.println("프로그램이 종료되었습니다.");
			System.exit(0);	
		}
		return View.HOME;
	}
	
	private int printMenu() {
    	System.out.println("===============================================================================");
		System.out.println("1.코스피  2.코스닥  3.시황정보  4.뉴스  5.토론게시판 "
				+ " 6.즐겨찾기목록  7.충전  8.투자내역  0.로그아웃 >");
		
		int input =ScanUtil.nextInt();
		
		switch(input) {
		case 1: return View.STOCK_KOSPY;
		case 2: return View.STOCK_KOSDAQ;
		case 3: return View.CUR_INFO;
		case 4: return View.NEWS;
		case 5: return View.BOARD_LIST;
		case 6: return View.FAVORITES;
		case 7: return View.CHARGE;
		case 8: return View.USER_INFO;
		case 0:
			//MemberService.LoginMember = null;
			return View.HOME;
		}
    	
    	return View.MAIN;
	}

}
