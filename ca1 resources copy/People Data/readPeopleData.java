import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class readPeopleData {

    public static void main(String[] args) throws Exception {
        //parsing and reading the CSV file data into the object array
        File directory = new File("./");
        String name = directory.getAbsolutePath() + "//people.csv";
        ArrayList<People> people = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(name))) {
            //people = new People[10000]; //had ArrayIndexOutOfBoundsException: Index 10000 out of bounds for length 10000 after adding new record
            // in this reason list was changed to ArrayList, unfortunately in the last stages, haha
            // this will just print the header in CSV file
            scanner.nextLine();
            int i = 0;
            String sGetData;
            while (scanner.hasNextLine()) {
                sGetData = scanner.nextLine();
                String[] data = sGetData.split(",");
                people.add(new People(Integer.parseInt(data[0]), data[1], data[2], data[3], Integer.parseInt(data[4]), Long.parseLong(data[5])));
            }
            //closes the scanner
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: " + e);
        }
        /* 
        sample part:
        // we can print details due to overridden toString method in the class below
        System.out.println(people[0]);
        System.out.println(people[1]);

        // we can compare objects based on their ID due to overridden CompareTo method in the class below
        System.out.println(people[0] == people[0]);
        System.out.println(people[0] == people[1]);
        */
        
        /* this method was changed 
        PeopleArrayList<People> peopleBubbleList = new PeopleArrayList<>();
        for (int i = 0; i < people.length; i++) {
            peopleBubbleList.add(people[i]);
            }
        for peopleBubbleList.addAll(Arrays.asList(people));
        sourse https://stackoverflow.com/questions/157944/create-arraylist-from-array/158269#158269
         */

        //bubblesort compute and test (task1)
        PeopleArrayList<People> peopleBubbleList = new PeopleArrayList<>();
        peopleBubbleList.addAll(people);
        System.out.println(peopleBubbleList.size() + " unsorted records from people.csv was taken");
        System.out.println("BubbleSorting... this is a long algorithm, be patient");

        //Experimentally analyse the time complexity for sorting of all elements
        long lStartTime = System.currentTimeMillis();
        peopleBubbleList.bubbleSort();
        long lEndTime = System.currentTimeMillis();
        long lDuration = lEndTime - lStartTime;
        System.out.println(peopleBubbleList.size() + " records sorted in ascending order by column 'Age'");
        System.out.println("Bubble Sort of " + peopleBubbleList.size() + " elements took " + lDuration + " milliseconds");
        
        //task2 analyse complexity with 10, 100, 1000, 5000, 10000 elems, better not to run, sorry Hamilton's PC
        int[] iComplexityTests = {10, 100, 1000, 5000, 10000};
        for (int i = 0; i < iComplexityTests.length; i++) {
            int test = iComplexityTests[i];
            System.out.println("Checking complexity with " +test+ " elements...");
            PeopleArrayList<People> complexityBubbleList = new PeopleArrayList<>();
            for (int j = 0; j < test && j < people.size(); j++) {
                complexityBubbleList.add(people.get(j)); //take elems for different tests(10,100...)
            }

            lStartTime = System.currentTimeMillis();
            complexityBubbleList.bubbleSort();
            lEndTime = System.currentTimeMillis();
            lDuration = lEndTime - lStartTime;

            System.out.println("Bubble Sort of " + test + " elements took " + lDuration + " milliseconds");

        }
        // print 5 records every 1k to check sorting with age and id
        System.out.println("///Printing 5 records with an interval of 1000 to check the sorting...");
        for (int i = 0; i < 5000; i+=1000) {
            System.out.println(peopleBubbleList.get(i));
        }
        
        //quicksort compute and test (task3)
        PeopleArrayList<People> peopleQuickSortList = new PeopleArrayList<>();
        peopleQuickSortList.addAll(people);
        System.out.println(peopleQuickSortList.size() + " elements QuickSorting...");
        //Experimentally analyse the time complexity
        lStartTime = System.currentTimeMillis();
        peopleQuickSortList.quickSort(0, people.size() - 1);
        lEndTime = System.currentTimeMillis();
        lDuration = lEndTime - lStartTime;
        System.out.println("Quick Sort of " + peopleQuickSortList.size() + " elements took " + lDuration + " milliseconds");

        // print 5 records every 1k to check sorting with age and id
        System.out.println("///Printing 5 records with an interval of 1000 to check the sorting...");
        for (int i = 0; i < 5000; i+=1000) {
            System.out.println(peopleBubbleList.get(i));
        }
        //binary search (task4)
        PeopleArrayList<People> peopleBinaryList = new PeopleArrayList<>();
        peopleBinaryList.addAll(people);
        //sort before binary search
        /*for sort by name was used Comparator
        one of sources:
        https://www.baeldung.com/java-8-comparator-comparing
        */
        Comparator<People> peopleComparator = Comparator.comparing(People::getsName);

        peopleBinaryList.sort(Comparator.comparing(People::getsName));
        System.out.println("Was taken " + peopleBinaryList.size() + " elements. Check if it is sorted before binary search ");
        /* for testing of sort (sorted by name, it works)
        for (int i = 0; i < 5000; i+=1000) {
            System.out.println(peopleBinaryList.get(i));
        } 
        */
        //ask to enter the name for searching
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter the name to search");
        String sNameFinder = keyboard.nextLine();
        People searchByName = new People(0, sNameFinder, "", "", 0, 0);

        //searching with binary search
        System.out.println("Searching for " + sNameFinder);
        int iPosition = peopleBinaryList.binarySearch_recursive(searchByName, 0, peopleBinaryList.size() - 1, peopleComparator);

        if (iPosition == -1) {
            System.out.println("The name " + sNameFinder + " was NOT found in the list!");
        } else {
            People searchPersonResult = peopleBinaryList.get(iPosition); //getting full details if found
            System.out.println("The name " + sNameFinder + " was found! Details here: " +searchPersonResult);
        }
        addNewRecord(name, people);
    }
    //task 5 add new record
    private static void addNewRecord(String fileName, ArrayList<People> people) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a new record in the format: Name,Surname,Job,Age,Credit");
        String input = scanner.nextLine();
        String[] newPeopleRecord = input.split(",");

        if (newPeopleRecord.length != 5) { //5 because id will get automaticaly
            throw new InvalidInputException("Invalid input. Please provide all five fields in the format: Name,Surname,Job,Age,Credit.");
        }
        //declare
        String name = newPeopleRecord[0];
        String surname = newPeopleRecord[1];
        String job = newPeopleRecord[2];
        int age = -1; //cannot write = newPeopleRecord[3]; because its String, should get int first
        long credit = -1;
        //check values for age and credit
        try {
            age = Integer.parseInt(newPeopleRecord[3]);
            credit = Long.parseLong(newPeopleRecord[4]);
            // Validate age
            if (age < 0 || age > 125) {
                throw new NumberFormatException("Age must be between 0 and 125.");
        }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format for Age(integer) or Credit(long). Please enter valid numbers.");
            return;
        }
        try {
            isNameValid(name);
        } catch (InvalidInputException e) {
            System.out.println(e.getMessage());
            return;
        }
        int newId = getNextId(people);
        People newPerson = new People(newId, name, surname, job, age, credit);

        // write new record
        try (
            FileWriter fw = new FileWriter(fileName, true); 
            BufferedWriter bw = new BufferedWriter(fw)) {
            bw.newLine();
            bw.write(newPerson.toStringCSV());
            System.out.println("Data was added to " + fileName);
            System.out.println("New record - " + newPerson.toStringCSV());
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("New record added successfully!");
    }
    private static void isNameValid(String name) throws InvalidInputException {
        if (name.isEmpty() || name.matches("[a-zA-Z]")) {
            throw new InvalidInputException("Person's name cannot be empty. It cannot have only digits! Please correct this!");
        }
    }
    //get last id from file for new record
    private static int getNextId(ArrayList<People> people) {
        int maxId = 0;
        for (People person : people) {
            if (person != null && person.getiId() > maxId) {
                maxId = person.getiId();
            }
        }
        return maxId + 1;
    }

}
//Comparable<Object> was changed to Comparable<People> 
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
        int iSortByAge = Integer.compare(iAge, myPeople.iAge);

        if (iSortByAge == 0) {
            return Integer.compare(iId, myPeople.iId);
        }

        return iSortByAge;
    }
    
    public int compareByName(People obj) {
        return this.sName.compareTo(obj.sName);
    }

    @Override
    public String toString() {
        return "Person [ID= " + iId + ", Name= " + sName + ", Surname= "
                + sSurname + ", Job= " + sJob + ", Age= "
                + iAge + ", Credit= " + lCredit + "]";
    }
    //toString for csv file
    public String toStringCSV() {
        return String.format("%d,%s,%s,%s,%d,%d", iId, sName, sSurname, sJob, iAge, lCredit);
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
