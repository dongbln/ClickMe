/**
 * 
 */
package org.myProject.alcheAPIPrototype.Storm;

 

import java.util.Map;

import org.myProject.alcheAPIPrototype.User;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

/**
 * @author tsi Tawatchai Siripanya
 * 
 */
public class UserExtractBolt extends BaseRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private OutputCollector collector;

	@SuppressWarnings("rawtypes")
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
		// this.data = new HashMap<User, List<String>>();

	}

	public void execute(Tuple input) {
		Object data = null;
		data = input.getValueByField("user");
		// System.out.println(data);
		// if (input.contains("user"))
		// data = (HashMap<User, List<String>>) input.getValueByField("user");
		print(data);

	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// do nothing
	}

	public static void print(Object data) {
		// for (Entry<User, List<String>> entry : data.entrySet()) {
		// System.out.println("Key : " + entry.getKey() + " Value : "
		// + entry.getValue());
		// }

		System.out.println(data.toString());
	}

}
