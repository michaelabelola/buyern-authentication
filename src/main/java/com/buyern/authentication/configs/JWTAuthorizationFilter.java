package com.buyern.authentication.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final String HEADER_STRING = "Authorization";
    @Value("${jwt.secret}")
    private String secret;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        return new UsernamePasswordAuthenticationToken("userId", null, null);
//        String token = request.getHeader(HEADER_STRING);
//        if (token != null) {
//            String userId = null;
//            try {
//                userId = JWT.require(Algorithm.HMAC512(secret.getBytes()))
//                        .build()
//                        .verify(token.replace(TOKEN_PREFIX, ""))
//                        .getSubject();
////                UserSession userSession = userSessionService.getSession(userId);
////                return new UsernamePasswordAuthenticationToken(userId, null, userSession.getAuthorities());
//                return new UsernamePasswordAuthenticationToken(userId, null, null);
//            } catch (Exception ex) {
//                return null;
//            }
//        } else
//            try {
//                response.sendError(HttpServletResponse.SC_PROXY_AUTHENTICATION_REQUIRED, "Invalid Token");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        setResponse(response, "Error 2");
//        return null;
    }

    public void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(message);
        oos.flush();
        response.getOutputStream().write(bos.toByteArray());
    }

    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "e no do");
        response.sendRedirect("/user/auth/signIn/fail");
        super.onUnsuccessfulAuthentication(request, response, failed);
    }
}
