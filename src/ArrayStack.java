import java.util.Queue;

public class ArrayStack<T> implements Stack<T> {
    private T[] items;
    private int n;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        items = (T[]) new Object[1000];
        n = 0;
    }

    @Override
    public void push(T item) {
        // TODO: If the array is full, double its capacity; then store item at index n and increment n.
        if (n >= 1000) return;
        items[n++] = item;
    }

    @Override
    public T pop() {
        // TODO: Decrement n, save and return that item, and set the popped slot to null to avoid loitering.
        T item = items[--n];
        items[n] = null;
        return item;
    }

    @Override
    public boolean isEmpty() {
        // TODO: Return true when n is zero.
        return n == 0;
    }

    @Override
    public int size() {
        // TODO: Return the number of items currently in the stack.
        return n;
    }

    public static void reverseStack(Stack<Integer> s) {
        Stack<Integer> temp = new ArrayStack<>();
        while (!s.isEmpty()) {
            temp.push(s.pop());
        }
        while (!temp.isEmpty()) {
            s.push(temp.pop());
        }
    }

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

    /**
     * Challenge: Given a string containing '(', ')', '{', '}', '[', and ']'
     * return true if every opening bracket is closed by the same type in the correct order.
     * The empty string is valid.
     */
    public static boolean isValid(String s) {
        // TODO: Use an ArrayStack<Character> to check matching brackets. Push opening brackets;
        // for each closer, check that the most recent opener has the matching type.
        ArrayStack<Character> stack = new ArrayStack<>();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') stack.push(c);
            else {
                char top = stack.pop();
                if (top == '(' && c != ')') return false;
                if (top == '{' && c != '}') return false;
                if (top == '[' && c != ']') return false;
            }
        }
        return stack.isEmpty();
    }

    static void check(String label, Object actual, Object expected) {
        if (actual.equals(expected))
            System.out.println("PASS | " + label);
        else
            System.out.println("FAIL | " + label + " | expected: " + expected + " | got: " + actual);
    }
 
    public static void main(String[] args) {
        ArrayStack<String> stack = new ArrayStack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        check("size after 3 pushes", stack.size(), 3);
        check("isEmpty after pushes", stack.isEmpty(), false);
        check("pop 1 (LIFO)", stack.pop(), "third");
        check("pop 2 (LIFO)", stack.pop(), "second");
        check("pop 3 (LIFO)", stack.pop(), "first");
        check("isEmpty after all pops", stack.isEmpty(), true);

        check("isValid: \"[]\"", isValid("[]"), true);
        check("isValid: \"([{}])\"", isValid("([{}])"), true);
        check("isValid: \"[(])\"", isValid("[(])"), false);
        check("isValid: empty string", isValid(""), true);
        check("isValid: \"({[]})\"", isValid("({[]})"), true);
        check("isValid: \"(((\"", isValid("((("), false);
    }
}
