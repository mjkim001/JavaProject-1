package util;

public interface View {
	int HOME = 0;
	int LOGIN = 1;
	int JOIN = 2;
	
	int MAIN = 3;
	
	//메인 메뉴
	int STOCK_KOSPY = 4; //코스피
	int STOCK_KOSDAQ = 5; //코스닥
	int CUR_INFO = 6; //시황정보
	int NEWS = 7; //뉴스
	int BOARD_LIST = 8; //토론게시판
	int FAVORITES = 10; //즐겨찾기 목록
	int CHARGE = 11; //충전
	int USER_INFO = 12; //사용자 정보
	
	//시황정보 메뉴
	int CUR_INFO_METAL = 13; //귀금속
	int CUR_INFO_OIL = 14; //유가
	int CUR_INFO_EXCHANGE = 15; //환율
	
	//뉴스 메뉴
	int NEWS_CUR = 16; //시황
	int NEWS_ENTER = 17; //기업분석
	int NEWS_OVERSEAS = 18; //해외증시
	
	//토론게시판 메뉴
	int BOARD_READ = 19; //게시글 읽기
	int BOARD_INSERT = 20; //게시글 작성
	int BOARD_UPDATE = 21; //게시글 수정
	int BOARD_DELETE = 22; //게시글 삭제

}
