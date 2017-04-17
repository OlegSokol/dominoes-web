package ua.dominos.web.controller;

import org.apache.log4j.Logger;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.service.DominoesService;
import ua.dominos.service.exception.DominoesServiceException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    private static final long serialVersionUID = -2223036533524684616L;
    private static final Logger LOG = Logger.getLogger(HttpServlet.class);

    private DominoesService dominoesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dominoesService = (DominoesService) getServletContext().getAttribute("dominoesService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<DominoTileChain> chainsHistory = dominoesService.getChainsHistory();
            request.setAttribute("history", chainsHistory);
            request.getRequestDispatcher("/WEB-INF/jsp/combinations-history.jsp").forward(request, response);
        } catch (DominoesServiceException e) {
            LOG.warn("Service is not available", e);
            response.sendError(500, "Service is not available: " + e.getMessage());
        }
    }
}
