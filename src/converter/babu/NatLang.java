	import com.google.cloud.language.v1.AnalyzeEntitiesRequest;
	import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
	import com.google.cloud.language.v1.Document;
	import com.google.cloud.language.v1.Document.Type;
	import com.google.cloud.language.v1.EncodingType;
	import com.google.cloud.language.v1.Entity;
	import com.google.cloud.language.v1.LanguageServiceClient;

	
	public class NatLang {
			public static void main (String[]args) {
			
			}
	 
	  public static String analyzeEntitiesText(String text) throws Exception {
	   
	    try (LanguageServiceClient language = LanguageServiceClient.create()) {
	      Document doc = Document.newBuilder()
	          .setContent(text)
	          .setType(Type.PLAIN_TEXT)
	          .build();
	      AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
	          .setDocument(doc)
	          .setEncodingType(EncodingType.UTF16)
	          .build();

	      AnalyzeEntitiesResponse response = language.analyzeEntities(request);
	     
	     
	      for (Entity entity : response.getEntitiesList()) {
	       return entity.getType().toString();
	      
	      }
	    }
		return "";
	   
	  }
}
