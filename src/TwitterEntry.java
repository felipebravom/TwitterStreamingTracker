

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import twitter4j.Status;

public class TwitterEntry {
	private Status status; //twitter4j Tweet representation
	private List<String> tokens;  // List of words in the content
	private Map<String,Object> features;
	


	public TwitterEntry(Status status) {
		this.status=status;
		this.features=new HashMap<String,Object>();

	}
	
	public Status getStatus(){
		return this.status;
	}
	
	public List<String> getTokens(){
		return this.tokens;
	}
	
	public Map<String,Object> getFeatures(){
		return this.features;
	}
	
	public void setTokens(List<String> tokens){
		this.tokens=tokens;
	}





	// converts the Entry into a DBObject
	public DBObject dbTweet() {
		DBObject dbTweet = new BasicDBObject();
		
				
		dbTweet.put("tweetId", this.status.getId());
		

		
		
		dbTweet.put("text", this.status.getText());
		dbTweet.put("date", this.status.getCreatedAt());
		dbTweet.put("isRetweet", this.status.isRetweet());
		dbTweet.put("isRetweeted",this.status.isRetweeted());		
		dbTweet.put("retweetCount",this.status.getRetweetCount());
		
		if(this.status.isRetweet())
			dbTweet.put("retweetedStatusId", this.status.getRetweetedStatus().getId());
		
		dbTweet.put("inReplyToStatusId",this.status.getInReplyToStatusId());
		dbTweet.put("inReplyToUserId",this.status.getInReplyToUserId());
		dbTweet.put("favoriteCount",this.status.getFavoriteCount());
		
		dbTweet.put("lang", this.status.getLang());
		
		
		dbTweet.put("userId", this.status.getUser().getId());
		dbTweet.put("userName", this.status.getUser().getName());		
		dbTweet.put("userLang",this.status.getUser().getLang());		
		dbTweet.put("userFollowersCount", this.status.getUser().getFollowersCount());
		



		if (this.status.getUser().getLocation() != null)
			dbTweet.put("user_loc", this.status.getUser().getLocation());

		// If the tweet is GeoLocated we add it
		if (status.getGeoLocation() != null) {
			Double[] geo = { status.getGeoLocation().getLatitude(),
					status.getGeoLocation().getLongitude() };
			dbTweet.put("loc", geo);
		}

		if(this.status.getPlace()!=null){
			dbTweet.put("place", this.status.getPlace().getName());			
		}
		
		// Insert all the features		
		for(String feature:this.features.keySet()){
			dbTweet.put(feature, this.features.get(feature));		
		}


	


		return dbTweet;
	}





}
