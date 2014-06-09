

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;



// Contains a TwitterEntry object and applies different filters to it
public class TwitterEntryController {
	private TwitterEntry twitterEntry;


	public TwitterEntryController(TwitterEntry twitterEntry){
		this.twitterEntry=twitterEntry;
	}

	public TwitterEntry getTwitterEntry(){
		return this.twitterEntry;
	}




	// Detects the language of the message
	public void detectLanguage(){
		try {

			Detector detector=DetectorFactory.create();
			detector.append(this.twitterEntry.getStatus().getText());

			String lang=detector.detect();
			this.twitterEntry.getFeatures().put("detect_lang", lang);

		} catch (LangDetectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}





}
