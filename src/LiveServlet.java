

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Servlet implementation class LiveServlet
 */
@WebServlet("/LiveServlet")
public class LiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LiveServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
		System.out.println("inside do Post");
		String value="";
		String line="";
		BufferedReader br=new BufferedReader(new FileReader("C:/Users/Poojitha/Downloads/uploads/Cleaned Data with timestamp.csv"));
		br.readLine();
		while((line=br.readLine())!=null){
			String[] data=line.split(",");
			Pattern stopWords = Pattern.compile("\\b(?:i|a|and|about|an|are|that|it|you|your|if|for|on|are|with|as|he|was|is|from|the|to|u|of|this|will|in|our)\\b\\s*", Pattern.CASE_INSENSITIVE);
			try{
				/*
				 * Code using regex for pre-processing.
				 * Could not remove common stopwords. 
				 * 
				 * Matcher matcher = stopWords.matcher(data[0]);
				String temp = matcher.replaceAll("");*/
				
				System.out.println("Before Pre-processing --> "+data[0]);
				
				String temp = tokenizeStopStem(data[0]);
				
				System.out.println("After Pre-processing --> "+temp);
				
				
				//value="{\"subject\": "+"\""+temp+"\""+",\"from_name\": "+"\""+data[1]+"\""+",\"from_address\": "+"\""+data[2]+"\""+",\"to_name\": "+"\""+data[3]+"\""+",\"to_address\": "+"\""+data[4]+"\""+",\"time\": "+"\""+data[5]+"\""+",\"ethernet_dest\": "+"\""+data[6]+"\""+",\"ethernet_source\": "+"\""+data[7]+"\""+",\"Source_IP\": "+"\""+data[8]+"\""+",\"Dest_IP\": "+"\""+data[9]+"\""+"}";
				value="{\"subject\": "+"\""+temp+"\""+",\"from_name\": "+"\""+data[1]+"\""+",\"from_address\": "+"\""+data[2]+"\""+",\"to_name\": "+"\""+data[3]+"\""+",\"to_address\": "+"\""+data[4]+"\""+",\"time\": "+"\""+data[5]+"\""+",\"ethernet_dest\": "+"\""+data[6]+"\""+",\"ethernet_source\": "+"\""+data[7]+"\""+",\"Source_IP\": "+"\""+data[8]+"\""+",\"Dest_IP\": "+"\""+data[9]+"\""+",\"Protocol\": "+"\""+data[10]+"\""+"}";
				insertdb(value);
			}
			catch(Exception e){
				e.printStackTrace();
			}
	
		}
		
	}
	
	private static String tokenizeStopStem(String input) {
		
	      HashSet<String> words = new HashSet<String>();
	      
		  words.add("a");
		  words.add("able");
		  words.add("about");
		  words.add("above");
		  words.add("according");
		  words.add("accordingly");
		  words.add("across");
		  words.add("actually");
		  words.add("after");
		  words.add("afterwards");
		  words.add("again");
		  words.add("against");
		  words.add("all");
		  words.add("allow");
	      words.add("allows");
	      words.add("almost");
	      words.add("alone");
	      words.add("along");
	      words.add("already");
	      words.add("also");
	      words.add("although");
	      words.add("always");
	      words.add("am");
	      words.add("among");
	      words.add("amongst");
	      words.add("an");
	      words.add("and");
	      words.add("another");
	      words.add("any");
	      words.add("anybody");
	      words.add("anyhow");
	      words.add("anyone");
	      words.add("anything");
	      words.add("anyway");
	      words.add("anyways");
	      words.add("anywhere");
	      words.add("apart");
	      words.add("appear");
	      words.add("appreciate");
	      words.add("appropriate");
	      words.add("are");
	      words.add("around");
	      words.add("as");
	      words.add("aside");
	      words.add("ask");
	      words.add("asking");
	      words.add("associated");
	      words.add("at");
	      words.add("available");
	      words.add("away");
	      words.add("awfully");
	      words.add("b");
	      words.add("be");
	      words.add("became");
	      words.add("because");
	      words.add("become");
	      words.add("becomes");
	      words.add("becoming");
	      words.add("been");
	      words.add("before");
	      words.add("beforehand");
	      words.add("behind");
	      words.add("being");
	      words.add("believe");
	      words.add("below");
	      words.add("beside");
	      words.add("besides");
	      words.add("best");
	      words.add("better");
	      words.add("between");
	      words.add("beyond");
	      words.add("both");
	      words.add("brief");
	      words.add("but");
	      words.add("by");
	      words.add("c");
	      words.add("came");
	      words.add("can");
	      words.add("cannot");
	      words.add("cant");
	      words.add("cause");
	      words.add("causes");
	      words.add("certain");
	      words.add("certainly");
	      words.add("changes");
	      words.add("clearly");
	      words.add("co");
	      words.add("com");
	      words.add("come");
	      words.add("comes");
	      words.add("concerning");
	      words.add("consequently");
	      words.add("consider");
	      words.add("considering");
	      words.add("contain");
	      words.add("containing");
	      words.add("contains");
	      words.add("corresponding");
	      words.add("could");
	      words.add("course");
	      words.add("currently");
	      words.add("d");
	      words.add("definitely");
	      words.add("described");
	      words.add("despite");
	      words.add("did");
	      words.add("different");
	      words.add("do");
	      words.add("does");
	      words.add("doing");
	      words.add("done");
	      words.add("down");
	      words.add("downwards");
	      words.add("during");
	      words.add("e");
	      words.add("each");
	      words.add("edu");
	      words.add("eg");
	      words.add("eight");
	      words.add("either");
	      words.add("else");
	      words.add("elsewhere");
	      words.add("enough");
	      words.add("entirely");
	      words.add("especially");
	      words.add("et");
	      words.add("etc");
	      words.add("even");
	      words.add("ever");
	      words.add("every");
	      words.add("everybody");
	      words.add("everyone");
	      words.add("everything");
	      words.add("everywhere");
	      words.add("ex");
	      words.add("exactly");
	      words.add("example");
	      words.add("except");
	      words.add("f");
	      words.add("far");
	      words.add("few");
	      words.add("fifth");
	      words.add("first");
	      words.add("five");
	      words.add("followed");
	      words.add("following");
	      words.add("follows");
	      words.add("for");
	      words.add("former");
	      words.add("formerly");
	      words.add("forth");
	      words.add("four");
	      words.add("from");
	      words.add("further");
	      words.add("furthermore");
	      words.add("g");
	      words.add("get");
	      words.add("gets");
	      words.add("getting");
	      words.add("given");
	      words.add("gives");
	      words.add("go");
	      words.add("goes");
	      words.add("going");
	      words.add("gone");
	      words.add("got");
	      words.add("gotten");
	      words.add("greetings");
	      words.add("h");
	      words.add("had");
	      words.add("happens");
	      words.add("hardly");
	      words.add("has");
	      words.add("have");
	      words.add("having");
	      words.add("he");
	      words.add("hello");
	      words.add("help");
	      words.add("hence");
	      words.add("her");
	      words.add("here");
	      words.add("hereafter");
	      words.add("hereby");
	      words.add("herein");
	      words.add("hereupon");
	      words.add("hers");
	      words.add("herself");
	      words.add("hi");
	      words.add("him");
	      words.add("himself");
	      words.add("his");
	      words.add("hither");
	      words.add("hopefully");
	      words.add("how");
	      words.add("howbeit");
	      words.add("however");
	      words.add("i");
	      words.add("ie");
	      words.add("if");
	      words.add("ignored");
	      words.add("immediate");
	      words.add("in");
	      words.add("inasmuch");
	      words.add("inc");
	      words.add("indeed");
	      words.add("indicate");
	      words.add("indicated");
	      words.add("indicates");
	      words.add("inner");
	      words.add("insofar");
	      words.add("instead");
	      words.add("into");
	      words.add("inward");
	      words.add("is");
	      words.add("it");
	      words.add("its");
	      words.add("itself");
	      words.add("j");
	      words.add("just");
	      words.add("k");
	      words.add("keep");
	      words.add("keeps");
	      words.add("kept");
	      words.add("know");
	      words.add("knows");
	      words.add("known");
	      words.add("l");
	      words.add("last");
	      words.add("lately");
	      words.add("later");
	      words.add("latter");
	      words.add("latterly");
	      words.add("least");
	      words.add("less");
	      words.add("lest");
	      words.add("let");
	      words.add("like");
	      words.add("liked");
	      words.add("likely");
	      words.add("little");
	      words.add("ll"); //words.added to avoid words like you'll,I'll etc.
	      words.add("look");
	      words.add("looking");
	      words.add("looks");
	      words.add("ltd");
	      words.add("m");
	      words.add("mainly");
	      words.add("many");
	      words.add("may");
	      words.add("maybe");
	      words.add("me");
	      words.add("mean");
	      words.add("meanwhile");
	      words.add("merely");
	      words.add("might");
	      words.add("more");
	      words.add("moreover");
	      words.add("most");
	      words.add("mostly");
	      words.add("much");
	      words.add("must");
	      words.add("my");
	      words.add("myself");
	      words.add("n");
	      words.add("name");
	      words.add("namely");
	      words.add("nd");
	      words.add("near");
	      words.add("nearly");
	      words.add("necessary");
	      words.add("need");
	      words.add("needs");
	      words.add("neither");
	      words.add("never");
	      words.add("nevertheless");
	      words.add("new");
	      words.add("next");
	      words.add("nine");
	      words.add("no");
	      words.add("nobody");
	      words.add("non");
	      words.add("none");
	      words.add("noone");
	      words.add("nor");
	      words.add("normally");
	      words.add("not");
	      words.add("nothing");
	      words.add("novel");
	      words.add("now");
	      words.add("nowhere");
	      words.add("o");
	      words.add("obviously");
	      words.add("of");
	      words.add("off");
	      words.add("often");
	      words.add("oh");
	      words.add("ok");
	      words.add("okay");
	      words.add("old");
	      words.add("on");
	      words.add("once");
	      words.add("one");
	      words.add("ones");
	      words.add("only");
	      words.add("onto");
	      words.add("or");
	      words.add("other");
	      words.add("others");
	      words.add("otherwise");
	      words.add("ought");
	      words.add("our");
	      words.add("ours");
	      words.add("ourselves");
	      words.add("out");
	      words.add("outside");
	      words.add("over");
	      words.add("overall");
	      words.add("own");
	      words.add("p");
	      words.add("particular");
	      words.add("particularly");
	      words.add("per");
	      words.add("perhaps");
	      words.add("placed");
	      words.add("please");
	      words.add("plus");
	      words.add("possible");
	      words.add("presumably");
	      words.add("probably");
	      words.add("provides");
	      words.add("q");
	      words.add("que");
	      words.add("quite");
	      words.add("qv");
	      words.add("r");
	      words.add("rather");
	      words.add("rd");
	      words.add("re");
	      words.add("really");
	      words.add("reasonably");
	      words.add("regarding");
	      words.add("regardless");
	      words.add("regards");
	      words.add("relatively");
	      words.add("respectively");
	      words.add("right");
	      words.add("s");
	      words.add("said");
	      words.add("same");
	      words.add("saw");
	      words.add("say");
	      words.add("saying");
	      words.add("says");
	      words.add("second");
	      words.add("secondly");
	      words.add("see");
	      words.add("seeing");
	      words.add("seem");
	      words.add("seemed");
	      words.add("seeming");
	      words.add("seems");
	      words.add("seen");
	      words.add("self");
	      words.add("selves");
	      words.add("sensible");
	      words.add("sent");
	      words.add("serious");
	      words.add("seriously");
	      words.add("seven");
	      words.add("several");
	      words.add("shall");
	      words.add("she");
	      words.add("should");
	      words.add("since");
	      words.add("six");
	      words.add("so");
	      words.add("some");
	      words.add("somebody");
	      words.add("somehow");
	      words.add("someone");
	      words.add("something");
	      words.add("sometime");
	      words.add("sometimes");
	      words.add("somewhat");
	      words.add("somewhere");
	      words.add("soon");
	      words.add("sorry");
	      words.add("specified");
	      words.add("specify");
	      words.add("specifying");
	      words.add("still");
	      words.add("sub");
	      words.add("such");
	      words.add("sup");
	      words.add("sure");
	      words.add("t");
	      words.add("take");
	      words.add("taken");
	      words.add("tell");
	      words.add("tends");
	      words.add("th");
	      words.add("than");
	      words.add("thank");
	      words.add("thanks");
	      words.add("thanx");
	      words.add("that");
	      words.add("thats");
	      words.add("the");
	      words.add("their");
	      words.add("theirs");
	      words.add("them");
	      words.add("themselves");
	      words.add("then");
	      words.add("thence");
	      words.add("there");
	      words.add("thereafter");
	      words.add("thereby");
	      words.add("therefore");
	      words.add("therein");
	      words.add("theres");
	      words.add("thereupon");
	      words.add("these");
	      words.add("they");
	      words.add("think");
	      words.add("third");
	      words.add("this");
	      words.add("thorough");
	      words.add("thoroughly");
	      words.add("those");
	      words.add("though");
	      words.add("three");
	      words.add("through");
	      words.add("throughout");
	      words.add("thru");
	      words.add("thus");
	      words.add("to");
	      words.add("together");
	      words.add("too");
	      words.add("took");
	      words.add("toward");
	      words.add("towards");
	      words.add("tried");
	      words.add("tries");
	      words.add("truly");
	      words.add("try");
	      words.add("trying");
	      words.add("twice");
	      words.add("two");
	      words.add("u");
	      words.add("un");
	      words.add("under");
	      words.add("unfortunately");
	      words.add("unless");
	      words.add("unlikely");
	      words.add("until");
	      words.add("unto");
	      words.add("up");
	      words.add("upon");
	      words.add("us");
	      words.add("use");
	      words.add("used");
	      words.add("useful");
	      words.add("uses");
	      words.add("using");
	      words.add("usually");
	      words.add("uucp");
	      words.add("v");
	      words.add("value");
	      words.add("various");
	      words.add("ve"); //words.added to avoid words like I've,you've etc.
	      words.add("very");
	      words.add("via");
	      words.add("viz");
	      words.add("vs");
	      words.add("w");
	      words.add("want");
	      words.add("wants");
	      words.add("was");
	      words.add("way");
	      words.add("we");
	      words.add("welcome");
	      words.add("well");
	      words.add("went");
	      words.add("were");
	      words.add("what");
	      words.add("whatever");
	      words.add("when");
	      words.add("whence");
	      words.add("whenever");
	      words.add("where");
	      words.add("whereafter");
	      words.add("whereas");
	      words.add("whereby");
	      words.add("wherein");
	      words.add("whereupon");
	      words.add("wherever");
	      words.add("whether");
	      words.add("which");
	      words.add("while");
	      words.add("whither");
	      words.add("who");
	      words.add("whoever");
	      words.add("whole");
	      words.add("whom");
	      words.add("whose");
	      words.add("why");
	      words.add("will");
	      words.add("willing");
	      words.add("wish");
	      words.add("with");
	      words.add("within");
	      words.add("without");
	      words.add("wonder");
	      words.add("would");
	      words.add("would");
	      words.add("x");
	      words.add("y");
	      words.add("yes");
	      words.add("yet");
	      words.add("you");
	      words.add("your");
	      words.add("yours");
	      words.add("yourself");
	      words.add("yourselves");
	      words.add("z");
	      words.add("zero");
	      
	 CharArraySet stopwords = new CharArraySet(words, true);	
	 
	
	 
	 Analyzer analyzer = new StandardAnalyzer();
	 //Analyzer analyzer = new EnglishAnalyzer(stopwords);
	 
	 StringBuilder sb = new StringBuilder();
	 try{
	 TokenStream tokenStream = analyzer.tokenStream("contents", new StringReader(input));
	 tokenStream = new StopFilter(tokenStream, stopwords);
	 
	 //tokenStream = new PorterStemFilter(tokenStream);
	 
	 tokenStream = new KStemFilter(tokenStream);


 
 CharTermAttribute term = (CharTermAttribute) tokenStream.getAttribute(CharTermAttribute.class);
 //PorterStemmer stem = new PorterStemmer();

 
	tokenStream.reset();
     while (tokenStream.incrementToken()) {
    	 //stem.setCurrent(term.toString());
    	 //stem.stem();
    	 
         if (sb.length() > 0) {
             sb.append(" ");
         }
         sb.append(term.toString());
     }
     
  tokenStream.close();   
  analyzer.close();   
 }
 catch (IOException e){
     System.out.println(e.getMessage());
 }
	 
 return sb.toString();
}
	
	private static void insertdb(String value) throws IOException{
		URL url=new URL("http://localhost:5984/emailrepo/"+java.util.UUID.randomUUID());
		System.out.println(url);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		System.out.println("URL "+url.toString());
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(value);
		out.close();
		httpCon.getInputStream();
}
	
}
