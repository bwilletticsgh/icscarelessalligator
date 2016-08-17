package gov.dhs.kudos.rest.v1.filter;

import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.service.StatsService;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
public class JwtAuthenticationFilter implements Filter
{
    private static final Logger LOG = Logger.getLogger(JwtAuthenticationFilter.class);
    
    private final StatsService statsService;

    public JwtAuthenticationFilter(StatsService statsService) 
    {
        this.statsService = statsService;
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException 
    {
        User user = null;
        String path = ((HttpServletRequest) request).getPathInfo();
        
        if(!path.startsWith("/v1/user/login") && !path.startsWith("/v1/user/register"))
        {
            try
            {
                user = JwtTokenUtil.validateToken((HttpServletRequest)request, (HttpServletResponse) response);
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
        
        statsService.record(path, user);
        
        filterChain.doFilter(request, response);
    }    

    @Override
    public void init(FilterConfig filterConfig) throws ServletException 
    {
        
    }

    @Override
    public void destroy() 
    {
        
    }
}
