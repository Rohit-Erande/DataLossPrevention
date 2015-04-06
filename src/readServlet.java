

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class readServlet
 */
@WebServlet("/readServlet")
public class readServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public readServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String value="";
		String line="";
		BufferedReader br=new BufferedReader(new FileReader("/home/adminuser/Downloads/uploads/rohit_csv.csv"));
		br.readLine();
		while((line=br.readLine())!=null){
			String[] data=line.split(",");
			Pattern stopWords = Pattern.compile("\\b(?:i|a|and|about|an|are|that|it|you|your|if|for|on|are|with|as|he|was|is|from|the|to|u|of|this|will|in|our)\\b\\s*", Pattern.CASE_INSENSITIVE);
			try{
				Matcher matcher = stopWords.matcher(data[0]);
				String temp = matcher.replaceAll("");
				value="{\"subject\": "+"\""+temp+"\""+",\"from_name\": "+"\""+data[1]+"\""+",\"from_address\": "+"\""+data[2]+"\""+",\"to_name\": "+"\""+data[3]+"\""+",\"to_address\": "+"\""+data[4]+"\""+",\"time\": "+"\""+data[5]+"\""+",\"ethernet_dest\": "+"\""+data[6]+"\""+",\"ethernet_source\": "+"\""+data[7]+"\""+",\"Source_IP\": "+"\""+data[8]+"\""+",\"Dest_IP\": "+"\""+data[9]+"\""+",\"Protocol\": "+"\""+data[10]+"\""+"}";
				insertdb(value);
				//System.out.println(value);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
private static void insertdb(String value) throws IOException{
				URL url=new URL("http://127.0.0.1:5984/emailrepo/"+java.util.UUID.randomUUID());
				//System.out.println(url);
	HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	httpCon.setDoOutput(true);
	httpCon.setRequestMethod("PUT");
	System.out.println("URL "+url.toString());
	OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
   out.write(value);
    out.close();
    httpCon.getInputStream();
    /*try {
        Thread.sleep(100);                 //1000 milliseconds is one second.
    } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
    }*/
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("inside doPost of readServlet");
	}

}
