import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Dion on 7/4/2016.
 */

public class scrapeSiteGBP {

    public currencyDAO currencyDAOTest = new currencyDAO();

    public void scrapeSiteGBP() {

        try {
            //haalt CBVS site op
            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.cbvs.sr/nl/").get();

            //Vindt alle elementen met USD
            //Elements test = doc.select("tr:contains(USD)");

            //Vindt: div met rate-info dat Aankoop contained. daarvan de tr die GBP contained
            Elements currencyExchangeGBP = doc.select("div.rate-info:contains(Aankoop) tr:contains(GBP)");

            /*
            voorbeeld van output:
            <tr>
            <td>GBP</td>
            <td style="padding-left: 5px;">6,726</td>
            <td style="padding-left: 10px;">6,846</td>
            </tr>
            */

            //Remove all not needed information
            String currencyExchangeValueGBP= String.valueOf(currencyExchangeGBP);
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace("<tr>", "");
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace("<td>", "");
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace("<td style=\"padding-left: 5px;\">", "");
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace("<td style=\"padding-left: 10px;\">", "");
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace("</td>", "");
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace("</tr>", "");
            currencyExchangeValueGBP = currencyExchangeValueGBP.replace(",", ".");
            Scanner scGBP = new Scanner(currencyExchangeValueGBP);

            String currencyGBP = scGBP.next(),
                    aankoopGBP = scGBP.next(),
                    verkoopGBP = scGBP.next();
            scGBP.close();

            double aankoopValueGBP = Double.parseDouble(aankoopGBP);
            //afronden naar 2 decimalen
            aankoopValueGBP = Math.round(aankoopValueGBP * 100.0) / 100.0;
            DecimalFormat df = new DecimalFormat("#.00");

            double verkoopValueGBP = Double.parseDouble(verkoopGBP);
            verkoopValueGBP = Math.round(verkoopValueGBP * 100.0) / 100.0;
            DecimalFormat df2 = new DecimalFormat("#.00");

            //get date
            LocalDate test = LocalDate.now();

            System.out.println(test);
            System.out.println(currencyGBP + " Aankoop: " +  df.format(aankoopValueGBP));
            System.out.println(currencyGBP + " Verkoop: " +  df2.format(verkoopValueGBP));

            //write currency exchange rate to db
            currencyDAOTest.saveCurrencyGBP(test, df.format(aankoopValueGBP), df2.format(verkoopValueGBP));

            //write currency exchange rate to files
            FileWriter fwGBP = null;
            try {
                String filename = "Aankoop " + currencyGBP + ".txt";
                fwGBP = new FileWriter(filename, true); //the true will append the new data
                fwGBP.write(test + ", " + df.format(aankoopValueGBP) + "\n");//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwGBP.append(System.getProperty("line.separator"));
                fwGBP.close();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwGBP != null) {
                    fwGBP.close();
                }
            }

            try {
                String filename = "Verkoop " + currencyGBP + ".txt";
                fwGBP = new FileWriter(filename, true); //the true will append the new data
                fwGBP.write(test + ", " + df2.format(verkoopValueGBP) + "\n");//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwGBP.append(System.getProperty("line.separator"));
                fwGBP.close();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwGBP != null) {
                    fwGBP.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
