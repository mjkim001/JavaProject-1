package service;

import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.ScanUtil;
import util.View;

public class NewsService {
	
	private static NewsService instance;

	public static NewsService getInstance() {
		if (instance == null) {
			instance = new NewsService();
		}
		return instance;
	}
	
	private static String newsSection = "https://finance.naver.com/news/news_list.naver?mode=LSS3D&section_id=101&section_id2=258&section_id3=401";
	private static String newsSection2 = "https://finance.naver.com/news/news_list.naver?mode=LSS3D&section_id=101&section_id2=258&section_id3=402";
	private static String newsSection3 = "https://finance.naver.com/news/news_list.naver?mode=LSS3D&section_id=101&section_id2=258&section_id3=403";
	
	Document doc;
	
	public int getNews() {	
		System.out.println("[뉴스]");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.print("1.[시황뉴스] 2.[기업분석] 3.[해외증시] 0.[돌아가기] > ");
		int input = ScanUtil.nextInt();
		
		switch (input) {
		case 1: return View.NEWS_CUR;
		case 2: return View.NEWS_ENTER;
		case 3: return View.NEWS_OVERSEAS;
		case 0: return View.MAIN;
		}
		return View.NEWS;
	}
	
	public int newsCur() {
		try {
			doc = Jsoup.connect(newsSection).get();
			
			Elements title = doc.select("#contentarea_left > ul > li.newsList.top > dl > .articleSubject");
			Elements content = doc.select("#contentarea_left > ul > li.newsList.top > dl > .articleSummary");
			
			System.out.println("[뉴스] - [시황뉴스]");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			for(int i = 0; i < 10; i++) {
				System.out.print(i + 1 + ". ");
				System.out.println(title.get(i).text());
				System.out.println(content.get(i).text());
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			}

			
			System.out.print("1.[뒤로가기] 0.[메인] > ");
			int input = ScanUtil.nextInt();
			
			switch (input) {
			case 1: return View.NEWS;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return View.MAIN;
	}

	public int newsEnter() {
		try {
			doc = Jsoup.connect(newsSection2).get();
			
			Elements title = doc.select("#contentarea_left > ul > li.newsList.top > dl > .articleSubject");
			Elements content = doc.select("#contentarea_left > ul > li.newsList.top > dl > .articleSummary");
			System.out.println("[뉴스] - [기업분석]");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			for(int i = 0; i < 10; i++) {
				System.out.print(i + 1 + ". ");
				System.out.println(title.get(i).text());
				System.out.println(content.get(i).text());
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			}

			
			System.out.print("1.[뒤로가기] 0.[메인] > ");
			int input = ScanUtil.nextInt();
			
			switch (input) {
			case 1: return View.NEWS;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return View.MAIN;
	}

	public int newsOverseas() {
		try {
			doc = Jsoup.connect(newsSection3).get();
			
			Elements title = doc.select("#contentarea_left > ul > li.newsList.top > dl > .articleSubject");
			Elements content = doc.select("#contentarea_left > ul > li.newsList.top > dl > .articleSummary");
			System.out.println("[뉴스] - [해외증시]");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			for(int i = 0; i < 10; i++) {
				System.out.print(i + 1 + ". ");
				System.out.println(title.get(i).text());
				System.out.println(content.get(i).text());
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			}

			
			System.out.print("1.[뒤로가기] 0.[메인] > ");
			int input = ScanUtil.nextInt();
			
			switch (input) {
			case 1: return View.NEWS;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return View.MAIN;
	}
}
