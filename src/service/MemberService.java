package service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.MemberDao;
import util.ScanUtil;
import util.View;


public class MemberService {
	
	private MemberService() {
		
	}
	private static MemberService instance;
	public static MemberService getInstance() {
		if(instance == null) {
			instance = new MemberService();
		}
		return instance;
	}
	public static Map<String, Object> loginMember;
	
	private MemberDao memberDao = MemberDao.getInstance();
	
	public int join() {
		
		String idReg = "^[a-zA-Z]{1}[a-zA-Z0-9]{4,11}$"; // 시작은 영문으로하고 영문,숫자 조합으로 5자이상 12자이하
		String passReg = "[a-zA-Z0-9_*~*!*]{6,12}"; // 특수문자는 _,~,!만 쓸수있고 숫자,영문,특수문자 조합으로 6자이상 12자이하
		String memNameReg = "[a-zA-Z]{2,10}"; // 이름은 문자만 2자이상 10자이하
		
		System.out.println("============================== 회원가입 ===============================");
		System.out.println("아이디는 시작은 영문으로만 가능하며 영문,숫자 조합으로 5자이상 12자이하");
		System.out.println("비밀번호를 특수문자는 _,~,!만 가능하며 숫자,영문,특수문자(선택) 조합으로 6자이상 12자이하");
		System.out.println("이름은 문자만 2자이상 10자이하");
		System.out.println("====================================================================");
		System.out.print("아이디 > ");
		String memId = ""; //알파벳이나 숫자
		memId = ScanUtil.nextLine();
		//id 유효성검사
		Pattern p1 = Pattern.compile(idReg);
		Matcher m1 = p1.matcher(memId);
		if(!m1.matches()) {
			System.out.println("아이디는 시작은 영문으로만 가능하며 영문,숫자 조합으로 5자이상 12자이하이어야 합니다.");
			return View.HOME;
		}
		//id중복확인
		if(memberDao.checkMember(memId) != null) {
			System.out.println("이미 가입된 회원입니다.");
			return View.HOME;
		}
		
		System.out.print("비밀번호 > ");
		String password = ""; //영문자 숫자 특수문자 
		password = ScanUtil.nextLine();
		
		Pattern p2 = Pattern.compile(passReg);
		Matcher m2 = p2.matcher(password);
		if(!m2.matches()) {
			System.out.println("비밀번호를 특수문자는 _,~,!만 가능하며 숫자,영문,특수문자 조합으로 6자이상 12자이하이어야 합니다.");
			return View.HOME;
		}
		
		System.out.print("회원명 > ");
		String memName = ""; //알파벳 숫자
		memName = ScanUtil.nextLine();
		
		Pattern p3 = Pattern.compile(memNameReg);
		Matcher m3 = p3.matcher(memName);
		if(!m3.matches()) {
			System.out.println("이름은 문자만 2자이상 10자이하이어야 합니다.");
			return View.HOME;
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("MEM_ID", memId);
		param.put("PASSWORD", password);
		param.put("MEM_NAME", memName);
		
		int result = memberDao.insertMember(param);
		if(result > 0) {
			System.out.println("회원가입이 완료되었습니다.");
		}else {
			System.out.println("회원가입 실패");
		}
		
		return View.HOME;
	}
	
	public int login() {
		System.out.println("================= 로그인 =================");
		System.out.print("아이디 > ");
		String memId = ScanUtil.nextLine();
		System.out.print("비밀번호 > ");
		String password = ScanUtil.nextLine();
		
		Map<String, Object> member = memberDao.loginMember(memId, password);
		
		if(member == null) {
			System.out.println("아이디 혹은 비밀번호를 잘못 입력하셨습니다.");
		}else {
			System.out.println("로그인 성공");
			loginMember = member;
			return View.MAIN;
		}
		return View.HOME;
	}
	
}
