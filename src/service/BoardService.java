package service;

import java.util.List;
import java.util.Map;

import dao.BoardDao;
import util.JDBCUtil;
import util.ScanUtil;
import util.View;

public class BoardService {
	
	private BoardService() {
		
	}
	private static BoardService instance;
	public static BoardService getInstance() {
		if(instance == null) {
			instance = new BoardService();
		}
		return instance;
	}
	
	private BoardDao boardDao = BoardDao.getInstance();
	
	public int boardList() { //첫화면 출력
		List<Map<String, Object>> boardList = boardDao.selectBoardList();
		
		System.out.println("[게 시 판]");
		System.out.println("========================================================");
		System.out.printf("글번호\t   제목\t\t     작성자\t 작성일");
		System.out.println();
		System.out.println("--------------------------------------------------------");
		for(Map<String, Object> board : boardList) {
			System.out.printf("%-5s %-10.10s\t%10s %10s",board.get("BOARD_NO"),board.get("BOARD_TITLE"),board.get("MEM_NAME"),board.get("BOARD_DATE"));
			System.out.println();
			System.out.println("--------------------------------------------------------");
		}

		System.out.print("1.[조회] 2.[등록] 0.[돌아가기] > ");
		
		int input = ScanUtil.nextInt();
		
		switch(input) {
		case 1 : 
			System.out.print("게시물 번호 > ");
			currentBoardNo = ScanUtil.nextInt();
			return View.BOARD_READ; //read
		case 2 :
			return View.BOARD_INSERT; //insert
		case 0 :
			return View.MAIN; //join
		}
		return View.BOARD_LIST; //list
	}
	
	int currentBoardNo;
	int currentCommentsNo;
	
	public int boardRead() { //조회
		int boardNo = currentBoardNo;
		Map<String, Object> board = boardDao.selectBoard(boardNo);
		List<Map<String, Object>> comments = boardDao.selectComment(boardNo);
		
		System.out.println("[게 시 판] - [조 회]");
		System.out.println("======================================================");
		System.out.println("번호\t: " + board.get("BOARD_NO"));
		System.out.println("작성자\t: " + board.get("MEM_NAME"));
		System.out.println("작성일\t: " + board.get("BOARD_DATE"));
		System.out.println("제목\t: " + board.get("BOARD_TITLE"));
		System.out.println("내용\t : " + board.get("BOARD_CONTENT"));
		System.out.println("======================================================");
		
		if(comments != null) {
			System.out.println("댓글번호\t작성자\t내용\t작성일");
			for(Map<String, Object> comment : comments) {
				System.out.println(comment.get("COMMENTS_NO")
						+ "\t" + comment.get("MEM_NAME")
						+ "\t" + comment.get("COMMENTS_CON")
						+ "\t" + comment.get("COMMENTS_DATE"));
			}
		}else {
			System.out.println();
		}

		if(board.get("MEM_ID").equals(MemberService.loginMember.get("MEM_ID"))) {				
			System.out.print("1.[수정] 2.[삭제] 3.[댓글달기] 0.[목록] > ");
			int input = ScanUtil.nextInt();
			switch(input) {
			case 1 : return View.BOARD_UPDATE;
			case 2 : return View.BOARD_DELETE;
			case 3 : return commentInsert(boardNo);
			case 0 : return View.BOARD_LIST;
			}
			return View.BOARD_LIST;
		}else {
			System.out.print("1.[댓글달기] 0.[목록] > ");		
			int input = ScanUtil.nextInt();
			switch(input) {
			case 1 : return commentInsert(boardNo);
			case 0 : return View.BOARD_LIST;
			}
			return View.BOARD_LIST;
		}
		
		
	}
	
	public int boardInsert() {
		System.out.println("[게 시 판] - [등 록]");
		System.out.println("======================================================");
		System.out.print("제목 > ");
		String title = ScanUtil.nextLine();
		System.out.print("내용 > ");
		String content = ScanUtil.nextLine();
		String memId = (String)MemberService.loginMember.get("MEM_ID");
		
		int result = boardDao.insertBoard(title, content, memId);
		
		if(0 < result) {
			System.out.println("게시물 등록 성공");
		}else {
			System.out.println("게시물 등록 실패");
		}
		return View.BOARD_LIST;
	}
	
	public int commentInsert(int boardNo) {
		System.out.println("[게 시 판] - [댓글달기]");
		System.out.println("======================================================");
		System.out.print("댓글내용 > ");
		String content = ScanUtil.nextLine();
		String memId = (String)MemberService.loginMember.get("MEM_ID");
		
		int result = boardDao.insertComment(content, memId, boardNo);
		
		if(0 < result) {
			System.out.println("댓글 등록 성공");
			return View.BOARD_LIST;
		}else {
			System.out.println("댓글 등록 실패");
			return boardRead();
		}
	}
	
	public int boardUpdate() {
		System.out.println("[게 시 판] - [수 정]");
		System.out.println("======================================================");
		int boardNo = currentBoardNo;
		System.out.print("제목 > ");
		String title = ScanUtil.nextLine();
		System.out.print("내용 > ");
		String content = ScanUtil.nextLine();
		
		int result = boardDao.updateBoard(boardNo, title, content);
		
		if(0 < result) {
			System.out.println("게시글 수정 성공");
			return View.BOARD_LIST;
		}else {
			System.out.println("게시글 수정 실패");
			return boardRead();
		}
	}
	
	public int boardDelete() {
		System.out.print("정말로 삭제하시겠습니까?(y/n) > ");
		String y = ScanUtil.nextLine();
		if(y.equals("y")) {
			int boardNo = currentBoardNo;
			int result1 = boardDao.deleteComments(boardNo);
			int result = boardDao.deleteBoard(boardNo);
			if(0 < result) {
				System.out.println("게시글 삭제 성공");
				return View.BOARD_LIST;
			}else {
				System.out.println("게시글 삭제 실패");
				return boardRead();
			}
		}else {
			return boardRead();
		}
		
		
		
	}
}
