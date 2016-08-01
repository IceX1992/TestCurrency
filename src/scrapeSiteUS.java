import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Created by Dion on 6/3/2016.
 */

public class scrapeSiteUS {

    public currencyDAO currencyDAOTest = new currencyDAO();

    public void scrapeSiteUS() {

        try {
            //haalt CBVS site op
            org.jsoup.nodes.Document doc = Jsoup.connect("https://www.cbvs.sr/nl/").get();

            //Vindt alle elementen met USD
            //Elements test = doc.select("tr:contains(USD)");

            //Vindt: div met rate-info dat Aankoop contained. daarvan de tr die USD contained
            Elements currencyExchange = doc.select("div.rate-info:contains(Aankoop) tr:contains(USD)");

            /*
            voorbeeld van output:
            <tr>
            <td>USD</td>
            <td style="padding-left: 5px;">6,726</td>
            <td style="padding-left: 10px;">6,846</td>
            </tr>
            */

            //Remove all not needed information
            String currencyExchangeValueUS = String.valueOf(currencyExchange);
            currencyExchangeValueUS = currencyExchangeValueUS.replace("<tr>", "");
            currencyExchangeValueUS = currencyExchangeValueUS.replace("<td>", "");
            currencyExchangeValueUS = currencyExchangeValueUS.replace("<td style=\"padding-left: 5px;\">", "");
            currencyExchangeValueUS = currencyExchangeValueUS.replace("<td style=\"padding-left: 10px;\">", "");
            currencyExchangeValueUS = currencyExchangeValueUS.replace("</td>", "");
            currencyExchangeValueUS = currencyExchangeValueUS.replace("</tr>", "");
            currencyExchangeValueUS = currencyExchangeValueUS.replace(",", ".");
            Scanner sc = new Scanner(currencyExchangeValueUS);

            String currencyUS = sc.next(),
                    aankoopUS = sc.next(),
                    verkoopUS = sc.next();
            sc.close();

            double aankoopValueUS = Double.parseDouble(aankoopUS);
            //afronden naar 2 decimalen
            aankoopValueUS = Math.round(aankoopValueUS * 100.0) / 100.0;
            DecimalFormat df = new DecimalFormat("#.00");

            double verkoopValueUS = Double.parseDouble(verkoopUS);
            verkoopValueUS = Math.round(verkoopValueUS * 100.0) / 100.0;
            DecimalFormat df2 = new DecimalFormat("#.00");

            //get date
            LocalDate test = LocalDate.now();

            System.out.println(test);
            System.out.println(currencyUS + " Aankoop: " + df.format(aankoopValueUS));
            System.out.println(currencyUS + " Verkoop: " + df2.format(verkoopValueUS));

            //write currencyUS exchange rate to db
            currencyDAOTest.saveCurrencyUS(test, df.format(aankoopValueUS), df2.format(verkoopValueUS));

            //write currencyUS exchange rate to files
            FileWriter fw = null;
            try {
                String filename = "Aankoop " + currencyUS + ".txt";
                fw = new FileWriter(filename, true); //the true will append the new data
                fw.write(test + ", " + df.format(aankoopValueUS) + "\n");//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fw.append(System.getProperty("line.separator"));
                fw.close();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fw != null) {
                    fw.close();
                }
            }

            try {
                String filename = "Verkoop " + currencyUS + ".txt";
                fw = new FileWriter(filename, true); //the true will append the new data
                fw.write(test + ", " + df2.format(verkoopValueUS) + "\n");//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fw.append(System.getProperty("line.separator"));
                fw.close();

            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fw != null) {
                    fw.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
