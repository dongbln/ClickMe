/**
 * 
 */
package org.myProject.alcheAPIPrototype;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

/**
 * @author tsi Tawatchai Siripanya
 * 
 */
public class Simulation {
	
	private static final Log logger = LogFactory.getLog(Simulation.class);
	private static final String x1 = "http://www.bild.de/ratgeber/gesundheit/gesundheit/home-15734752.bild.html";
	private static final String x2 = "http://www.bild.de/geld/startseite/geld/home-15683376.bild.html";
	private static final String x3 = "http://www.bild.de/unterhaltung/kino/kino/home-15697512.bild.html";
	private static final String x4 = "http://www.bild.de/sport/fussball/fussball/home-15769054.bild.html";
	private static final String x5 = "http://www.bild.de/unterhaltung/musik/musik/home-15697104.bild.html";
	private static final String x6 = "http://www.bild.de/politik/inland/haushaltsplan/schaeuble-macht-schluss-mit-schulden-35035888.bild.html";
	private static final String x7 = "http://www.bild.de/politik/inland/bischof/marx-neuer-vorsitzender-der-bischofskonferenz-35036532.bild.html";
	private static final String x8 = "http://www.bild.de/spiele/online-spiele/online-spiele/home-15720618.bild.html";
	private static final String x9 = "http://www.bild.de/regional/frankfurt/frankfurt-am-main/wetter_extreme-35025306.bild.html";
	private static final String x10 = "http://www.webtrekk.com/de/home.html";

	private static final String pos = "I love donuts";
	private static final String neg = "Cats are stupid";
	private static final String n1 = "Ich liebe Wald";
	private static final String n2 = "Ich esse keine Reis";

	/**
	 * @param args
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws XPathExpressionException
	 */
	public static void main(String[] args) throws XPathExpressionException,
			IOException, SAXException, ParserConfigurationException {
		AlchemyAPI api = AlchemyAPI.GetInstanceFromString(apiKey);
		User u1 = new User("u1");
		User u2 = new User("u2");
		logger.info("Adding urls in the list...");
		List<String> url1 = new ArrayList<String>();		
		url1.add(x1);
		url1.add(x1);
		url1.add(x2);
		url1.add(x2);
		url1.add(x3);
		url1.add(x3);
		List<String> url2 = new ArrayList<String>();
		url2.add(x1);
		url2.add(x2);
		url2.add(x3);
		url2.add(x4);
		url2.add(x5);
		url2.add(x6);
		url2.add(x6);
		url2.add(x7);
		url2.add(x7);
		url2.add(x8);
		url2.add(x9);

		// logger.info("user1 clicking...");
		// click(url1, api, u1);
		// logger.info("user2 clicking...");
		// click(url2, api, u2);

		// System.out.println(u1.getCategories());
		// System.out.println("u1 size:" + u1.getCategories().size());
		// System.out.println(u2.getCategories());
		// System.out.println("u2 size:" + u2.getCategories().size());

		// Sentiment
		// String gerPos="Ich liebe Ihre Produkte";
		// String gerNeg="Meine Webseite  ist langsam";
		// sentiment(gerPos, api);
		// sentiment(gerNeg, api);

		 //Entities
		String t="http://www.techcrunch.com/";
		String[] urs= new String[]{"usr1","usr2","usr3","usr4"};
		Random rand = new Random();
		String s = url2.get(rand.nextInt(url2.size()));
		for (int i = 0; i < url2.size(); i++) {
			entity(s, api);
		}
		
		
		
		   // Extract concept tags for a web URL.
//        Document doc = api.URLGetRankedConcepts("http://www.techcrunch.com/");
//        System.out.println(getStringFromDocument(doc));
// 



		// Counting
		// countWithFrequency(u1);
		// countWithFrequency(u2);

	}

	/***
	 * Get the categories from given urls
	 * 
	 * @param myUrl
	 * @param api
	 * @param user
	 * @throws XPathExpressionException
	 * @throws IOException
	 * @throws SAXException
	 * @throws ParserConfigurationException
	 */

	public static void click(List<String> myUrl, AlchemyAPI api, User user)
			throws XPathExpressionException, IOException, SAXException,
			ParserConfigurationException {

		for (String url : myUrl) {
			Document d1 = api.URLGetCategory(url);
			String c1 = d1.getElementsByTagName("category").item(0)
					.getTextContent();
			user.getCategories().add(c1);
		}

	}

	public static void countWithFrequency(User user) {
		Set<String> set = new HashSet<String>(user.getCategories());
		for (String url : set) {
			System.out.println("User:" + user.getId() + " has:" + url + ":"
					+ Collections.frequency(user.getCategories(), url));
		}
	}

	public static void sentiment(String text, AlchemyAPI api)
			throws XPathExpressionException, IOException, SAXException,
			ParserConfigurationException {
		Document doc = api.TextGetTextSentiment(text);
		String sentiment = doc.getElementsByTagName("type").item(0)
				.getTextContent();
		String score = doc.getElementsByTagName("score").item(0)
				.getTextContent();
		System.out.println("Sentiment: " + sentiment);
		System.out.println("Score: " + score);

	}

	public static void entity(String url, AlchemyAPI api)
			throws XPathExpressionException, IOException, SAXException,
			ParserConfigurationException {
		//List<String> myList = new ArrayList<String>();
		
		Document doc = api.URLGetRankedNamedEntities(url);
		System.out.println(doc);
		
		//Document doc = api.URLGetRankedConcepts(url);
		for (int i = 0; i < 1; i++) {
			
			String entities = doc.getElementsByTagName("entity").item(i).getTextContent();			
					
			System.out.println("Entities:" + entities);
			
		}

		
	}
	
	// utility method
    private static String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);

            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
