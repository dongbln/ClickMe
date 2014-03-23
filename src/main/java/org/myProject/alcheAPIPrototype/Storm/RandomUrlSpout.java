package org.myProject.alcheAPIPrototype.Storm;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.myProject.alcheAPIPrototype.User;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

@SuppressWarnings("serial")
public class RandomUrlSpout extends BaseRichSpout {
	public static final Logger logger = LoggerFactory
            .getLogger(RandomUrlSpout.class);
	private SpoutOutputCollector collector;
	private Random rand;
	private String[] urls ;
	
	
	public RandomUrlSpout(){
		
	}
	public RandomUrlSpout(String[] urls ){
		this.urls=urls ;
	}

	/***
	 * Prepare before call nextTuple() method
	 */
	@SuppressWarnings("rawtypes")
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this.collector = collector;
		this.rand = new Random();		
		//this.data= new HashMap<User, String>();

	}

	/***
	 * Randomly generate a url from the array
	 */
	public void nextTuple() {
		Utils.sleep(100);
			
		String url = urls[rand.nextInt(urls.length)];
		
		
		collector.emit(new Values(url));

	}

	/***
	 * Output the field called "url"
	 */
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("url"));

	}

}
