package graph.implementation;

public class Utils {

    /**
     * Checks if an array is empty or not.
     * @param array the array we want to verify
     * @return true if the array is empty; false otherwise
     */
    public static boolean isEmpty(Object[] array) {
        for (Object obj : array) {
            if (obj != null) {
                // There is one object in the array, it is not empty
                return false;
            }
        }
        // All the slots were null so the array is empty
        return true;
    }

    /**
     * Returns the real size of an array (not its length). Its real size is the number of elements that are actually
     * int this given array.
     * @param array the array we want the size of
     * @return the size of the array
     */
    public static int getArraySize(Object[] array) {
        int size = 0;
        for (Object obj : array) {
            if (obj != null) size +=1;
        }
        return size;
    }

    /**
     * Returns the first empty slot's index in a given array or -1 if there are no empty slot.
     * An empty slot is a slot for which the value is null.
     * @param array the array
     * @return the first empty slot's index or -1 if none was found
     */
    public static int getFirstEmptyIndex(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
