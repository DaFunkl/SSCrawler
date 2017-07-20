package project;

public class Link {
	public String url;
	public String text;
	public String description;

	public Link() {
	}

	public Link(String url, String text) {
		this.url = url;
		this.text = text;
	}

	String regex = "(http)[s]?(:)[/][/](www.stepstone.de)[/](stellenangebote--)(.*)(inline.html[?]suid=)(.*)";

	public boolean isJob() {
		if (text != null && text.length() > 0) {
			return url.matches(regex);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Url: ");
		sb.append(url);
		sb.append("\n");
		sb.append("Text:");
		sb.append(text);
		sb.append("\n");
		sb.append("Description:");
		sb.append(description);
		sb.append("\n");
		return sb.toString();
	}
}