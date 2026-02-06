package com.example.boardv1.board;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SummernoteService {

    public String parseContent(String content) {
        Document doc = Jsoup.parse(content);

        // 태그명 가져오기
        Elements paragraphs = doc.select("p");

        for (Element element : paragraphs) {
            System.out.println("p text = " + element.text());
        }

        Elements iframes = doc.select("iframe");
        for (Element iframe : iframes) {
            System.out.println("iframe src = " + iframe.attr("src"));
        }

        return content;
    }
}
