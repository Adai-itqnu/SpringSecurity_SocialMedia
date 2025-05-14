
package com.hungdev.configs;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.hungdev.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserDetailsService userDetailsService;

	 @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	            throws ServletException, IOException {

	        String token = null;

	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if ("jwt_token".equals(cookie.getName())) {
	                    token = cookie.getValue();
	                    break;
	                }
	            }
	        }

	        if (token != null) {
	            String username = jwtService.extractUsernameFromToken(token);

	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

	            if (jwtService.validateToken(token)) {
	                List<String> roles = jwtService.extractAuthoritiesFromToken(token);
	                List<GrantedAuthority> authorities = roles.stream()
	                        .map(SimpleGrantedAuthority::new)
	                        .collect(Collectors.toList());

	                UsernamePasswordAuthenticationToken authToken =
	                        new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
	                
	                authToken.setDetails(userDetails);
					/* authToken.setAuthenticated(true); */
	                SecurityContextHolder.getContext().setAuthentication(authToken);

	                System.out.println("Auth set: " + username + " | Authorities: " + roles);
	            } else {
	                SecurityContextHolder.clearContext();
	            }
	        }

	        chain.doFilter(request, response);
	    }

}
