package ru.tiutikova.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.tiutikova.dto.UserDto;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

//@Component
public class AuthFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    private static final int ONE_YEAR = 31536000;
    private final static String USER_SESSION_COOKIE = "sessionId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        ((HttpServletRequest) request).getContextPath();
        String servletPath = ((HttpServletRequest) request).getServletPath();

        Cookie[] cookies = ((HttpServletRequest) request).getCookies();
        String sessionKey = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (AuthFilter.USER_SESSION_COOKIE.equals(cookie.getName())) {
                    sessionKey = cookie.getValue();
                    break;
                }
            }
        }

        /*if (isPublicRes(servletPath)) {
            super.doFilter(request, response, chain);
            return;
        }*/

        if (sessionKey == null || sessionKey.isEmpty()) {
            UUID uuid = UUID.randomUUID();

            Cookie cookie = new Cookie(AuthFilter.USER_SESSION_COOKIE, uuid.toString());
            cookie.setMaxAge(ONE_YEAR);
            cookie.setPath("/");
            ((HttpServletResponse)response).addCookie(cookie);
        }

        UserDto userDto = new UserDto();
        userDto.setSessionId(sessionKey);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(userDto);

        /*try {
            UserDto userDto = authService.getUserAndPermissions(sessionKey);
//            ((HttpServletRequest) request).getUserPrincipal()
        } catch (Exception ex) {
            LOGGER.error("");
//            ((HttpServletResponse)response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            RequestDispatcher rd = ((HttpServletRequest) request).getRequestDispatcher("/login");
            rd.forward(request,response);
            return;
        }*/


        chain.doFilter(request, response);
    }

}
