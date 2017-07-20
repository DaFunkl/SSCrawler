package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebStuff {

	public static void fetchLinks(String toFile) {
		createWriter(toFile);
		jsoupMagic();
		System.out.println("insgesamt gefundene: " + completeAmt);
		closeWriter();
	}

	public static void jsoupMagic() {
//		String[] url = { "https://www.stepstone.de/5/resultlistpage?fu=1000000&of=",
//				"&suid=2065338d-5bdb-4f99-9ff9-0a536581c303&an=paging_next" };
		int mult = 100;// 25;
		String[] url = {
				"https://www.stepstone.de/5/resultlistpage?fu=1000000&li=100&of=",
				"&suid=2065338d-5bdb-4f99-9ff9-0a536581c303"
		};
		int i = 0;
		String curl = url[0] + i + url[1];
		boolean flag = true;
		while (flag) {
			System.out.println(curl);
			flag = parseUrl(curl);
			curl = url[0] + (++i * mult) + url[1];
			System.out.println("aktueller Stand: " +completeAmt);
		}
	}

	static int completeAmt = 0;

	public static boolean parseUrl(String url) {
		boolean foundJob = false;
		Document doc;
		try {
			doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			Link cLink;
			int count = 0;
			HashMap<String, Link> jobs = new HashMap<String, Link>();
			for (Element link : links) {
				cLink = new Link(link.attr("abs:href"), link.text());
				if (cLink.isJob()) {
					if (jobs.keySet().contains(cLink.url)) {
						Link l = jobs.get(cLink.url);
						l.description = cLink.text;
						jobs.put(cLink.url, l);
					} else {
						foundJob = true;
						count++;
						completeAmt++;
						jobs.put(cLink.url, cLink);
					}
				}
			}
			writeJobs(jobs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return foundJob;
	}

	public static void writeJobs(HashMap<String, Link> jobs) {
		for (String s : jobs.keySet()) {
//			write(jobs.get(s).toString());
			write(jobs.get(s).url);
		}
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
			w.write(s + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
