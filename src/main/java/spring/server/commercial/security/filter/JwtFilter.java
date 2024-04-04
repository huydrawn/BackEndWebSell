package spring.server.commercial.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import spring.server.commercial.config.jwt.JwtUtils;
import spring.server.commercial.config.path.PathConfig;
import spring.server.commercial.config.websocket.WebSocketConfig;
import spring.server.commercial.exception.bearertoken.BearerTokenException;
import spring.server.commercial.exception.bearertoken.BearerTokenMissingException;
import spring.server.commercial.exception.jwt.JwtTokenException;
import spring.server.commercial.security.JwtTokenAuthentication;
import spring.server.commercial.security.provider_auth.JwtAuthenticationProvider;

@Service
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final JwtUtils jwtUtils;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (PathConfig.checkPathForAuthentication(request.getServletPath(), request.getMethod())) {

			try {
				
				String token = getTokenFroRequest(request);

				if (StringUtils.hasText(token) && jwtUtils.valiadJwtToken(token)) {

					String email = jwtUtils.getSubjectFromJwtToken(token);

					Authentication authentication = jwtAuthenticationProvider
							.authenticate(new JwtTokenAuthentication(email, null));
					authentication.setAuthenticated(true);
					SecurityContextHolder.getContext().setAuthentication(authentication);

				}
			} catch (JwtTokenException | BearerTokenException e) {
				System.out.println(e.getMessage());
			}
		}
		filterChain.doFilter(request, response);
	}

	private String getTokenFroRequest(HttpServletRequest request) throws BearerTokenException {

		if (PathConfig.matcherConnectWebSocketPath(request.getServletPath()) && request.getHeader("Authorization") == null ) {
			return request.getParameter("token").toString(); 
		}
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		} else {
			throw new BearerTokenMissingException("Not Found Bearer Token in headers");
		}
	}

}
