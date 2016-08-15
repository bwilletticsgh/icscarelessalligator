package gov.dhs.kudos.rest.v1.util;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;

/**
 *
 * @author bsuneson
 */
public class JwtTokenUtil 
{
    private static KeyPair keys;
    
    public static String generateToken(User user, HttpServletResponse response) throws KudosException
    {
        try
        {
            String token = Jwts.builder().setSubject(user.getEmail())
                .setIssuer("KudosREST")
                .setExpiration(new Date(System.currentTimeMillis() + 900000))
                .claim("kudosUser", user)
                .signWith(SignatureAlgorithm.RS512, keys.getPrivate()).compact();
        
            response.setHeader("Authorization", "Bearer " + token);

            return token;
        }
        catch(Exception e)
        {
            throw new KudosException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    public static User validateToken(HttpServletRequest httpRequest, HttpServletResponse response) throws KudosException
    {
        try
        {
            String header = httpRequest.getHeader("Authorization");
        
            if(header == null || !header.startsWith("Bearer "))
                throw new KudosException("No Kudos Token found in request headers", HttpStatus.BAD_REQUEST);

            Claims claims = Jwts.parser().setSigningKey(keys.getPrivate()).parseClaimsJws(header.substring(7)).getBody();
            claims.setExpiration(new Date(System.currentTimeMillis() + 900000));

            String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.RS512, keys.getPrivate()).compact();
            response.setHeader("Authorization", "Bearer " + token);

            return claims.get("kudosUser", User.class);
        }
        catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e)
        {
            if((e instanceof SignatureException) || (e instanceof MalformedJwtException) || (e instanceof IllegalArgumentException))
                throw new KudosException(e.getMessage(), HttpStatus.UNAUTHORIZED);
            else if(e instanceof ExpiredJwtException)
                throw new KudosException("EXPIRED TOKEN", HttpStatus.UNAUTHORIZED);
            else
                throw new KudosException(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }        
    }
    
    static
    {
        try
        {            
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            random.setSeed(md.digest(new String(Base64.decodeBase64("Y2wwdWQ5QGNlJEQxJHBAdA==")).getBytes()));
            keys = RsaProvider.generateKeyPair(2048, random);            
        }
        catch(NoSuchAlgorithmException | NoSuchProviderException e)
        {
            // TODO handle
            e.printStackTrace();
        }
    }
}
