package cf.baradist.dao;

public abstract class DaoFactory {
    private volatile static DaoFactory daoFactory;

    public abstract UserDao getUserDao();

    public abstract void fillTestData();

    public static synchronized DaoFactory getDaoFactory() {
        if (daoFactory == null) {
            synchronized (DaoFactory.class) {
                daoFactory = new H2DaoFactory();
            }
        }
        return daoFactory;
    }
}
