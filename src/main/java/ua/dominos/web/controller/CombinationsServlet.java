package ua.dominos.web.controller;

import org.apache.log4j.Logger;
import ua.dominos.entity.DominoBox;
import ua.dominos.entity.DominoTile;
import ua.dominos.entity.DominoTileChain;
import ua.dominos.service.DominoesService;
import ua.dominos.service.exception.DominoesServiceException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/combinations")
public class CombinationsServlet extends HttpServlet {
    private static final long serialVersionUID = -1813036533124184676L;
    private static final Logger LOG = Logger.getLogger(CombinationsServlet.class);

    private DominoesService dominoesService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dominoesService = (DominoesService) getServletContext().getAttribute("dominoesService");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/combinations.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int numberOfDominoes = Integer.parseInt(request.getParameter("numberOfDominoes"));
            boolean isValid = isValidNumberOfDominoes(numberOfDominoes);
            if (!isValid) {
                LOG.warn("Number: " + numberOfDominoes + ", is not valid");
                response.sendError(500, "Wrong number!");
            }
            DominoBox dominoBox = DominoBox.getInstance();
            List<DominoTile> randomDominoTiles = dominoBox.getRandomTiles(numberOfDominoes);
            List<DominoTileChain> allChainCombinations = dominoesService.getAllChainCombinations(randomDominoTiles);
            DominoTileChain longestCombination = dominoesService.getLongestCombination(randomDominoTiles);
            if (allChainCombinations != null) {
                dominoesService.saveChains(allChainCombinations);
            }
            HttpSession session = request.getSession(true);
            session.setAttribute("combinations", allChainCombinations);
            session.setAttribute("dominoes", randomDominoTiles);
            session.setAttribute("longestChain", longestCombination);
            response.sendRedirect("/combinations");
        } catch (DominoesServiceException | NumberFormatException e) {
            LOG.warn("Service is not available", e);
            response.sendError(500, "Service is not available: " + e.getMessage());
        }
    }

    private boolean isValidNumberOfDominoes(int number) {
        return number <= 28 && number > 0;
    }
}
