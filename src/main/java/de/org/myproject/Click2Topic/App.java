package de.org.myproject.Click2Topic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

class User {
	private String id;
	private List<String> url;

	/**
	 * @param id
	 */
	public User(String id) {
		super();
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	private void setId(String id) {
		this.id = id;
	}

	public List<String> getUrl() {
		return url;
	}

	private void setUrl(List<String> url) {
		this.url = url;
	}

	public List<String> click(AlchemyAPI api, Set<String> urls)
			throws XPathExpressionException, IOException, SAXException,
			ParserConfigurationException {
		List<String> category = new ArrayList<String>();
		Document doc;
		for (String url : urls) {
			doc = api.URLGetCategory(url);
			String categoryTemp = doc.getElementsByTagName("category").item(0)
					.getTextContent();
			category.add(categoryTemp);
		}

		return category;

	}

}

class Url {
	private String url;

	/**
	 * @param url
	 */
	public Url(String url) {
		super();
		this.url = url;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	private void setUrl(String url) {
		this.url = url;
	}

}

/**
 * Hello world!
 * 
 */
public class App {
	private static String apiKey = " ";

	public static void main(String[] args) throws XPathExpressionException,
			IOException, SAXException, ParserConfigurationException {
		AlchemyAPI api = AlchemyAPI.GetInstanceFromString(apiKey);
		User usr1 = new User("Usr1");
		User usr2 = new User("Usr2");
		User usr3 = new User("Usr3");
		
		Set<String> url1 = new HashSet<String>();
		Set<String> url2 = new HashSet<String>();
		Set<String> url3 = new HashSet<String>();
		//adding urls
		String x1 = "http://www.bild.de/ratgeber/gesundheit/gesundheit/home-15734752.bild.html";
		String x2 = "http://www.bild.de/geld/startseite/geld/home-15683376.bild.html";
		String x3 = "http://www.bild.de/sport/fussball/fussball/home-15769054.bild.html";
		String x4 = "http://www.bild.de/spiele/online-spiele/online-spiele/home-15720618.bild.html";
		String x5 = "http://www.bild.de/politik/inland/bischof/marx-neuer-vorsitzender-der-bischofskonferenz-35036532.bild.html";

		url1.add(x1);
		url1.add(x2);
		url1.add(x3);
		url1.add(x4);
		url1.add(x5);

		url2.add(x1);
		url2.add(x2);
		url2.add(x3);

		url3.add(x2);
		url3.add(x3);
		url3.add(x4);
		url3.add(x5);

		List<String> c1 = new ArrayList<String>();
		List<String> c2 = new ArrayList<String>();
		List<String> c3 = new ArrayList<String>();
		c1.addAll(usr1.click(api, url1));
		c1.addAll(usr1.click(api, url2));
		c1.addAll(usr1.click(api, url3));

		c2.addAll(usr2.click(api, url2));

		c3.addAll(usr3.click(api, url1));
		c3.addAll(usr3.click(api, url3));

		Map<User, List<String>> db = new HashMap<User, List<String>>();
		db.put(usr1, c1);
		db.put(usr2, c2);
		db.put(usr3, c3);

		Map<User, List<String>> db2 = new HashMap<User, List<String>>();

		db2.put(usr1, usr1.click(api, url1));
		db2.put(usr1, usr1.click(api, url2));
		db2.put(usr1, usr1.click(api, url3));
		db2.put(usr2, usr2.click(api, url3));
		db2.put(usr2, usr2.click(api, url2));
		db2.put(usr3, usr3.click(api, url3));

		// print the results
		System.out.println("Print1");
		print(db);
		// print the results
		System.out.println("Print2");
		print(db2);

	}

	public static void print(Map<User, List<String>> db) {

		for (Entry<User, List<String>> entry : db.entrySet()) {
			User usr = entry.getKey();
			List<String> category = entry.getValue();

			System.out.println(usr.getId().toString() + ":" + "category="
					+ category);

		}
	}

}
