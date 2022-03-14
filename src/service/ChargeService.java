package service;

import java.util.Map;

import dao.ChargeDao;
import dao.MemberDao;
import util.ScanUtil;
import util.View;

public class ChargeService {
	
private ChargeService() {
		
	}
	
	private static ChargeService instance;
	public static ChargeService getInstance() {
		if(instance == null) {
			instance = new ChargeService();
		}
		return instance;
	}
	
	private ChargeDao chargeDao = ChargeDao.getInstance();
	
	public int chargemoney() {
		System.out.println("충전한도는 1,000,000원 입니다.");
		System.out.print("충전금액 > ");
		int money = ScanUtil.nextInt();
		if(money > 1000000) {
			System.out.print("충전한도를 넘었습니다. 다시 입력해주세요 > ");
		}else {
			int result = chargeDao.chargemoney(money);
			MemberService.loginMember = MemberDao.getInstance().getMember(MemberService.loginMember.get("MEM_ID"));
			if(result > 0) System.out.println("충전이 완료되었습니다.");
		}
		return View.MAIN;
	}
}
