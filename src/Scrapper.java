
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
public class Scrapper {
	public static void main(String args[])
	{
        try {
        	ebay("rolex", 2);
        } catch(IOException ie) {
            System.out.println("Connection error");
        } 

    }
	
	public static void ebay(String itemName, int pageNumber) throws IOException
	{
		ArrayList<Double> accPrice = new ArrayList<Double>();
		for (int j=1;j<=pageNumber;j++)
		{
			
			Document doc = Jsoup.connect("https://www.ebay.co.uk/sch/i.html?_from=R40&_nkw="+itemName+"&LH_TitleDesc=0&_ipg=200&_pgn="+j).get();
			System.out.println(doc.title()+"Page: "+j);

			for (int i=2;i<210;i++)
			{
				Elements price = doc.select("#srp-river-results > ul > li:nth-child("+i+") > div > div.s-item__info.clearfix > div.s-item__details.clearfix > div:nth-child(1) > span");
				if(!price.isEmpty()&&price!=null)
				{
				accPrice.add(priceCalc(price.text()));
				//System.out.println(priceCalc(price.text()));
				Elements link = doc.select("#srp-river-results > ul > li:nth-child("+i+") > div > div.s-item__info.clearfix > a");
				String url = link.attr("href");
				System.out.println(url);
				}
			}
			
			Collections.sort(accPrice);
        	Collections.reverse(accPrice);
        	
        	for (int i=0;i<accPrice.size();i++)
        	{
        		System.out.println(accPrice.get(i));
        	}
		}
	}
	
	public static Double priceCalc(String price) 
	{
	    Pattern p = Pattern.compile("(\\d+.\\d+)");
	    Matcher m = p.matcher(price);
	    String lmao="";
	    while(m.find()) 
	    {
	    	 lmao=m.group();
	    }
	    
	    return Double.parseDouble( lmao.replace(",","") );
	 }
}