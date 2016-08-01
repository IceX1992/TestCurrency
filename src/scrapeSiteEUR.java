import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Dion on 6/1/2016.
 */

public class scrapeSiteEUR {

    public currencyDAO currencyDAOTest = new currencyDAO();

    public void scrapeSiteEuro() {

        try {
            //haalt CBVS site op
            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.cbvs.sr/nl/").get();

            //Vindt alle elementen met USD
            //Elements test = doc.select("tr:contains(USD)");

            //Vindt: div met rate-info dat Aankoop contained. daarvan de tr die EURO contained
            Elements currencyExchangeEU = doc.select("div.rate-info:contains(Aankoop) tr:contains(EUR)");

            /*
            voorbeeld van output:
            <tr>
            <td>EUR</td>
            <td style="padding-left: 5px;">6,726</td>
            <td style="padding-left: 10px;">6,846</td>
            </tr>
            */

            //Remove all not needed information
            String currencyExchangeValueEUR = String.valueOf(currencyExchangeEU);
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace("<tr>", "");
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace("<td>", "");
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace("<td style=\"padding-left: 5px;\">", "");
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace("<td style=\"padding-left: 10px;\">", "");
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace("</td>", "");
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace("</tr>", "");
            currencyExchangeValueEUR = currencyExchangeValueEUR.replace(",", ".");
            Scanner scEUR = new Scanner(currencyExchangeValueEUR);

            String currencyEUR = scEUR.next(),
                    aankoopEUR = scEUR.next(),
                    verkoopEUR = scEUR.next();
            scEUR.close();

            double aankoopValueEUR = Double.parseDouble(aankoopEUR);
            //afronden naar 2 decimalen
            aankoopValueEUR = Math.round(aankoopValueEUR * 100.0) / 100.0;
            DecimalFormat df = new DecimalFormat("#.00");

            double verkoopValueEUR = Double.parseDouble(verkoopEUR);
            verkoopValueEUR = Math.round(verkoopValueEUR * 100.0) / 100.0;
            DecimalFormat df2 = new DecimalFormat("#.00");

            //get date
            LocalDate test = LocalDate.now();

            System.out.println(test);
            System.out.println(currencyEUR + " Aankoop: " +  df.format(aankoopValueEUR));
            System.out.println(currencyEUR + " Verkoop: " +  df2.format(verkoopValueEUR));

            //write currency exchange rate to db
            currencyDAOTest.saveCurrencyEURO(test, df.format(aankoopValueEUR), df2.format(verkoopValueEUR));

            //write currency exchange rate to files
            FileWriter fwEUR = null;
            try {
                String filename = "Aankoop " + currencyEUR + ".txt";
                fwEUR = new FileWriter(filename, true); //the true will append the new data
                fwEUR.write(test + ", " + df.format(aankoopValueEUR) + "\n");//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwEUR.append(System.getProperty("line.separator"));
                fwEUR.close();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwEUR != null) {
                    fwEUR.close();
                }
            }

            try {
                String filename = "Verkoop " + currencyEUR + ".txt";
                fwEUR = new FileWriter(filename, true); //the true will append the new data
                fwEUR.write(test + ", " + df2.format(verkoopValueEUR) + "\n");//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwEUR.append(System.getProperty("line.separator"));
                fwEUR.close();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwEUR != null) {
                    fwEUR.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
