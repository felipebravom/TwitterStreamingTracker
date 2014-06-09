

import java.net.UnknownHostException;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

// This class is used to manage the connection with the Database

public class MongoConnection {
	private String HOST="localhost"; // database host
	private int PORT=27017; // database port 
	private String DB_NAME="tweets"; // database name
	private String collectionName="sent_tweets";  
	
	
	private MongoClient mongo;
	private DB db;
	private DBCollection table;
	private int status;  // 0 for not initialized, 1 for initialized, -1 for error
	

	

	public MongoConnection() {	
		this.status=0;		
	}
	
	
	public void setCollectionName(String collectionName){
		this.collectionName=collectionName;
	}
	
	// Setup the Connection with the Database
	public void setupMongo() {
		try {
			this.mongo = new MongoClient(HOST, PORT);
			this.db = this.mongo.getDB(DB_NAME);
			this.table = db.getCollection(collectionName);
			this.status=1;
						
			
		} catch (UnknownHostException e) {
			this.status=-1;
			
		}
    }
	

	
		
	
	public void insert(DBObject object){
		this.table.insert(object);		
	}
	
	public void insert(List<DBObject> objects){
		this.table.insert(objects);		
	}
	
	
	
	public void disconnect(){
		this.mongo.close();
	}
	

}
