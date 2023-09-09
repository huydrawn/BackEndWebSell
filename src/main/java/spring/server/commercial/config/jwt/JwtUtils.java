package spring.server.commercial.config.jwt;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import spring.server.commercial.exception.jwt.JwtTokenException;
import spring.server.commercial.exception.jwt.JwtTokenMaformedException;
import spring.server.commercial.exception.jwt.JwtTokenMissingException;
import spring.server.commercial.model.user.User;

@Service
public class JwtUtils {
	private final String secret = "123456";
	private final int jwtExpirationMs = 36000000;

	public String gennerateJwtToken(User user) {
		return Jwts.builder().setSubject((user.getEmail())).setIssuedAt(new Date())
				.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	public Date getExpriedTime(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
	}

	public boolean valiadJwtToken(String token) throws JwtTokenException {
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (SignatureException e) {
			throw new JwtTokenMaformedException("Invaliad Jwt Signature");
		} catch (MalformedJwtException e) {
			throw new JwtTokenMaformedException("Invaliad Jwt Token");
		} catch (ExpiredJwtException e) {
			throw new JwtTokenMaformedException("Jwt Token has expried");
		} catch (UnsupportedJwtException e) {
			throw new JwtTokenMaformedException("Unsupported Jwt Token");
		} catch (IllegalArgumentException e) {
			throw new JwtTokenMissingException("Jwt token is empty");
		}
	}

	public String getSubjectFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}
}
