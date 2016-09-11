package gov.dhs.kudos.rest.v1.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author bsuneson
 */
public class CorsFilter implements Filter
{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException  {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
    {
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        ((HttpServletResponse) response).setHeader("Access-Control-Expose-Headers", "Authorization");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
        ((HttpServletResponse) response).setHeader("X-Frame-Options", "SAMEORIGIN");
        
        if(!((HttpServletRequest) request).getMethod().equalsIgnoreCase("OPTIONS"))
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
