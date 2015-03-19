

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
		System.out.println("inside doGet of readServlet");
		int cnt=100;
		String[] timeStamp={"2015-02-08 14:59:30.252","2014-10-07 18:59:30.252","2014-12-12 15:59:30.252","2010-03-08 14:59:30.252"};
		int r=0;
		String value="";
		String line="";
		int count=0;
		BufferedReader br=new BufferedReader(new FileReader("/home/adminuser/Downloads/emails_Karan.CSV"));
		//for(int i=0;i<10;i++)
		br.readLine();
		while((line=br.readLine())!=null){
			String[] data=line.split(",");
			r = (int) (Math.random() * (2 - 0)) + 0;
			try{
			//System.out.println("Subject "+data[0]+" From Name: "+data[1]+" From Address: "+data[2]+" To Name: "+data[4]+" To Address: "+data[5]);
			value="{\"sub\": [{\"subject\": "+"\""+data[0]+"\""+"}],\"addresses\": [{\"from_name\": "+"\""+data[1]+"\""+",\"from_address\": "+"\""+data[2]+"\""+"},{\"to_name\": "+"\""+data[4]+"\""+",\"to_address\": "+"\""+data[5]+"\""+"}],\"time\": "+"\""+timeStamp[r]+"\""+"}";
			//System.out.println(value);
			System.out.println("--------------------");
			insertdb(value,cnt);
			//break;
			cnt++;
			//break;
			if(cnt==20)
				break;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}

		
		
	}
private static void insertdb(String value,int cnt) throws IOException{
		
		URL url=new URL("http://127.0.0.1:5984/emailrepo/"+java.util.UUID.randomUUID());
	HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
	httpCon.setDoOutput(true);
	httpCon.setRequestMethod("PUT");
	System.out.println("URL "+url.toString());
	OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
    //out.write("{\"sub\": [{\"subject\": \"karan's email\"}],\"addresses\": [{\"from_name\": \"spam filter\",\"from_address\": \"xyz@spam.com\"},{\"to_name\": \"test user\",\"to_address\": \"test@gmail.com\"}],\"time\": \"2010-03-08 14:59:30.252\"	}");
	System.out.println("value "+value+" URL "+url);

	out.write(value);
    out.close();
    httpCon.getInputStream();
    /*try {
        Thread.sleep(2000);                 //1000 milliseconds is one second.
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
