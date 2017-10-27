package DAO;

public class DAOException extends Exception {

    public DAOException(Exception e){
        super(e);
    }

    public DAOException(String cause , Exception e ){
        super(cause,e);
    }

    public DAOException(String cause){
        super(cause);
    }

}
