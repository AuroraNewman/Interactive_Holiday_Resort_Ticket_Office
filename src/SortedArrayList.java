/*
Task 1 of the specification says that you need to develop a SortedArrayList<E> class as a subclass of the
standard library class ArrayList<E> from the java.util package.
Observe that your SortedArrayList<E> class should be implemented as a generic class that uses a type variable E.
This class should contain only one method for inserting a new element into a sorted array list.
This insertion method must not use the Collections.sort() method or any other built-in Java sorting method.
Furthermore, this method should be a non-static method and therefore work as an operation on sorted array lists.
We know that a “future” sorted array list, on which this method will be invoked, is represented inside the method by the "this" reference variable.
Into this sorted array list, the method should to insert a new element of type E in the right place, so the ascending order of the elements in the list is preserved.
You need to think carefully about the heading of your SortedArrayList class. Will it work with any type substituted for E, or should its type variable be
constrained in any way?
You should be able to find some clues to help you answer this question by looking at slide 41 of the second set of lecture notes.
 */

import java.util.ArrayList;

public class SortedArrayList <E extends Comparable<? super E>> extends ArrayList<E>{
       public static <E extends Comparable<? super E>> void sortArrayList(ArrayList<E> sortableList) {
        for (int i = 1; i < sortableList.size(); i++) {
            E value = sortableList.get(i);
            int j;
            for (j = i; j > 0; j--) {
                if (sortableList.get(j - 1).compareTo(value) < 0) {
                    break;
                }
                else {
                    sortableList.set(j, sortableList.get(j - 1));
                }
            }
            sortableList.set(j, value);
            }
        }
}
/*
@Override
    public boolean add(E e) {
        return super.add(e);
    }
//TODO: make an implementation of sorted array list that takes in an add and puts it in the correct order instead of taking a list and sorting it.
 */
