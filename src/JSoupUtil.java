import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JSoupUtil {
	private static String URL = "https://movie.douban.com/annual2015/";

	JSoupUtil() {
		String url = URL;
		Connection connection = Jsoup.connect(url);
		Document document;
		for (int i = 1; i < 6; i++) {
			if (i == 3) {
				continue;
			}
			try {
				document = connection.get();
				Elements page = document.select("div[data-anchor=" + i + "]");
				Iterator<Element> pageIter = page.iterator();
				while (pageIter.hasNext()) {
					Element element = pageIter.next();
					Element topTitle = element.select("div.top-title").first();
					Element bottomTitle = element.select("div.bottom-title").first();
					String title = topTitle.text() + bottomTitle.text();
					System.out.println(title);
					Element description=element.select("span.description").first();
					System.out.println(description.text());
					Elements film = element.select("a.subject");
					Iterator<Element> fiIter = film.iterator();
					while (fiIter.hasNext()) {
						Element fiElement = fiIter.next();
						System.out.println(fiElement.attr("href"));
						Elements eleHref = fiElement.select("p");
						String name = eleHref.get(0).text();
						String value = eleHref.get(1).text();
						System.out.printf("%-15s\t%-5s\n", name, value);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		new JSoupUtil();
	}
}
