package com.shabbir.Jwt_Impl.Filter;

import com.shabbir.Jwt_Impl.Service.UserDetailsServiceImpl;
import com.shabbir.Jwt_Impl.Utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String getAuthHeader = request.getHeader("Authorization");
        String username=null;
        String jwtToken = null;
        if(getAuthHeader != null && getAuthHeader.startsWith("Bearer ")){
            jwtToken = getAuthHeader.substring(7);
            username = jwtUtils.extractUsername(jwtToken);
        }
        if(username != null ){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtUtils.isValidToken(jwtToken)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
