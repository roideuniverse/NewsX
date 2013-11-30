package ir.search;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

public class LocationFinder {
	
	public List<String> getLocation(String text)
	{
		List<String> lstLocation=new ArrayList<>();
		try {
			
			InputStream modelIn = new FileInputStream(FileUtil.getPath()+"en-token.bin");
			   TokenizerModel tokenModel = new TokenizerModel(modelIn);
			   modelIn.close();
			   Tokenizer tokenizer = new TokenizerME(tokenModel);
			   NameFinderME nameFinder =
			      new NameFinderME(
			         new TokenNameFinderModel(new FileInputStream(FileUtil.getPath()+"en-ner-location.bin")));
			   String tokens[] = tokenizer.tokenize(text);
			   Span nameSpans[] = nameFinder.find(tokens);
			   for( int i = 0; i<nameSpans.length; i++) {
			      //System.out.println("Span: "+nameSpans[i].toString());
			      //System.out.println("Covered text is: "+tokens[nameSpans[i].getStart()] + " " + tokens[nameSpans[i].getStart()+1]);
			      if((nameSpans[i].getEnd()-nameSpans[i].getStart())==1)
			    	  {
			    	  	lstLocation.add(tokens[nameSpans[i].getStart()]);
			    	  }
			      else if((nameSpans[i].getEnd()-nameSpans[i].getStart())==2)
			      {
			    	  
			    	  lstLocation.add(tokens[nameSpans[i].getStart()]+ " "+ tokens[nameSpans[i].getStart()+1]);
			      }
			   }
			}
			catch(Exception e) {
			   System.out.println("Error in LocationFinder.getLocation.Message:"+e.getMessage());
			}
		return lstLocation;
	}
	
	public static void findName() throws IOException {
		InputStream is = new FileInputStream("/home/rahul/project3/en-ner-person.bin");
 
		TokenNameFinderModel model = new TokenNameFinderModel(is);
		is.close();
 
		NameFinderME nameFinder = new NameFinderME(model);
 
		String []sentence = new String[]{
			    "Mike",
			    "Smith",
			    "is",
			    "a",
			    "good",
			    "person"
			    };
 
			Span nameSpans[] = nameFinder.find(sentence);
 
			for(Span s: nameSpans)
				System.out.println(s.toString());
 
	}

	
}
