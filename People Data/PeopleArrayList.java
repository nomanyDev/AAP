import java.util.ArrayList;

public class PeopleArrayList<ElementType> extends ArrayList<ElementType> {
    // this is a relatively efficient Bubble Sort algorithm (as Bubble Sorts go).
    //It recognises that after each pass of the outer loop an additional right-most element
    //is in the correct position and can be ignored

    public void bubbleSort() {
        int iCount, jCount;
        Comparable elementAtjCount, elementAtjCountPlus;
        for (iCount = 0; iCount < size(); iCount++) {

            for (jCount = 0; jCount < size() - 1 - iCount; jCount++) {
                elementAtjCount = (Comparable) get(jCount);
                elementAtjCountPlus = (Comparable) get(jCount + 1);

                if (elementAtjCount.compareTo(elementAtjCountPlus) > 0) {
                    //swap element on postion jCount with element on position jCount + 1
                    swap(jCount, jCount + 1);
                }
            }
        }
    }

    public void swap(int inPos1, int inPos2) {
        //Create two objects that will store the info from the two positions
        ElementType objPos1 = get(inPos1);
        ElementType objPos2 = get(inPos2);
        //Remove element from position 1
        remove(inPos1);
        //Insert objPos2 into position 1
        add(inPos1, objPos2);
        //Remove element from position 2
        remove(inPos2);
        // Insert objPos1 into position 2
        add(inPos2, objPos1);
    }

    public void simpleBubbleSort() {
        // write the simpleBubbleSort() method
        //it simply compares neighbours repeatedly until there are no more swaps        
        boolean bMoreSwaps = true;

        while (bMoreSwaps == true) {
            int iCount;
            bMoreSwaps = false;
            for (iCount = 0; iCount < size() - 1; iCount++) {
                Comparable elementAtiCount = (Comparable) get(iCount);
                Comparable elementAtiCountPlus = (Comparable) get(iCount + 1);

                if (elementAtiCount.compareTo(elementAtiCountPlus) > 0) {
                    swap(iCount, iCount + 1);
                    bMoreSwaps = true;
                }
            }
        }
    }
}

