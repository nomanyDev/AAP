import java.io.File;
import java.util.*;

public class readPeopleData {

    public static void main(String[] args) throws Exception {
        //parsing and reading the CSV file data into the object array
        File directory = new File("./");
        String name = directory.getAbsolutePath() + "//people.csv";
        People[] people;
        try (Scanner scanner = new Scanner(new File(name))) {
            people = new People[10000];
            // this will just print the header in CSV file
            scanner.nextLine();
            int i = 0;
            String sGetData;
            while (scanner.hasNextLine()) {
                sGetData = scanner.nextLine();
                String[] data = sGetData.split(",");
                people[i++] = new People(Integer.parseInt(data[0]), data[1], data[2], data[3], Integer.parseInt(data[4]), Long.parseLong(data[5]));
            }
            //closes the scanner
        }

        // we can print details due to overridden toString method in the class below
        System.out.println(people[0]);
        System.out.println(people[1]);

        // we can compare objects based on their ID due to overridden CompareTo method in the class below
        System.out.println(people[0] == people[0]);
        System.out.println(people[0] == people[1]);

        PeopleArrayList<People> peopleList = new PeopleArrayList<>();
        peopleList.addAll(Arrays.asList(people));

        peopleList.bubbleSort();

        // print first 100 for testing
        for (int i = 0; i < 100; i++) {
            System.out.println(peopleList.get(i));
        }
    }

}

class People implements Comparable<People> {
    private int iId;
    private String sName;
    private String sSurname;
    private String sJob;
    private int iAge;
    private long lCredit;

    // constructor
    public People(int iInId, String sInName, String sInSurname, String sInJob, int iInAge, long lInCredit) {
        this.iId = iInId;
        this.sName = sInName;
        this.sSurname = sInSurname;
        this.sJob = sInJob;
        this.iAge = iInAge;
        this.lCredit = lInCredit;
    }

    // the objects can be compared when sorting/searching
    @Override
    public int compareTo(People obj) {

        /*
		Edit this section so it compares the appropriate
		column you wish to sort by
         */
        People myPeople = (People) obj;
        //return iId - (myPeople.getiId());
        int iSortByAge = Integer.compare(this.iAge, myPeople.iAge);

        if (iSortByAge == 0) {
            return Integer.compare(this.iId, myPeople.iId);
        }

        return iSortByAge;
    }

    @Override
    public String toString() {
        return "Person [ID= " + iId + ", Name= " + sName + ", Surname= "
                + sSurname + ", Job= " + sJob + ", Age= "
                + iAge + ", Credit= " + lCredit + "]";
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

    public String getsSurname() {
        return sSurname;
    }

    public void setsSurname(String sSurname) {
        this.sSurname = sSurname;
    }

    public String getsJob() {
        return sJob;
    }

    public void setsJob(String sJob) {
        this.sJob = sJob;
    }

    public int getiAge() {
        return iAge;
    }

    public void setiAge(int iAge) {
        this.iAge = iAge;
    }

    public long getlCredit() {
        return lCredit;
    }

    public void setlCredit(long lCredit) {
        this.lCredit = lCredit;
    }

}
