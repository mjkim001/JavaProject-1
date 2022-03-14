package service;

import dao.ChargeDao;
import util.ScanUtil;
import util.View;

	public class CurInfoService {
		
	private CurInfoService() {
			
		}
		
		private static CurInfoService instance;
		public static CurInfoService getInstance() {
			if(instance == null) {
				instance = new CurInfoService();
			}
			return instance;
		}
		
		public int viewCurInfo() {
			System.out.println("[시황정보]");
			System.out.println("------------------------------------------------------------------");
			System.out.print("1.[유가정보] 2.[귀금속정보] 3.[환율] 0.[메뉴] > ");
			int input = ScanUtil.nextInt();
			
			switch(input) {
			case 1: return View.CUR_INFO_OIL;
			case 2: return View.CUR_INFO_METAL;
			case 3: return View.CUR_INFO_EXCHANGE;
			case 0: return View.MAIN;
			}
			return View.CUR_INFO;
		}
}
