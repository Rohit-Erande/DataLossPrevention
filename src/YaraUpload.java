
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.KStemFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.util.CharArraySet;

/**
 * Servlet implementation class YaraUpload
 */
@WebServlet("/YaraUpload")
public class YaraUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "C:/Users/Poojitha/Downloads/uploads";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public YaraUpload() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside doPost");
		String name = null;
		if (ServletFileUpload.isMultipartContent(request)) {

			try {
				List<FileItem> multiparts = new ServletFileUpload(
						new DiskFileItemFactory()).parseRequest(request);

				for (FileItem item : multiparts) {
					if (!item.isFormField()) {
						name = new File(item.getName()).getName();
						System.out.println(name);
						item.write(new File(UPLOAD_DIRECTORY + File.separator
								+ name));
					}
				}
				request.setAttribute("message", "File Uploaded Successfully");
				// response.sendRedirect("./readServlet");
				executeScript(
						"C:/Users/Poojitha/Downloads/DLP/DataLossPrevention-master/icmp.bat",
						UPLOAD_DIRECTORY + "/" + name);
				GenerateFile("C:/Users/Poojitha/Downloads/DLP/DataLossPrevention-master/yara-output.txt");

			} catch (Exception ex) {
				request.setAttribute("message", "File Upload Failed due to "
						+ ex);
			}
		} else {
			request.setAttribute("message",
					"Sorry this Servlet only handles file upload request");
		}

	}

	public static String executeScript(String file, String filepath) {
		StringBuffer output = new StringBuffer();

		Process p;

		try {
			String command = file + " " + filepath;
			System.out.println("before command execure   ->" + command);
			p = Runtime.getRuntime().exec(command);

			int exitval = p.waitFor();

			System.out.println("Backup exitval: " + exitval);

			BufferedReader reader =

			new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			// Protocol /home/anusha/Desktop/new/packet_00517_20111130012332
			while ((line = reader.readLine()) != null) {

				output.append(line + "\n");
				// System.out.println(output.toString());

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return output.toString();

	}

	public static void GenerateFile(String filepath) {
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(filepath));
			System.out.println(filepath);
			// StringBuilder result= new StringBuilder();

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println("inside while");
				String split[] = sCurrentLine.trim().split(" ");
				// System.out.println(split[1].substring(40, 45));
				int packet_num = Integer.parseInt(split[1].substring(40, 45)) + 1;
				// System.out.println("packet_num"+packet_num);

				System.out.println("Before header.bat");
				String CurrentLine = executeScript(
						"C:/Users/Poojitha/Downloads/DLP/DataLossPrevention-master/header.bat "
								+ packet_num, "");
				// System.out.println("packet-out: "+CurrentLine);
				CurrentLine = CurrentLine.trim();
				CurrentLine = CurrentLine.replace(" ->", "");
				String res[] = CurrentLine.split(" ");
				/*
				 * System.out.println("count"); int i=0; for (String r:res) {
				 * System.out.println(i+ " " +r); i++; }
				 */

				// String res[] =sc.readFile("/home/anusha/Desktop/out.txt");
				String value = "";
				System.out.println(res[5]);
				if (res[5].equalsIgnoreCase("")) {
					// line= res[0]+ ","+res[1]+" "+res[2]+","+res[3]+
					// ","+res[4]+ ","+res[6]+ ","+split[0];
					value = "{\"Packet_no\": " + "\"" + res[12] + "\""
							+ ",\"timestamp\": " + "\"" + res[15] + "\""
							+ ",\"Source_IP\": " + "\"" + res[16] + "\""
							+ ",\"Destination_IP\": " + "\"" + res[17] + "\""
							+ ",\"Protocol\": " + "\"" + res[18] + "\""
							+ ",\"Attack\": " + "\"" + split[0] + "\"" + "}";
				} else {
					// line= res[0]+ ","+res[1]+" "+res[2]+","+res[3]+
					// ","+res[4]+ ","+res[5]+ ","+split[0];
					value = "{\"Packet_no\": " + "\"" + res[12] + "\""
							+ ",\"timestamp\": " + "\"" + res[15] + "\""
							+ ",\"Source_IP\": " + "\"" + res[16] + "\""
							+ ",\"Destination_IP\": " + "\"" + res[17] + "\""
							+ ",\"Protocol\": " + "\"" + res[18] + "\""
							+ ",\"Attack\": " + "\"" + split[0] + "\"" + "}";
				}
				// result.append(line +"\n");
				System.out.println("before insertDB");
				System.out.println(value);
				insertdb(value);

			}
			// System.out.print(result.toString());
			// sc.writeFile(result.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void insertdb(String value) throws IOException {
		URL url = new URL("http://localhost:5984/attackrepo/"
				+ java.util.UUID.randomUUID());
		System.out.println(url);
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		System.out.println("URL == " + url.toString());
		OutputStreamWriter out = new OutputStreamWriter(
				httpCon.getOutputStream());
		out.write(value);
		out.close();
		httpCon.getInputStream();
	}

}
