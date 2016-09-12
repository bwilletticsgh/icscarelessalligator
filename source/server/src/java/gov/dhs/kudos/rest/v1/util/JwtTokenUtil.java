package gov.dhs.kudos.rest.v1.util;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.IOException;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpStatus;

/**
 * Utility for JSON Web Tokens
 * @author bsuneson
 */
public class JwtTokenUtil 
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(JwtTokenUtil.class);
    /** The strong crypto keys **/
    private static KeyPair keys;
    /** JSON to POJO to JSON MAPPER **/
    private static final ObjectMapper MAPPER = new ObjectMapper();
    
    /**
     * Generates a JWT for the given user and places the token within the Authorization header
     * @param user The user object to generate a token for
     * @param response The HttpServletResponse to place the token into
     * @return The JSON Web Token
     * @throws KudosException 
     */
    public static synchronized String generateToken(User user, HttpServletResponse response) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Generating token for User: " + user.getEmail());
        
        try
        {            
            String token = Jwts.builder().setSubject(user.getEmail())
                .setIssuer("KudosREST")
                .setExpiration(new Date(System.currentTimeMillis() + 28800000))
                .claim("kudosUser", MAPPER.writeValueAsString(user))
                .signWith(SignatureAlgorithm.RS512, keys.getPrivate()).compact();
        
            response.setHeader("Authorization", "Bearer " + token);

            return token;
        }
        catch(Exception e)
        {
            LOG.error(e);
            throw new KudosException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Validates the JWT from the incoming request
     * @param httpRequest The HttpServletRequest that should contain the JWT
     * @param response The response that will obtain an updated JWT with an extended 15min expiry time
     * @return The User object associated with the request and within the JWT
     * @throws KudosException 
     */
    public static synchronized User validateToken(HttpServletRequest httpRequest, HttpServletResponse response) throws KudosException
    {
        if(LOG.isDebugEnabled())
            LOG.debug("Validating token...");
        
        try
        {
            String header = httpRequest.getHeader("Authorization");
            if(header == null || header.length() == 0)
                header = "Bearer " + httpRequest.getHeader("api_key");
        
            if(header == null || !header.startsWith("Bearer ") || header.equals("Bearer null"))
                throw new KudosException("No Kudos Token found in request headers", HttpStatus.UNAUTHORIZED);

            Claims claims = Jwts.parser().setSigningKey(keys.getPrivate()).parseClaimsJws(header.substring(7)).getBody();            
            claims.setExpiration(new Date(System.currentTimeMillis() + 28800000));

            User user = MAPPER.readValue((String)claims.get("kudosUser"), User.class);
            
            String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.RS512, keys.getPrivate()).compact();
            response.setHeader("Authorization", "Bearer " + token);
            
            if(LOG.isDebugEnabled())
                LOG.debug("Token validated for User: " + user.getEmail());

            return user;
        }
        catch(ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | IOException e)
        {
            LOG.error(e);
            
            if((e instanceof SignatureException) || (e instanceof MalformedJwtException) || (e instanceof IllegalArgumentException))
                throw new KudosException(e.getMessage(), HttpStatus.UNAUTHORIZED);
            else if(e instanceof ExpiredJwtException)
                throw new KudosException("EXPIRED TOKEN", HttpStatus.UNAUTHORIZED);
            else
                throw new KudosException(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }        
    }
    
    /**
     * Initialize the strong KeyPair
     */
    static
    {
        try
        {  
            if(LOG.isDebugEnabled())
                LOG.debug("Generating strong KeyPair...");
            
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            random.setSeed(md.digest(new String(Base64.decodeBase64("Y2wwdWQ5QGNlJEQxJHBAdA==")).getBytes()));
            keys = RsaProvider.generateKeyPair(2048, random);            
        }
        catch(NoSuchAlgorithmException | NoSuchProviderException e)
        {
            LOG.error(e);
        }
    }
}
