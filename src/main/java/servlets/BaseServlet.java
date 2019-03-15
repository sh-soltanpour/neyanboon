package servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

abstract class BaseServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    enum HttpStatus {
        OK(200), NOTFOUND(404), ACCESSDENIED(403), CONFLICT(409), INTERNAL_SERVER(500);

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

    void returnJson(Object obj, HttpServletResponse resp) {
        returnJson(obj, resp, HttpStatus.OK);
    }

    void returnJson(Object obj, HttpServletResponse resp, HttpStatus status) {
        try {
            resp.setContentType("application/json; charset=UTF-8");
            resp.setStatus(status.getCode());
            String json = objectMapper.writeValueAsString(obj);
            PrintWriter out = resp.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpStatus.INTERNAL_SERVER.getCode());
        }
    }

    void returnError(String message, HttpStatus status, HttpServletResponse resp) {
        returnJson(new ErrorMessage(message), resp, status);
    }

    private class ErrorMessage {
        public String getMessage() {
            return message;
        }

        String message;

        private ErrorMessage(String message) {
            this.message = message;
        }
    }

}
