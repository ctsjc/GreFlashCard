package enc.gre.flahcard.greflashcard.internal;

import java.util.StringTokenizer;

import enc.gre.flahcard.greflashcard.dataType.Dict;
import enc.gre.flahcard.greflashcard.deck.DeckEngine;

import android.util.Log;
import android.view.Gravity;
import android.webkit.WebView;
import android.widget.TextView;

public class TextFormatter {
	String hdata;
	String hfont;
	String hText;

	String hPre = "<html xmlns="+'"'+"http://www.w3.org/1999/xhtml"+'"'+">"+
			"<head>"+
			"<title>Vertical Centering Using a Table Container</title>"+
			"<style type="+'"'+"text/css"+'"'+">"+
			"* {padding: 0;margin: 0;}" +
			"html, body {	height: 100%;}" +
			"p{font-size: 40px;}" +
			"table {	vertical-align: middle;	height: 100%;	margin: 0 auto;}"+
			"</style>" +
			"</head>" +
			"<body><table><tr><td><div>" ;

	String hEnd = "</div></td></tr></table></body></html>";
	public void setText(final boolean showWord,final TextView textView,final  String text){
		Log.e("TextFormat", String.valueOf( showWord));
		if(showWord){ 
			/* if word is going to be displayed
			 * Then font must be 25
			 * Centered aligned
			 * First alphabet should be capital 
			 */
			textView.setTextSize(50);	
			textView.setText(text);

		}//end of if
		else{
			textView.setGravity(Gravity.LEFT);
			textView.setTextSize(25);
			textView.setText("\t"+text);
			// format the text
		}//end of else

	}//end of formatText

	public void setText(final WebView view){
		//		MainActivityLogic activityLogic = MainActivityLogic.getInstance();
		DeckEngine activityLogic=DeckEngine.getInstance();
		boolean showWord = activityLogic.isShowWord();

		if(showWord){ 
			String text = activityLogic.currWord();
			if(text ==null){
				text= "Resource not found, check the Deck";
			}
			hfont="50";
			hText=
					"<html xmlns="+'"'+"http://www.w3.org/1999/xhtml"+'"'+">"+
							"<head>"+
							"<title>Vertical Centering Using a Table Container</title>"+
							"<style type="+'"'+"text/css"+'"'+">"+
							"* {padding: 0;margin: 0;}" +
							"html, body {	height: 100%;}" +
							"p{font-size: 40px;}" +
							"table {	vertical-align: middle;	height: 100%;	margin: 0 auto;}"+
							"</style>" +
							"</head>" +
							"<body><table><tr><td><div>" +
							"<p>"+
							text+
							"</p>" +
							"</div></td></tr></table></body></html>";
			view.loadDataWithBaseURL( null, hText,"text/html", "UTF-8",null);
		}//end of if
		else{
			String text = activityLogic.currWord();
			if(text ==null){
				text= "Resource not found, check the Deck";
			}
			String currMeaning = activityLogic.currMeaning();
			String meaning = null  ;
			if(currMeaning !=null){
				StringTokenizer stringTokenizer = new StringTokenizer(currMeaning,";");
				while (stringTokenizer.hasMoreElements()) {
					if(meaning == null)
						meaning= "<li>"+stringTokenizer.nextElement().toString()+"</li>" ;
					else
						meaning+= "<li>"+stringTokenizer.nextElement().toString()+"</li>" ;
				}
			}
			if(meaning == null){
				meaning = "Resource not found, check the Deck";
			}

			hText ="<html xmlns="+'"'+"http://www.w3.org/1999/xhtml"+'"'+">"+
					"<head>"+
					"<style type="+'"'+"text/css"+'"'+">"+
					"* {padding: 0;margin: 0;}" +
					"html, body {	height: 100%;}" +
					"table {	vertical-align: left;	height: 100%;	margin: 0 auto;}"+
					"</style>" +
					"</head>" +
					"<body><table>"+
					"<tr>" +
					"<th><div><p  style="+'"'+"font-size: 20px;font-weight: bold;"+'"'+">"+
					"<font color="+'"'+"1F7C8F"+'"'+">"+
					text+"</p></div></th></tr>"+
					"<tr><td><div><p><datalist><option><ol  style="+'"'+"font-size: 28px;list-style-position: inside;"+'"'+">" +
					meaning+
					"</ol></option></datalist></p></div></td></tr>"+
					"<tr><td><div><p  style="+'"'+"font-size: 20px;font-style: italic;"+'"'+">"+"Hi this is sentence "+"</p></div></td></tr>"+
					"</table></body>	</html>";
			view.loadDataWithBaseURL( null, hText,"text/html", "UTF-8",null);
		}//end of else
	}//end of formatText
	
	public void showTable(final WebView view){
		//		MainActivityLogic activityLogic = MainActivityLogic.getInstance();
		DeckEngine activityLogic=DeckEngine.getInstance();
		StringBuffer word;
		StringBuffer meaning;
		StringBuffer text=new StringBuffer();
		//Get Word and meaning
		for(Dict d : activityLogic.getDictionary()){
			 word  = new StringBuffer(d.getWord());
			 meaning = new StringBuffer(d.getMeaning());
			text = text.append(
							"<tr>" +
							"<th><div><p  style="+'"'+"font-size: 20px;font-weight: bold;"+'"'+">"+
							"<font color="+'"'+"1F7C8F"+'"'+">"+
							word +
							"</th>"+
							"</tr>"+
							"<tr>" +
							"<th><div><p  style="+'"'+"font-size: 15px;"+'"'+">"+
							meaning+
							"</th>"+
							"</tr>"
			);
		}//end of for
		view.loadDataWithBaseURL( null, text.toString(),"text/html", "UTF-8",null);
	}//end of showTable
}//end of class
