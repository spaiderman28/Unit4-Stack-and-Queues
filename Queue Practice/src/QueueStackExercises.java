import java.util.*;
import java.util.Queue;

/**
 * Starter file for Queue & Stack Exercises 1–4.
 * Exercises: reverseStack, reorder, isPalindrome, removeMin.
 *
 * Students: implement each method where marked TODO.
 * The test harness at the bottom is complete and passes once methods are correct.
 * LinkedList is used as the Queue implementation; swap in QueueArray if required.
 */
public class QueueStackExercises {

    // Exercise 1: Reverse a stack using one queue.
    public static void reverseStack(Stack<Integer> s) {
        Queue<Integer> temp = new LinkedList<>();
        while (!s.isEmpty()) {
            temp.add(s.pop());
        }
        while (!temp.isEmpty()) {
            s.push(temp.remove());
        }
    }

    // Exercise 2: Reorder a queue sorted by absolute value into numeric order.
    // Must use one stack as auxiliary storage.
    public static void reorder(Queue<Integer> q) {
        Stack<Integer> stack = new ArrayStack<>();
        int size = q.size();
        for (int i = 0; i < size; i++) {
            stack.push(q.remove());
        }
        while (!stack.isEmpty()) {
            q.add(stack.pop());
        }
        for (int i = 0; i < size; i++) {
            int num = q.remove();

            if (num < 0) q.add(num);
            else stack.push(num);
        }
        while (!stack.isEmpty()) {
            q.add(stack.pop());
        }
    }

    // Exercise 3: Return true if the queue is a palindrome.
    // Must restore the queue to its original state before returning.
    // Must use one Stack as auxiliary storage.
    public static boolean isPalindrome(Queue<Integer> q) {
        Stack<Integer> stack = new ArrayStack<>();
        int size = q.size();
        for (int i = 0; i < size; i++) {
            int num = q.remove();
            stack.push(num);
            q.add(num);
        }
        for (int i = 0; i < size; i++) {
            int num = q.remove();
            if (num != stack.pop()) return false;
            q.add(num);
        }
        return true;
    }

    // Exercise 4: Remove and return the minimum value from the stack.
    // Remove ALL occurrences of the minimum.
    // Must use one queue as auxiliary storage.
    public static int removeMin(Stack<Integer> stack) {
        Stack<Integer> temp = new ArrayStack<>();
        int min = stack.pop(); temp.push(min);
        while (!stack.isEmpty()) {
            int num = stack.pop();
            if (num < min) min = num;
            temp.push(num);
        }
        while (!temp.isEmpty()) {
            int num = temp.pop();
            if (num != min) stack.push(num);
        }
        return min;
    }

    // --- Test helpers ---

    /** Compares and consumes the queue's contents against expected values. */
    private static boolean queueEquals(Queue<Integer> actual, Integer... expected) {
        if (actual.size() != expected.length) return false;
        for (Integer val : expected) {
            if (!Objects.equals(actual.remove(), val)) return false;
        }
        return actual.isEmpty();
    }

    /**
     * Compares stack contents bottom-to-top against expected values
     * without destroying the stack. Stack.toArray() returns bottom-to-top order.
     */
    private static boolean stackEquals(Stack<Integer> actual, Integer... expectedBottomToTop) {
        Object[] arr = actual.toArray(); // bottom-to-top order
        if (arr.length != expectedBottomToTop.length) return false;
        for (int i = 0; i < arr.length; i++) {
            if (!Objects.equals(arr[i], expectedBottomToTop[i])) return false;
        }
        return true;
    }

    private static void report(String description, boolean passed) {
        System.out.println((passed ? "PASS: " : "FAIL: ") + description);
    }

    public static void main(String[] args) {
        // --- Exercise 1: reverseStack ---
        Stack<Integer> rev1 = new Stack<>();
        for (int v : new int[]{1, 2, 3, 4, 5}) rev1.push(v);
        reverseStack(rev1);
        report("reverseStack test 1: [1,2,3,4,5] → bottom-to-top [5,4,3,2,1]",
                stackEquals(rev1, 5, 4, 3, 2, 1));

        Stack<Integer> rev2 = new Stack<>();
        rev2.push(1);
        reverseStack(rev2);
        report("reverseStack test 2: single element [1] stays [1]",
                stackEquals(rev2, 1));

        Stack<Integer> rev3 = new Stack<>();
        reverseStack(rev3);
        report("reverseStack test 3: empty stack stays empty",
                stackEquals(rev3));

        // --- Exercise 2: reorder ---
        Queue<Integer> r1 = new LinkedList<>(Arrays.asList(1, 2, -2, 4, -5, 8, -8, 12, -15));
        reorder(r1);
        report("reorder test 1", queueEquals(r1, -15, -8, -5, -2, 1, 2, 4, 8, 12));

        Queue<Integer> r2 = new LinkedList<>(Arrays.asList(3, -3, 7));
        reorder(r2);
        report("reorder test 2", queueEquals(r2, -3, 3, 7));

        Queue<Integer> r3 = new LinkedList<>();
        reorder(r3);
        report("reorder test 3 (empty queue)", queueEquals(r3));

        // --- Exercise 3: isPalindrome ---
        Queue<Integer> p1 = new LinkedList<>(Arrays.asList(3, 8, 17, 9, 17, 8, 3));
        report("isPalindrome test 1: [3,8,17,9,17,8,3] → true", isPalindrome(p1));

        Queue<Integer> p2 = new LinkedList<>(Arrays.asList(3, 17, 9, 4, 17, 3));
        report("isPalindrome test 2: [3,17,9,4,17,3] → false", !isPalindrome(p2));

        Queue<Integer> p3 = new LinkedList<>();
        report("isPalindrome test 3: empty → true", isPalindrome(p3));

        Queue<Integer> p4 = new LinkedList<>(Arrays.asList(5));
        report("isPalindrome test 4: [5] → true", isPalindrome(p4));

        Queue<Integer> p5 = new LinkedList<>(Arrays.asList(3, 8, 17, 9, 17, 8, 3));
        isPalindrome(p5);
        report("isPalindrome test 5: queue restored after call",
                queueEquals(p5, 3, 8, 17, 9, 17, 8, 3));

        // --- Exercise 4: removeMin ---
        Stack<Integer> m1 = new Stack<>();
        for (int v : new int[]{2, 8, 3, 19, 2, 3, 2, 7, 12, -8, 4}) m1.push(v);
        int min1 = removeMin(m1);
        report("removeMin test 1: returns -8", min1 == -8);
        report("removeMin test 1: stack is [2,8,3,19,2,3,2,7,12,4]",
                stackEquals(m1, 2, 8, 3, 19, 2, 3, 2, 7, 12, 4));

        int min2 = removeMin(m1);
        report("removeMin test 2: returns 2", min2 == 2);
        report("removeMin test 2: stack is [8,3,19,3,7,12,4]",
                stackEquals(m1, 8, 3, 19, 3, 7, 12, 4));

        Stack<Integer> m3 = new Stack<>();
        m3.push(5);
        int min3 = removeMin(m3);
        report("removeMin test 3: single element returns 5", min3 == 5);
        report("removeMin test 3: stack is empty", m3.isEmpty());
    }
}
