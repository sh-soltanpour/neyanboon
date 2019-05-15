package configuration;

import factory.ObjectFactory;
import security.TokenService;
import services.user.UserService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private final String authHeader = "X-Auth-Token";
    private TokenService tokenService = ObjectFactory.getTokenService();
    private UserService userService = ObjectFactory.getUserService();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        if (true)
            return;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getRequestURI().contains("/login") || httpServletRequest.getRequestURI().contains("/register"))
            chain.doFilter(request, response);
        try {
            String token = httpServletRequest.getHeader(authHeader);
            if (token == null) {
                httpServletResponse.setStatus(403);
                return;
            }
            String userId = tokenService.parseToken(token);
            httpServletRequest.setAttribute("currentUser", userService.getUser(userId));
            chain.doFilter(request, response);
        } catch (Exception e) {
            httpServletResponse.setStatus(403);
        }
    }
}
