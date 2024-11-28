package com.uber.uber.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.uber.uber.services.JwtService;
import com.uber.uber.services.UserDetailsServiceImpl;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter{

    private JwtService jwtService;
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    public JwtFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        
        // verify header
        String header = request.getHeader("Authorization");
        
        if(header == null || !header.startsWith("Bearer ")){
            SecurityContextHolder.clearContext();
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(
                "{\"error\": \"Invalid or expired token\"}"
            );
            return;
        }
        final String jwt;
        String username = null;

        jwt = header.substring(7);

        try{
            username = jwtService.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = null;
                try{
                    userDetails = userDetailsService.loadUserByUsername(username);
                }catch(UsernameNotFoundException ex){
                    response.setStatus(401);
                    response.setContentType("application/json");
                    response.getWriter().write(
                        "{\"error\": \"Invalid User\"}"
                    );
                    return;
                }
                if(jwtService.isTokenValid(jwt, userDetails.getUsername())){
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }catch(JwtException ex){
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write(
                "{\"error\": \"Authentication Failed\"}"
            );
            return;
        }
        filterChain.doFilter(request, response);
    }
}
