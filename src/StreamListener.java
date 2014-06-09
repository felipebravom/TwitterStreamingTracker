

import java.util.ArrayList;
import java.util.List;

import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.mongodb.DBObject;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

// Implement StatusListener to retrieve tweets from the Streaming API

public class StreamListener implements StatusListener {
	public final static int MAX_TWEETS_ACCUM= 500; // Max number of tweets accumulated before saving	
	private List<Status> tweetAccum; // Accumulates Tweets

	private MongoConnection mongoConnection;

	public StreamListener(MongoConnection mongo) {		

		this.mongoConnection=mongo;
		this.tweetAccum=new ArrayList<Status>();



		// TODO Auto-generated constructor stub
	}

	public void setMongoConnection(MongoConnection mongo){
		this.mongoConnection=mongo;
	}
	
	
	public void setupLanguageDetector(){
		try {
			DetectorFactory.loadProfile("profiles/");
		} catch (LangDetectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	public void onStatus(Status status) {



		this.tweetAccum.add(status);


		// check whether the list of tweets is full
		if(this.tweetAccum.size()>=MAX_TWEETS_ACCUM){
			List<DBObject> dbObs=new ArrayList<DBObject>();

			for(Status st:this.tweetAccum ){
				TwitterEntry te=new TwitterEntry(st);
				TwitterEntryController tec=new TwitterEntryController(te);
				tec.detectLanguage();
				dbObs.add(te.dbTweet());
			}
			this.mongoConnection.insert(dbObs);
			System.out.println("SAVED");

			this.tweetAccum.clear();
		}

	}		





	public void onDeletionNotice(
			StatusDeletionNotice statusDeletionNotice) {
		System.out.println("Got a status deletion notice id:"
				+ statusDeletionNotice.getStatusId());
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		System.out.println("Got track limitation notice:"
				+ numberOfLimitedStatuses);
	}

	public void onScrubGeo(long userId, long upToStatusId) {
		System.out.println("Got scrub_geo event userId:" + userId
				+ " upToStatusId:" + upToStatusId);
	}

	public void onStallWarning(StallWarning warning) {
		System.out.println("Got stall warning:" + warning);
	}

	public void onException(Exception ex) {
		ex.printStackTrace();
	}

}
