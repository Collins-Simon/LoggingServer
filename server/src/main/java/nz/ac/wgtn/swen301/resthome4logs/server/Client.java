package nz.ac.wgtn.swen301.resthome4logs.server;

import java.io.IOException;

import okhttp3.OkHttpClient;

public class Client {
	OkHttpClient client = new OkHttpClient();
	public static void main(String[] args) {
		new Client().run(args[0]);
	}

	private String run(String url)  {
	  Request request = new Request.Builder()
	      .url(url)
	      .build();

	  try (Response response = client.newCall(request).execute()) {
	    return response.body().string();
	  }
	}
}
