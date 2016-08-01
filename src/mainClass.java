import java.text.DateFormatSymbols;
import java.util.Calendar;

/**
 * Created by Dion on 5/31/2016.
 */

public class mainClass {
    public static void main(String[] args) {

        // get day of the week
        String dayNames[] = new DateFormatSymbols().getWeekdays();
        Calendar date2 = Calendar.getInstance();

        String dagvdWeek = dayNames[date2.get(Calendar.DAY_OF_WEEK)];
        String sunday = "Sunday";
        String saturday = "Saturday";

        //System.out.println(dagvdWeek);

        scrapeSiteUS currencyUS = new scrapeSiteUS();
        scrapeSiteEUR currencyEUR = new scrapeSiteEUR();
        scrapeSiteGBP currencyGBP = new scrapeSiteGBP();

        //see if it is saturday or sunday, no currency update on those 2 days
        if (dagvdWeek != sunday) {
            if (dagvdWeek != saturday) {
                currencyEUR.scrapeSiteEuro();
                currencyUS.scrapeSiteUS();
                currencyGBP.scrapeSiteGBP();
            } else {
                //System.out.println("Het is zaterdag dus geen koers update");
            }
        } else {
            //System.out.println("Het is zondag dus geen koers update");
        }

    }
}
