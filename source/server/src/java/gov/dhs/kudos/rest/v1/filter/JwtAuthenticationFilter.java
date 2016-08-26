package gov.dhs.kudos.rest.v1.filter;

import gov.dhs.kudos.rest.v1.exception.KudosException;
import gov.dhs.kudos.rest.v1.model.UsageStatistic;
import gov.dhs.kudos.rest.v1.model.User;
import gov.dhs.kudos.rest.v1.repo.UsageStatisticRepo;
import gov.dhs.kudos.rest.v1.util.JwtTokenUtil;
import gov.dhs.kudos.rest.v1.util.LogUtils;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A custom servlet filter implementation for authenticating all request to the REST API
 * @author bsuneson
 */
@Component("jwtAuthenticationFilter")
public class JwtAuthenticationFilter implements Filter
{
    /** The logger for this class **/
    private static final Logger LOG = Logger.getLogger(JwtAuthenticationFilter.class);
    
    /** The usage statistics mono repository for storing usage **/
    @Autowired
    protected UsageStatisticRepo usageStatisticRepo;

    public JwtAuthenticationFilter() 
    {
        
    }
    
    /**
     * Parses the request for a valid JSON Web Token if the incoming request is
     * not for login or registration.  Logs all usage.
     * @param request The incoming request
     * @param response The response to update a JWT's expiry time - if valid
     * @param filterChain Spring's remaining default filters
     * @throws ServletException
     * @throws IOException 
     */
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
                request.setAttribute("kudosUser", user);
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
        
        record(path, (user == null ? request.getRemoteAddr() : user.getEmail()));
        
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
    
    /**
     * Logs and stores the endpoint usage
     * @param uri
     * @param user 
     */
    private void record(String uri, String user)
    {        
        LOG.info("[Usage] " + user + " accessing " + uri);
        
        try
        {            
            if(uri != null && uri.length() > 1)
            {
                uri = LogUtils.filterUsageURI(uri);
                usageStatisticRepo.save(new UsageStatistic(uri, user));
            }                
        }
        catch(Exception e)
        {
            LOG.error(e);
        }
    }
}
