import java.io.File;
import java.util.*;

public class readCompaniesData {

    public static void main(String[] args) throws Exception {
        //parsing and reading the CSV file data into the object array
        File directory = new File("./");
        String name = directory.getAbsolutePath() + "//companies.csv";
        Company[] company;
        try (Scanner scanner = new Scanner(new File(name))) {
            company = new Company[10000];
            // this will just print the header in CSV file
            scanner.nextLine();
            int i = 0;
            String sGetData;
            while (scanner.hasNextLine()) {
                sGetData = scanner.nextLine();
                String[] data = sGetData.split(";");
                company[i++] = new Company(Integer.parseInt(data[0]), data[1], data[2], data[3], Integer.parseInt(data[4]), Long.parseLong(data[5]));
            }
            //closes the scanner
        }

        // we can print details due to overridden toString method in the class below
        System.out.println(company[0]);
        System.out.println(company[1]);

        // we can compare objects based on their ID due to overridden CompareTo method in the class below
        System.out.println(company[0] == company[0]);
        System.out.println(company[0] == company[1]);
    }

}

class Company implements Comparable<Object> {
    private int iId;
    private String sName;
    private String sCountry;
    private String sCurrency;
    private int iAccount;
    private long lCvv;

    // constructor
    public Company(int iInId, String sInName, String sInCountry, String sInCurrency, int iInAccount, long lInCvv) {
        this.iId = iInId;
        this.sName = sInName;
        this.sCountry = sInCountry;
        this.sCurrency = sInCurrency;
        this.iAccount = iInAccount;
        this.lCvv = lInCvv;
    }

    // the objects can be compared when sorting/searching
    @Override
    public int compareTo(Object obj) {

        /*
		Edit this section so it compares the appropriate
		column you wish to sort by
         */
        Company mycompany = (Company) obj;
        return iId - (mycompany.getiId());
    }

    @Override
    public String toString() {
        return "Company [ID= " + iId + ", Name= " + sName + ", Country= "
                + sCountry + ", Currency= " + sCurrency + ", Account= "
                + iAccount + ", CVV= " + lCvv + "]";
    }

    // getters and setters

    public int getiId() {
        return iId;
    }

    public void setiId(int iId) {
        this.iId = iId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsCountry() {
        return sCountry;
    }

    public void setsCountry(String sCountry) {
        this.sCountry = sCountry;
    }

    public String getsCurrency() {
        return sCurrency;
    }

    public void setsCurrency(String sCurrency) {
        this.sCurrency = sCurrency;
    }

    public int getiAccount() {
        return iAccount;
    }

    public void setiAccount(int iAccount) {
        this.iAccount = iAccount;
    }

    public long getlCvv() {
        return lCvv;
    }

    public void setlCvv(long lCvv) {
        this.lCvv = lCvv;
    }

}
