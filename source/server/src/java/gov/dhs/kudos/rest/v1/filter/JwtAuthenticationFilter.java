package gov.dhs.kudos.rest.v1.filter;

import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.exception.KudosException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author bsuneson
 */
public class JwtAuthenticationFilter extends org.springframework.web.filter.DelegatingFilterProxy
{
    private static final Logger LOG = Logger.getLogger(JwtAuthenticationFilter.class);
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException 
    {
        String path = ((HttpServletRequest) request).getPathInfo();
        
        if(!path.startsWith("/v1/user/login") && !path.startsWith("/v1/user/register"))
        {
            try
            {
                JwtTokenUtil.validateToken((HttpServletRequest)request, (HttpServletResponse) response);
            }
            catch(KudosException e)
            {
                ((HttpServletResponse) response).setStatus(e.getHttpStatus().value());
                ((HttpServletResponse) response).setContentType("application/json");
                ((HttpServletResponse) response).setCharacterEncoding("UTF-8");
                ((HttpServletResponse) response).getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
                return;
            }
        }
        else if(LOG.isDebugEnabled())
            LOG.debug("[Filter] User login attempt from: " + request.getRemoteAddr());
        
        filterChain.doFilter(request, response);        
    }    
}
