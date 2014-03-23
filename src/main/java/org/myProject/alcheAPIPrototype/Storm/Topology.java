/**
 * 
 */
package org.myProject.alcheAPIPrototype.Storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

/**
 * @author tsi Tawatchai Siripanya
 * 
 */
public class Topology {
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
	static String[] urls = new String[] { x1, x2, x3, x4, x5, x6, x7, x8, x9, x10 };

	/**
	 * @param args
	 * @throws InvalidTopologyException
	 * @throws AlreadyAliveException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new RandomUrlSpout(urls));
		builder.setBolt("bolt1", new URLExtractBolt()).shuffleGrouping("spout");
		// builder.setBolt("bolt1", new
		// URLExtractBolt()).fieldsGrouping("spout",
		// new Fields("usr"));
		// builder.setBolt("bolt2", new UserExtractBolt())
		// .shuffleGrouping("bolt1");

		// builder.setBolt("bolt2", new
		// UserExtractBolt()).fieldsGrouping("bolt1",
		// new Fields("user"));

		Config conf = new Config();
		conf.setDebug(false);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(1);

			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} else {
			conf.setMaxTaskParallelism(1);

			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("Click2Categories", conf,
					builder.createTopology());

			Thread.sleep(50000);

			cluster.shutdown();
		}

	}

}
