package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

public class Client {
	public static void main(String[] args) {
		doget(args[0], args[1]);
	}
	private static void doget(String filetype, String filename) {
		//System.out.println("\"" + filetype + "\"");
		HttpRequestFactory requestFactory
		= new NetHttpTransport().createRequestFactory();
		HttpRequest request;
		HttpResponse rawResponse = null;
		if(filetype.equals("excel")) {
			filetype = "xls";
			System.out.println("Doesn't work sorry, try csv instead it's better anyway");
			return;
		}
		try {
			request = requestFactory.buildGetRequest(
					new GenericUrl("http://localhost:8080/resthome4logs/stats" + filetype));
			rawResponse = request.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		if(filetype.equals("csv")) {
			String csvToExport = null;
			try {
				csvToExport = rawResponse.parseAsString();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				FileWriter output = new FileWriter(filename);
				output.write(csvToExport);
				output.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				return;
			}
		}
		XSSFWorkbook file = null;
		try {
			file = rawResponse.parseAs(XSSFWorkbook.class);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			FileOutputStream output = new FileOutputStream(filename);
			file.write(output);
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}
