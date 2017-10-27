package Services;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final Service service = new ServiceImpl();

    private ServiceFactory() {}

    public Service getDocumentService() {

        return service;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

}
