package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

abstract class BaseServlet extends HttpServlet {
    enum HttpStatus {
        OK(200), NOTFOUND(404), ACCESSDENIED(403), CONFLICT(409);

        private final int code;      // Private variable

        HttpStatus(int code) {     // Constructor
            this.code = code;
        }

        int getCode() {              // Getter
            return code;
        }
    }
    void showError(HttpServletRequest req, HttpServletResponse resp, String message, HttpStatus status)
            throws ServletException, IOException {
        req.setAttribute("message", message);
        resp.setStatus(status.getCode());
        req.getRequestDispatcher("/error.jsp").forward(req, resp);
    }
}
