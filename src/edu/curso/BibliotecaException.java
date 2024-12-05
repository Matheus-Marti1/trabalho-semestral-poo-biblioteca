package edu.curso;

public class BibliotecaException extends Exception {

    public BibliotecaException(String message) {
        super(message);
    }

    public BibliotecaException() {
        super();
    }

    public BibliotecaException(Throwable t) {
        super(t);
    }

}
