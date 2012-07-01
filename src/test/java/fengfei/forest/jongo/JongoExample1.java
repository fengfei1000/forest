package fengfei.forest.jongo;

import java.io.IOException;

import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class JongoExample1 {

	/**
	 * @param args
	 * @throws MongoException
	 * @throws IOException
	 */
	public static void main(String[] args) throws MongoException, IOException {
		Mongo mongo = new Mongo("127.0.0.1", 27017);
		Jongo jongo = new Jongo(mongo.getDB("dbname"));
		MongoCollection peoples = jongo.getCollection("peoples");
		peoples.save(new People("Joe", 27));
		Iterable<People> all = peoples.find("{}").as(People.class);
		People one = peoples.findOne("{}").as(People.class);
		System.out.println(one);
	}

}

class People {

	private String _id;
	private String name;
	private int age;

	public People() {
	}

	public People(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "People [_id=" + _id + ", name=" + name + ", age=" + age + "]";
	}

}