package com.example.boox;
import java.util.List;

import librosGoogle.BookAPI;
import librosGoogle.BookList;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class RequestAPI extends Activity {

	List<BookAPI> libros;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.request_api);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	public class Async extends AsyncTask<Void, Void, Void> {

		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub

					PruebaLibro();
				return null;
		}
		
		protected void onPostExecute(Void result) {
			
			if(libros!=null){
				TextView text1, text2, text3, text4, text5;
				text1 = (TextView) findViewById(R.id.textView1);
		    	text1.setText(libros.get(0).getVolumeInfo().getTitle());
		    	text2 = (TextView) findViewById(R.id.textView2);
		    	text2.setText(libros.get(1).getVolumeInfo().getTitle());
			}
			else{
				Toast.makeText(getApplicationContext(), "Mal leido", Toast.LENGTH_LONG).show();
			}
		}
	
	}
	
	public List<BookAPI> PruebaLibro(){
		    final String responseString = "{\"kind\": \"books#volumes\", \"totalItems\": 188, \"items\": [{\"kind\": \"books#volume\", \"id\": \"zyTCAlFPjgYC\", \"etag\": \"f0zKg75Mx/I\",\"selfLink\": \"https://www.googleapis.com/books/v1/volumes/zyTCAlFPjgYC\", \"volumeInfo\": {\"title\": \"The Google story\",\"authors\": [\"David A. Vise\",\"Mark Malseed\"],\"publisher\": \"Random House Digital, Inc.\",\"publishedDate\": \"2005-11-15\",\"description\": \"Here is the story behind one of the most remarkable Internet successes of our time. Based on scrupulous research and extraordinary accessto Google, ...\",\"industryIdentifiers\": [{\"type\": \"ISBN_10\",\"identifier\": \"055380457X\"},{\"type\": \"ISBN_13\",\"identifier\": \"9780553804577\"} ],\"pageCount\": 207,\"dimensions\": {\"height\": \"24.00 cm\",\"width\": \"16.03 cm\",\"thickness\": \"2.74 cm\"},\"printType\": \"BOOK\",\"mainCategory\": \"Business & Economics / Entrepreneurship\",\"categories\": [ \"Browsers (Computer programs)\",...],\"averageRating\": 3.5,\"ratingsCount\": 136,\"contentVersion\": \"1.1.0.0.preview.2\",\"imageLinks\": { \"smallThumbnail\": \"http://bks1.books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\"thumbnail\": \"http://bks1.books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\",\"small\": \"http://bks1.books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=2&edge=curl&source=gbs_api\",\"medium\": \"http://bks1.books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=3&edge=curl&source=gbs_api\",\"large\": \"http://bks1.books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=4&edge=curl&source=gbs_api\",  \"extraLarge\": \"http://bks1.books.google.com/books?id=zyTCAlFPjgYC&printsec=frontcover&img=1&zoom=6&edge=curl&source=gbs_api\"}, \"language\": \"en\",\"infoLink\": \"http://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&source=gbs_api\",\"canonicalVolumeLink\": \"http://books.google.com/books/about/The_Google_story.html?id=zyTCAlFPjgYC\"},\"saleInfo\": {\"country\": \"US\",\"saleability\": \"FOR_SALE\",\"isEbook\": true,\"listPrice\": {\"amount\": 11.99, \"currencyCode\": \"USD\"},\"retailPrice\": {\"amount\": 11.99,\"currencyCode\": \"USD\"},\"buyLink\": \"http://books.google.com/books?id=zyTCAlFPjgYC&ie=ISO-8859-1&buy=&source=gbs_api\"},\"accessInfo\": {\"country\": \"US\",\"viewability\": \"PARTIAL\",\"embeddable\": true,\"publicDomain\": false,\"textToSpeechPermission\": \"ALLOWED_FOR_ACCESSIBILITY\",\"epub\": {\"isAvailable\": true, \"acsTokenLink\": \"http://books.google.com/books/download/The_Google_story-sample-epub.acsm?id=zyTCAlFPjgYC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"},\"pdf\": {\"isAvailable\": false},\"accessViewStatus\": \"SAMPLE\"}},{   \"kind\": \"books#volume\",   \"id\": \"8Pr_kLFxciYC\",   \"etag\": \"OLz39rNcgfc\",   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/8Pr_kLFxciYC\",   \"volumeInfo\": {    \"title\": \"Flowers For Algernon\",    \"authors\": [     \"Daniel Keyes\"    ],    \"publisher\": \"Gateway\",    \"publishedDate\": \"2012-11-15\",    \"description\": \"Charlie Gordon, IQ 68, is a floor sweeper, and the gentle butt of everyone's jokes, until an experiment in the enhancement of human intelligence turns him into a genius. But then Algernon, the mouse whose triumphal experimental tranformation preceded his, fades and dies, and Charlie has to face the possibility that his salvation was only temporary.\",    \"industryIdentifiers\": [     {      \"type\": \"ISBN_10\",      \"identifier\": \"0575088494\"     },{      \"type\": \"ISBN_13\",      \"identifier\": \"9780575088498\"     }    ],    \"pageCount\": 224,    \"printType\": \"BOOK\",    \"averageRating\": 4.0,    \"ratingsCount\": 722,    \"contentVersion\": \"preview-1.0.0\",    \"imageLinks\": {     \"smallThumbnail\": \"http://bks7.books.google.es/books?id=8Pr_kLFxciYC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",     \"thumbnail\": \"http://bks7.books.google.es/books?id=8Pr_kLFxciYC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"    },    \"language\": \"en\",    \"previewLink\": \"http://books.google.es/books?id=8Pr_kLFxciYC&printsec=frontcover&dq=flowers+inauthor:keyes&hl=&cd=1&source=gbs_api\",    \"infoLink\": \"http://books.google.es/books?id=8Pr_kLFxciYC&dq=flowers+inauthor:keyes&hl=&source=gbs_api\",    \"canonicalVolumeLink\": \"http://books.google.es/books/about/Flowers_For_Algernon.html?hl=&id=8Pr_kLFxciYC\"   },   \"saleInfo\": {    \"country\": \"ES\",    \"saleability\": \"FOR_SALE\",    \"isEbook\": true,    \"listPrice\": {     \"amount\": 3.99,     \"currencyCode\": \"EUR\"    },    \"retailPrice\": {     \"amount\": 3.99,     \"currencyCode\": \"EUR\"    },    \"buyLink\": \"http://books.google.es/books?id=8Pr_kLFxciYC&dq=flowers+inauthor:keyes&hl=&buy=&source=gbs_api\"   },   \"accessInfo\": {    \"country\": \"ES\",    \"viewability\": \"PARTIAL\",    \"embeddable\": true,    \"publicDomain\": false,    \"textToSpeechPermission\": \"ALLOWED_FOR_ACCESSIBILITY\",    \"epub\": {\"isAvailable\": true,     \"acsTokenLink\": \"http://books.google.es/books/download/Flowers_For_Algernon-sample-epub.acsm?id=8Pr_kLFxciYC&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"    },    \"pdf\": {     \"isAvailable\": false    },    \"webReaderLink\": \"http://books.google.es/books/reader?id=8Pr_kLFxciYC&hl=&printsec=frontcover&output=reader&source=gbs_api\",    \"accessViewStatus\": \"SAMPLE\"   },   \"searchInfo\": {    \"textSnippet\": \"Charlie Gordon, IQ 68, is a floor sweeper, and the gentle butt of everyone&#39;s jokes, until an experiment in the enhancement of human intelligence turns him into a genius.\"   }  },  {   \"kind\": \"books#volume\",   \"id\": \"F1wgqlNi8AMC\",   \"etag\": \"NtFL3qlFIBc\",   \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/F1wgqlNi8AMC\",   \"volumeInfo\": {    \"title\": \"Flowers for Algernon\",    \"subtitle\": \"Full\",    \"authors\": [     \"David Rogers\",     \"Daniel Keyes\"    ],    \"publisher\": \"Dramatic Publishing\",    \"publishedDate\": \"1969-01-01\",    \"description\": \"The compelling story of Charlie Gordon, willing victim of a strange experiment - a moron, a genius, a man in search of himself. Poignant, funny, tragic, but with a hope for the indomitable spirit of man, this unusual play tells a story you will long remember. It also offers a magnificent role.\",    \"industryIdentifiers\": [     {      \"type\": \"ISBN_10\",      \"identifier\": \"0871295377\"     },     {      \"type\": \"ISBN_13\",      \"identifier\": \"9780871295378\"     }    ],    \"pageCount\": 117,    \"printType\": \"BOOK\",    \"contentVersion\": \"0.0.1.0.preview.0\",    \"imageLinks\": {     \"smallThumbnail\": \"http://bks5.books.google.es/books?id=F1wgqlNi8AMC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",     \"thumbnail\": \"http://bks5.books.google.es/books?id=F1wgqlNi8AMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"    },    \"language\": \"en\",    \"previewLink\": \"http://books.google.es/books?id=F1wgqlNi8AMC&printsec=frontcover&dq=flowers+inauthor:keyes&hl=&cd=2&source=gbs_api\",    \"infoLink\": \"http://books.google.es/books?id=F1wgqlNi8AMC&dq=flowers+inauthor:keyes&hl=&source=gbs_api\",    \"canonicalVolumeLink\": \"http://books.google.es/books/about/Flowers_for_Algernon.html?hl=&id=F1wgqlNi8AMC\"   },   \"saleInfo\": {    \"country\": \"ES\",    \"saleability\": \"NOT_FOR_SALE\",    \"isEbook\": false   },   \"accessInfo\": {    \"country\": \"ES\",    \"viewability\": \"PARTIAL\",    \"embeddable\": true,    \"publicDomain\": false,    \"textToSpeechPermission\": \"ALLOWED\",    \"epub\": {     \"isAvailable\": false    },    \"pdf\": {     \"isAvailable\": false    },    \"webReaderLink\": \"http://books.google.es/books/reader?id=F1wgqlNi8AMC&hl=&printsec=frontcover&output=reader&source=gbs_api\",    \"accessViewStatus\": \"SAMPLE\"   }   }] }";
		 
			Gson gson = new Gson();			
			BookList detailbook  = gson.fromJson(responseString, BookList.class);
			libros = detailbook.getItems();
			return libros;
	}
	
	public void BotonClick(View view) {
        // Do something in response to button

		Async fr = new Async();
		fr.execute(null, null, null);
			
    }
	
	/*
	public void CogerLibro() {

			HttpClient client = new DefaultHttpClient();  
            HttpGet request = new HttpGet("https://www.googleapis.com/books/v1/volumes/fYa_hJ4bWnsC?key=AIzaSyC9DevpMpZeWSTZFBwjhzql2iJKvpVwF7M"); 
            request.setHeader("Accept","application/json");
    		HttpResponse response = null;
    		try {
    			response = client.execute(request);
    			String responseString = null;
    			HttpEntity entity = response.getEntity(); 
    			if (entity != null) { 
    				InputStream stream = entity.getContent();
    				BufferedReader reader = new BufferedReader(new InputStreamReader(stream)); 
    				StringBuilder sb = new StringBuilder(); 
    				String line = null; 
    				while ((line = reader.readLine()) != null) { 
    					sb.append(line + "\n"); 
    				}
    				stream.close();
    				responseString = sb.toString(); 
    			}

    			GsonBuilder builder = new GsonBuilder(); 
    			Gson gson = builder.create(); 
    			JSONObject json = new JSONObject(responseString);

    			
    			BookAPI detailbook  = gson.fromJson(json.toString(), BookAPI.class);
    			//HighScoreList puntuacionesAmigos = gson.fromJson(json.toString(), HighScoreList.class);
    			
    		}
    		
	}
	}
	
	/*void escribir_XML() throws IllegalArgumentException, IllegalStateException, IOException{
		FileOutputStream fout = null;
		
		fout = null;
		
		XmlSerializer serializer = Xml.newSerializer();
		try{
			serializer.setOutput(fout, "UTF-8"); // indicamos el fichero en el que escribir
			serializer.startDocument(null, true); // indica que vamos a empezar a escribir aki
			serializer.setFeature("http://xmlpull.org/v1/doc/features.html #indent-output", true);
		} catch (Exception e){
			Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
		}
		
		serializer.startTag(null, "usuario");
			serializer.attribute(null, "id", "50");
			serializer.attribute(null, "nombre", "Bea");
			
			serializer.startTag(null, "libros");
			
				serializer.startTag(null, "libro");
					serializer.attribute(null, "Nombre", "La sombra del viento");
					serializer.attribute(null, "Autor", "Carlos Ruiz Zafon");
					serializer.attribute(null, "isbn", "101");
				serializer.endTag(null, "libro");
				
				serializer.startTag(null, "libro");
					serializer.attribute(null, "Nombre", "La catedral del mar");
					serializer.attribute(null, "Autor", "Ildefonso falcones");
					serializer.attribute(null, "isbn", "102");
				serializer.endTag(null, "libro");
			
			serializer.endTag(null, "libros");
			
			serializer.startTag(null, "amigos");
			
				serializer.startTag(null, "amigo");
					serializer.attribute(null, "nombre", "Javi");
				serializer.endTag(null,"amigo");
				
				serializer.startTag(null, "amigo");
					serializer.attribute(null, "nombre", "Celia");
				serializer.endTag(null,"amigo");
		
				serializer.startTag(null, "amigo");
					serializer.attribute(null, "nombre", "Danay");
				serializer.endTag(null,"amigo");
			
			serializer.endTag(null, "amigos");
		
		serializer.endTag(null, "usuario");
		
		serializer.endDocument();
		serializer.flush();
		fout.close();
		
		Toast.makeText(getApplicationContext(), "Hecho!", Toast.LENGTH_LONG).show();
		
		
		
		
		
		
	}*/
	

}
