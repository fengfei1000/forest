package fengfei.forest.mongo;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Example1 {

	/**
	 * @param args
	 * @throws MongoException
	 * @throws UnknownHostException
	 */
	public static void main(String[] args) throws UnknownHostException, MongoException {
		String username = "user1";
		String pwd = "pwd1";
		Mongo m = new Mongo("localhost", 27017);

		DB db = m.getDB("mydb");
		// boolean auth = db.authenticate(username,
		// pwd.toCharArray());
		DBCollection coll = db.getCollection("testCollection");
		BasicDBObject doc = new BasicDBObject();

		doc.put("name", "MongoDB");
		doc.put("type", "database");
		doc.put("count", 1);

		BasicDBObject info = new BasicDBObject();

		info.put("x", 203);
		info.put("y", 102);

		doc.put("info", info);

		coll.insert(doc);
		DBObject o = coll.findOne();
		System.out.println(o);
		//
		Set<String> colls = db.getCollectionNames();

		for (String s : colls) {
			System.out.println(s);
		}
	}
}
