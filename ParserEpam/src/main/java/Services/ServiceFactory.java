package Services;

import Services.Impl.MenuServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final MenuService service = new MenuServiceImpl();

    private ServiceFactory() {}

    public MenuService getMenuService() {

        return service;
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

}
