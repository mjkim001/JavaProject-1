package util;

import java.util.Scanner;

public class ScanUtil {
	
	private static Scanner s = new Scanner(System.in); //static을 붙인 이유는 프로그램을 시작하자마자 생성이 되어야 객체를 생성하지 않고도 사용이 가능하기 때문에
	
	public static String nextLine() {
		return s.nextLine();
	}
	
	public static int nextInt() {
		 int input = 0;
		 
		 try {
		 input = Integer.parseInt(s.nextLine());
		 } catch(Exception e) {
			System.out.print("잘못 입력하셨습니다. 다시 입력해주세요.>");
			input = nextInt(); //재귀호출
		 }
		 
		 return input;
	}

}
