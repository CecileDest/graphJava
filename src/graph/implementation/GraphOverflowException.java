package graph.implementation;

/*
Not necessary to catch them because this exception shouldn't
happen if the code is well made
 */
public class GraphOverflowException extends RuntimeException {

    public GraphOverflowException(String message) {
        super(message);
    }
    public GraphOverflowException() {
        super();
    }
}
