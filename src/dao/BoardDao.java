package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import service.MemberService;
import util.JDBCUtil;

import java.util.*;
import util.JDBCUtil;

public class BoardDao {
	
	private BoardDao() {
		
	}
	
	private static BoardDao instance;
	public static BoardDao getInstance() {
		if(instance == null) {
			instance = new BoardDao();
		}
		return instance;
	}
	
	public static List<Map<String, Object>> selectComment(int boardNo) { //댓글조회
		String sql = "SELECT A.COMMENTS_NO"
				+ "        , A.COMMENTS_CON"
				+ "        , B.MEM_NAME"
				+ "        , TO_CHAR(A.COMMENTS_DATE, 'MM/DD') AS COMMENTS_DATE"
				+ "     FROM COMMENTS A"
				+ "     LEFT OUTER JOIN MEMBER B ON A.MEM_ID = B.MEM_ID"
				+ "    WHERE A.BOARD_NO = " + boardNo;
		
		return JDBCUtil.selectList(sql);
	}
	
	public int insertComment(String content, String memId, int boardNo) {//댓글등록
		String sql = "INSERT INTO COMMENTS"
				+ " VALUES("
				+ " 	(SELECT NVL(MAX(COMMENTS_NO), 0) + 1 FROM COMMENTS)"
				+ "   ,?, ?, ?"
				+ "   ,SYSDATE" 
				+ ")";
		List<Object> param = new ArrayList<Object>();
		param.add(content);
		param.add(boardNo);
		param.add(memId);
		
		return JDBCUtil.update(sql, param);
		
	}
	
	public int insertBoard(String title, String content, String memId) {
		String sql = "INSERT INTO BOARD"
				+ "  VALUES("
				+ "     (SELECT NVL(MAX(BOARD_NO), 0)+ 1 FROM BOARD)"
				+ "      , ? ,? , SYSDATE"
				+ "      ,?"
				+ " )";
		List<Object> param = new ArrayList<Object>();
		param.add(title);
		param.add(content);
		param.add(memId);
		
		return JDBCUtil.update(sql, param);
	}
	
	public List<Map<String, Object>> selectBoardList(){
		String sql = "SELECT A.BOARD_NO"
				+ "         ,A.BOARD_TITLE"
				+ "         ,B.MEM_NAME"
				+ "         ,TO_CHAR(A.BOARD_DATE, 'MM/DD') AS BOARD_DATE"
				+ "     FROM BOARD A"
				+ "     LEFT OUTER JOIN MEMBER B ON A.MEM_ID = B.MEM_ID"
				+ "    ORDER BY A.BOARD_NO DESC";
		
		return JDBCUtil.selectList(sql);
	}
	
	public Map<String, Object> selectBoard(int boardNo){
		String sql = "SELECT A.BOARD_NO"
				+ "         ,A.BOARD_TITLE"
				+ "         ,A.BOARD_CONTENT"
				+ "         ,B.MEM_NAME"
				+ "         ,A.MEM_ID"
				+ "         ,TO_CHAR(A.BOARD_DATE, 'MM/DD') AS BOARD_DATE"
				+ "     FROM BOARD A"
				+ "     LEFT OUTER JOIN MEMBER B ON A.MEM_ID = B.MEM_ID"
				+ "    WHERE A.BOARD_NO = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(boardNo);
		
		return JDBCUtil.selectOne(sql,param);
		
	}
	
	
	
	public int updateBoard(int boardNo, String title, String content) {
		String sql = "UPDATE BOARD"
				+ "      SET BOARD_TITLE = ?"
				+ "         ,BOARD_CONTENT = ?"
				+ "    WHERE BOARD_NO = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(title);
		param.add(content);
		param.add(boardNo);
		
		return JDBCUtil.update(sql, param);
	}
	
	public int deleteBoard(int boardNo) {
		String sql = "DELETE FROM BOARD"
				+ "    WHERE BOARD_NO = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(boardNo);
		
		
		return JDBCUtil.update(sql, param);
	}
	
	public int deleteComments(int boardNo) {
		String sql = "DELETE FROM COMMENTS"
				+ "    WHERE BOARD_NO = ?";
		
		List<Object> param = new ArrayList<Object>();
		param.add(boardNo);
		return JDBCUtil.update(sql, param);
	}

	

	

	
}
