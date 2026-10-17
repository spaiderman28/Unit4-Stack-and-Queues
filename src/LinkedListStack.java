public class LinkedListStack<T> implements Stack<T> {
    private class Node {
        T item;
        Node next;
    }
    private Node first;
    private int n;

    public LinkedListStack() {
        first = null;
        n = 0;
    }

    @Override
    public void push(T item) {
        // TODO: Create a node, link it to the old first node, and make it the new first node.
        Node temp = first; 
        first = new Node(); 
        first.item = item; 
        first.next = temp;
    }

    @Override
    public T pop() {
        T item = first.item;
        first = first.next;
        n--;
        return item;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public int size() {
        return n;
    }

    /**
     * Challenge: Evaluate a valid arithmetic expression in Reverse Polish Notation.
     * The operators are '+', '-', '*', and '/'; division truncates toward zero.
     */
    public static int evalRPN(String[] tokens) {
        LinkedListStack<Integer> stack = new LinkedListStack<>();
        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int right = stack.pop();
                int left = stack.pop();
                int result;
                switch (token) {
                    case "+": result = left + right; break;
                    case "-": result = left - right; break;
                    case "*": result = left * right; break;
                    default:  result = left / right; break;
                }
                stack.push(result);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    static void check(String label, Object actual, Object expected) {
        if (actual.equals(expected))
            System.out.println("PASS | " + label);
        else
            System.out.println("FAIL | " + label + " | expected: " + expected + " | got: " + actual);
    }

    public static void main(String[] args) {
        LinkedListStack<String> stack = new LinkedListStack<>();
        stack.push("first");
        stack.push("second");
        stack.push("third");

        check("size after 3 pushes", stack.size(), 3);
        check("isEmpty after pushes", stack.isEmpty(), false);
        check("pop 1 (LIFO)", stack.pop(), "third");
        check("pop 2 (LIFO)", stack.pop(), "second");
        check("pop 3 (LIFO)", stack.pop(), "first");
        check("isEmpty after all pops", stack.isEmpty(), true);

        check("evalRPN: ((1+2)*3)-4", evalRPN(new String[]{"1", "2", "+", "3", "*", "4", "-"}), 5);
        check("evalRPN: (2+1)*3", evalRPN(new String[]{"2", "1", "+", "3", "*"}), 9);
        check("evalRPN: 4+(13/5)", evalRPN(new String[]{"4", "13", "5", "/", "+"}), 6);
        check("evalRPN: complex", evalRPN(new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}), 22);
    }
}
