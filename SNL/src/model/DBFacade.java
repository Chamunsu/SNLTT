package model;

import java.net.UnknownHostException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

/**
 * 
 * @author MinjuneL
 * 2014. 06. 10
 * 
 * mongo database facade
 * 
 */
public class DBFacade {
	Mongo conn;
	WriteConcern w;
	DB db;
	
	public void connection(){
		try {
			conn = new Mongo("localhost", 27017);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		w = new WriteConcern(1, 2000);
		conn.setWriteConcern(w);
		
		db = conn.getDB("SNL");
	}
	public void endConnection(){
		conn.close();
	}
	
	public boolean registerID(String id, String password, String name){
		connection();
		
		boolean result = false;
		
		DBCollection coll = db.getCollection("user");
		
		DBObject query = new BasicDBObject();
		query.put("user_id", id);
		DBCursor cur = coll.find(query);
		
		if(!cur.hasNext()){
			DBObject doc = new BasicDBObject();
			doc.put("user_id", id);
			doc.put("user_password", password);
			doc.put("user_name", name);
			coll.insert(doc);
			System.out.println("[MinjuneL] (DBFacase, registerID) "+doc.toString());
			result = true;
		}
		
		endConnection();
		return result;
	}
	
	public JSONObject login(String id, String password){
		JSONObject result = null;
		connection();
		
		DBCollection coll = db.getCollection("user");
		
		DBObject query = new BasicDBObject();
		query.put("user_id", id);
		query.put("user_password", password);
		DBCursor cur = coll.find(query);
		
		if(cur.hasNext()){
			DBObject doc = new BasicDBObject();
			doc = cur.next();
			
			result = (JSONObject)JSONValue.parse(doc.toString());
		}
		endConnection();
		return result;
	}
	
	public boolean insertData(String userId, String title, String summary, String filePath,
			String folder, int endOfPdf, String date, long realTime){
		boolean result = true;
		connection();
		
		DBCollection coll = db.getCollection("data");
		
		DBObject doc = new BasicDBObject();
		doc.put("provider", userId);
		doc.put("title", title);
		doc.put("summary", summary);
		doc.put("file_path", filePath);
		doc.put("folder", folder);
		doc.put("slide_length", endOfPdf);
		doc.put("date", date);
		doc.put("real_time", realTime);
		
		coll.insert(doc);
		System.out.println("[MinjuneL] (DBFacase, insertData) "+doc.toString());
		
		coll = db.getCollection("user");
		
		BasicDBObject updateQuery = new BasicDBObject();
		updateQuery.put("user_id", userId);
		
		BasicDBObject updateCommand = new BasicDBObject();
		updateCommand.put("$push", new BasicDBObject("upload_datas", doc.get("_id")));
		coll.update(updateQuery, updateCommand);
		
		endConnection();
		return result;
	}
	
	public JSONArray getMainDatas(String userId){
		JSONArray result = new JSONArray();
		connection();
		
		DBCollection coll = db.getCollection("data");
		
		DBObject query = new BasicDBObject();
		query.put("provider", userId);
		DBCursor cur = coll.find(query);

		while(cur.hasNext()){
			DBObject doc = new BasicDBObject();
			doc = cur.next();
			JSONObject temp = new JSONObject();
			temp = (JSONObject)JSONValue.parse(doc.toString());
			result.add(temp);
		}
		
		return result;
	}
}
