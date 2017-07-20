package project;

public class App {
	public static void main(String[] args) {
		WebStuff.fetchLinks("./Wassabi.txt");
	}
	
	public static void linkTest(){
		Link tl = new Link("http://www.stepstone.de/stellenangebote--IT-SOFTWAREENTWICKLER-M-W-Berlin-NEW-YORKER--4399105-inline.html?suid=2065338d-5bdb-4f99-9ff9-0a536581c303&rltr=1_1_25_dynrl_m_0_0&isHJR=true"
				, "asd");
		System.out.println(tl);
		System.out.println(tl.isJob());
	}
}
