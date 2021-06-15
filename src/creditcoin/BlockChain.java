package creditcoin;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class BlockChain {

//	public static String Store = "http://10.0.0.42:5000/transactions/new";
//	public static String Mine = "http://10.0.0.42:5000/mine";
//	public static String Retrive = "http://10.0.0.42:5000/chain";
	public static String Store = "transactions/new";
	public static String Mine = "mine";
	public static String Retrive = "chain";


	public static String addTransaction(String jsonInput, String urlLink) {
		try {
			URL url = new URL(urlLink);
			HttpURLConnection httpCon = (HttpURLConnection) url
					.openConnection();
			httpCon.setRequestProperty("Content-Type", "application/json");
			httpCon.setRequestMethod("POST");
			httpCon.setDoInput(true);
			httpCon.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(httpCon
					.getOutputStream());
			out.write(jsonInput);
			out.close();
			System.out.println(httpCon.getResponseCode());
			System.out.println(httpCon.getResponseMessage());
			InputStreamReader ir = new InputStreamReader(httpCon
					.getInputStream());
			char b[] = new char[1024];
			int l = 0;
			while (ir.ready()) {
				l = ir.read(b);
				System.out.println(new String(b, 0, l));
			}
		} catch (Exception e) {
			return "no";
		}
		return "ok";
	}

	public static void mineChain(String urlLink) {
		try {
			URL url = new URL(urlLink);
			HttpURLConnection httpCon = (HttpURLConnection) url
					.openConnection();
			httpCon.setDoOutput(false);
			httpCon.setRequestMethod("GET");
			System.out.println(httpCon.getResponseCode());
			System.out.println(httpCon.getResponseMessage());
			InputStreamReader ir = new InputStreamReader(httpCon
					.getInputStream());
			char b[] = new char[1024];
			int l = 0;
			while (ir.ready()) {
				l = ir.read(b);
				System.out.println(new String(b, 0, l));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
