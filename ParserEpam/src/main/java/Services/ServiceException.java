package Services;

public class ServiceException extends Exception {

    public ServiceException(Exception e){
        super(e);
    }

    public ServiceException(String cause , Exception e ){
        super(cause,e);
    }

    public ServiceException(String cause){
        super(cause);
    }
}
