package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
	public static void main(String[] args) {
        createWriter("./Wassabi.txt");
        try {
			jsoupMagic();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        closeWriter();
    }

	public static void jsoupMagic() throws IOException {
		String url = "https://www.stepstone.de/5/resultlistpage?fu=1000000&of=&suid=2065338d-5bdb-4f99-9ff9-0a536581c303&an=paging_next";
		print("Fetching %s...", url);

		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(), src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"), link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
		}
	}

	private static void print(String msg, Object... args) {
		String s = String.format(msg, args);
		System.out.println(s);
		write(s);
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}

	static Writer w;

	public static void createWriter(String path) {
		File statText = new File(path);
		FileOutputStream is;
		try {
			is = new FileOutputStream(statText);
			OutputStreamWriter osw = new OutputStreamWriter(is);
			w = new BufferedWriter(osw);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void closeWriter() {
		try {
			w.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void write(String s) {
		try {
			w.write(s+"\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}