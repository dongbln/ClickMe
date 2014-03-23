/**
 * 
 */
package org.myProject.alcheAPIPrototype.Storm;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.alchemyapi.api.AlchemyAPI;

import org.myProject.alcheAPIPrototype.User;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * @author tsi Tawatchai Siripanya
 * 
 */
public class URLExtractBolt extends BaseRichBolt {
	public static final Logger logger = LoggerFactory
			.getLogger(URLExtractBolt.class);
	private OutputCollector collector;
	
	private Random rand;
	private Queue<String> q ;
	private User user;
	private URL aURL;
	private String apiKey;
	
	public URLExtractBolt(String apiKey){
		this.apiKey=apiKey;
	}

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		this.rand = new Random();	
		this.q= new ArrayBlockingQueue<String>(1024);
		//String[] usrs = new String[] { "u1","u2","u3"};
		//user = new User(usrs[rand.nextInt(usrs.length)]);

	}

	public void execute(Tuple input) {
		AlchemyAPI api = AlchemyAPI.GetInstanceFromString(apiKey);
		// Map<User, ArrayList<String>> data = new HashMap<User,
		// ArrayList<String>>();
		// List<String> urls= new ArrayList<String>();
		//user = new User(input.getStringByField("usr"));
		String[] usrs = new String[] { "u1","u2","u3"};
		
	 
		String url = input.getStringByField("url");
		if(url != null) {
		q.offer(url);
		}
		// here  queue einbauen
		Document d1 = null;
		try {
			String myUrl =(String) q.poll();
			  aURL = new URL(myUrl);
			
		if(myUrl !=null){
			d1 = api.URLGetCategory(myUrl);
		}
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String c1 = d1.getElementsByTagName("category").item(0)
				.getTextContent();
		user = new User(usrs[rand.nextInt(usrs.length)]);
		user.getCategories().add(c1);
		user.getCategories().add(aURL.getPath());
		System.out.println("User:"+user.getId()+":Category"+user.getCategories());
		 
		 
			 
		 
		
//		rand.nextInt(list.size()).
//		user.getCategories().add(c1);
		
 
		// data.put(user, (ArrayList<String>) urls);

		// collector.emit(new Values(user)) ;
		 collector.ack(input);
		
		//print(user);

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// declarer.declare(new Fields("user"));
	}

	public static void print(User user) {
		System.out.println("User: "+user.getId()+"; Categories: "+user.getCategories());	
		 
	}
	
	public static void print(String [] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println();
			
		}
		 
	}

}
