package cf.baradist;

import cf.baradist.controller.AccountController;

public class AbstractTest {
    private static final String PROPERTIES_FILE = "src/integrationTest/resources/application.properties";

    public void setUp() throws Exception {
        Configurer.readProperties(PROPERTIES_FILE);
        Configurer.configureDb();
    }
}
