

import twitter4j.*;
import twitter4j.auth.AccessToken;

public class StreamingTracker {

	public final static String OAUTH_CONSUMER_KEY = "2J6YxWjj7zaVt979uoZtA";
	public final static String OAUTH_CONSUMER_SECRET = "8cIMS0nopUvQ8IVQZIUAx1SE2F56YoIC4PtcEDjn9E";
	public final static String OAUTH_ACCESS_TOKEN = "145084142-F54lBJdshyuLHf43ROpsUqzYt2NIbVqewjLqVdDu";
	public final static String OAUTH_ACCESS_TOKEN_SECRET = "XKCKw6YkZknPXR9A1PgjjiJzQf0MkWBIsz2pobN3VI";

	public StreamingTracker() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws TwitterException {

		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();

		twitterStream.setOAuthConsumer(OAUTH_CONSUMER_KEY,
				OAUTH_CONSUMER_SECRET);

		AccessToken accessToken = new AccessToken(OAUTH_ACCESS_TOKEN,
				OAUTH_ACCESS_TOKEN_SECRET);

		twitterStream.setOAuthAccessToken(accessToken);

		MongoConnection mongo=new MongoConnection();
		mongo.setCollectionName("stream_tweets");
		mongo.setupMongo();
		

		StreamListener listener = new StreamListener(mongo);
		
		listener.setupLanguageDetector();
		



		// Para filtrar 

		FilterQuery fq = new FilterQuery();
		String keywords[] = {"brazil2014","worldcup","#Brazil2014","#WorldCup", "#fifa", "#fifaworldcup" };
			
		
		
		
		fq.track(keywords);





		twitterStream.addListener(listener);
		twitterStream.filter(fq); 






	}

}
