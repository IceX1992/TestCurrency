import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

/**
 * Created by Dion on 6/3/2016.
 */
public class currencyDAO {

    Connection connectie = null;

    public void saveCurrencyEURO(LocalDate datum, String aankoop, String verkoop) {

        Date datumValue = Date.valueOf(datum);

        //STAP 2: Registreer JDBC driver
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STAP 3: Open een jdbc connectie
            System.out.println("Bezig met connectie naar de database...");
            connectie = DriverManager.getConnection("jdbc:mysql://localhost/currency_exchange",
                    "test",
                    "test");

            //STAP 4: Executeer een database sql query
            System.out.println("Creeer sql statement...");
            PreparedStatement ps = connectie.prepareStatement("INSERT INTO currency_eu (datum, aankoop, verkoop) VALUES (?, ?, ?)");
            ps.setDate(1, datumValue);
            ps.setString(2, aankoop);
            ps.setString(3, verkoop);
            int row = ps.executeUpdate();
            System.out.println("Aantal rows gecreeerd bij insert statement: " + row);
            System.out.println("\n");
            ps.close();
            connectie.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Fout bij het zoeken van mysql jdbc driver");
        } catch (SQLException e) {
            System.out.println("Fout bij het creeeren van jdbc database connectie");
            e.printStackTrace();

            //write error to file
            FileWriter fwError = null;
            LocalDate test = LocalDate.now();
            try {
                String filename = "errors " + ".txt";
                fwError = new FileWriter(filename, true); //the true will append the new data
                fwError.write(test + " euro error : " + String.valueOf(e));//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwError.append(System.getProperty("line.separator"));
                fwError.close();
            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwError != null) {
                    try {
                        fwError.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

        }

    }

    public void saveCurrencyUS(LocalDate datum, String aankoop, String verkoop) {

        Date datumValue = Date.valueOf(datum);
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STAP 3: Open een jdbc connectie
            System.out.println("Bezig met connectie naar de database...");
            connectie = DriverManager.getConnection("jdbc:mysql://localhost/currency_exchange",
                    "test",
                    "test");

            //STAP 4: Executeer een database sql query
            System.out.println("Creeer sql statement...");
            PreparedStatement ps = connectie.prepareStatement("INSERT INTO currency_usd (datum, aankoop, verkoop) VALUES (?, ?, ?)");
            ps.setDate(1, datumValue);
            ps.setString(2, aankoop);
            ps.setString(3, verkoop);
            int row = ps.executeUpdate();
            System.out.println("Aantal rows gecreeerd bij insert statement: " + row);
            System.out.println("\n");
            ps.close();
            connectie.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Fout bij het zoeken van mysql jdbc driver");
        } catch (SQLException e) {
            System.out.println("Fout bij het creeeren van jdbc database connectie");
            e.printStackTrace();

            //write error to file
            FileWriter fwError = null;
            LocalDate test = LocalDate.now();
            try {
                String filename = "errors " + ".txt";
                fwError = new FileWriter(filename, true); //the true will append the new data
                fwError.write(test + " usd error : " + String.valueOf(e));//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwError.append(System.getProperty("line.separator"));
                fwError.close();
            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwError != null) {
                    try {
                        fwError.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    public void saveCurrencyGBP(LocalDate datum, String aankoop, String verkoop) {

        Date datumValue = Date.valueOf(datum);
        try {
            Class.forName("com.mysql.jdbc.Driver");

            //STAP 3: Open een jdbc connectie
            System.out.println("Bezig met connectie naar de database...");
            connectie = DriverManager.getConnection("jdbc:mysql://localhost/currency_exchange",
                    "test",
                    "test");

            //STAP 4: Executeer een database sql query
            System.out.println("Creeer sql statement...");
            PreparedStatement ps = connectie.prepareStatement("INSERT INTO currency_gbp (datum, aankoop, verkoop) VALUES (?, ?, ?)");
            ps.setDate(1, datumValue);
            ps.setString(2, aankoop);
            ps.setString(3, verkoop);
            int row = ps.executeUpdate();
            System.out.println("Aantal rows gecreeerd bij insert statement: " + row);
            System.out.println("\n");
            ps.close();
            connectie.close();

        } catch (ClassNotFoundException e) {
            System.out.println("Fout bij het zoeken van mysql jdbc driver");
        } catch (SQLException e) {
            System.out.println("Fout bij het creeeren van jdbc database connectie");
            e.printStackTrace();

            //write error to file
            FileWriter fwError = null;
            LocalDate test = LocalDate.now();
            try {
                String filename = "errors " + ".txt";
                fwError = new FileWriter(filename, true); //the true will append the new data
                fwError.write(test + " gbp error : " + String.valueOf(e));//appends the string to the file
                //Onderste creert een line seperator zodat bij de volgende input het op een nieuwe regel komt
                fwError.append(System.getProperty("line.separator"));
                fwError.close();
            } catch (IOException ioe) {
                System.err.println("IOException: " + ioe.getMessage());

            } finally {
                if (fwError != null) {
                    try {
                        fwError.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}



