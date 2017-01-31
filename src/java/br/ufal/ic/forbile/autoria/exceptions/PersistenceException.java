package br.ufal.ic.forbile.autoria.exceptions;

import  java.sql.SQLException;

public class PersistenceException extends Exception {
    
    public PersistenceException(String message) {
        super(message);
    }
    
}
