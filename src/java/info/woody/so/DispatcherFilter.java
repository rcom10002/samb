package info.woody.so;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class DispatcherFilter
 */
public class DispatcherFilter implements Filter {

	private Set<String> allModules = null;

    /**
     * Default constructor. 
     */
    public DispatcherFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		String targetURI = ((HttpServletRequest)request).getRequestURI();
		String targetPath = targetURI.replace(request.getServletContext().getContextPath(), "").replaceAll("/", "").replaceAll(".entity$", "");

		if (isNotRestfulRequest(targetURI) && allModules.contains(targetPath)) {
			request.getRequestDispatcher("/WEB-INF/pages/global.jsp" + "?inc=" + targetPath).forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	private boolean isNotRestfulRequest(String targetURI) {
		return targetURI.indexOf("/rest/") < 0;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		allModules = new HashSet<String>();
		Set<String> paths = fConfig.getServletContext().getResourcePaths("/WEB-INF/pages/inc");
		for (String path : paths) {
			if (path.endsWith(".jsp")) {
				allModules.add(path.replaceAll("[.]jsp$", "").replaceAll("^.+/", ""));
			}
		}
		
		// request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE
	}

}
