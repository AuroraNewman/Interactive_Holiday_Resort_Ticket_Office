/**
 * this class hosts a single method used to insert an element into an arraylist at the appropriate place.
 */

import java.util.ArrayList;

public class SortedArrayList <E extends Comparable<? super E>> extends ArrayList<E> {
    /**
     * this method takes in a sortable list and an element to be added to that list.
     * if the size is zero (i.e., this is the first item to be added), the element is added.
     * for every other case, the element is compared to each Object in the arraylist starting at index zero.
     * if the Object in the arraylist is greater than the element to be added, the element is added into that Object's place.
     * if the comparing integer i reaches the end of the list, the element is added to the end of the list.
     * @param element element whose presence in this collection is to be ensured. It is added to the arraylist in the proper place to maintain its sorted status.
     * @return true if the element was added and false if it wasn't.
     */
    @Override
    public boolean add(E element) {
        if (this.size() == 0) {
            super.add(element);
            return true;
        } else {
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i).compareTo(element) > 0) {
                    this.add(i, element);
                    return true;
                } else if (i == (this.size() - 1)) {
                    super.add(element);
                    return true;
                }
            }
        }
        return false;
    }
}
