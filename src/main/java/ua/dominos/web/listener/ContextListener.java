package ua.dominos.web.listener;

import ua.dominos.dao.DominoesDao;
import ua.dominos.dao.impl.DominoesDaoImpl;
import ua.dominos.dao.transaction.TransactionManager;
import ua.dominos.service.DominoesService;
import ua.dominos.service.DominoesServiceImpl;
import ua.dominos.util.CombinationsUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Just 'spring' container.
 */
public class ContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        TransactionManager transactionManager = new TransactionManager();
        DominoesDao dominoesDao = new DominoesDaoImpl();
        CombinationsUtil combinationsUtil = new CombinationsUtil();
        DominoesService dominoesService = new DominoesServiceImpl(transactionManager, dominoesDao, combinationsUtil);
        servletContextEvent.getServletContext().setAttribute("dominoesService", dominoesService);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
