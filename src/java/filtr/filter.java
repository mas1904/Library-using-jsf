/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filtr;

import java.io.IOException;
import java.util.Enumeration;
import javax.faces.application.ResourceHandler;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import wyp.Czytelnicy;

/**
 *
 * @author Łukasz
 */
public class filter implements Filter  {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {    
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String loginURI = request.getRequestURI().substring(12);

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
        Czytelnicy a = (Czytelnicy)request.getSession().getAttribute("user"); 
            if (loginURI.equals("/category")) {
                loginURI = "view/page/category";
            } else if (loginURI.equals("/index")) {
                loginURI = "index";
            } else if (loginURI.equals("/search")) {
                loginURI = "view/page/search";
            } else if (loginURI.equals("/book")) {
                loginURI = "view/page/bookinfo";
            } else if (loginURI.equals("/register")) {
                loginURI = "view/page/register";
            } else if (loginURI.equals("/bin")) {
                loginURI = "view/page/bin";
            } else if (loginURI.equals("/library")) {
                loginURI = "view/page/library";
            } else if(a.getStopien()>1){ 
            if (loginURI.equals("/user")) {
                loginURI = "view/adm/user";
            } else if (loginURI.equals("/adminusers")) {
                loginURI = "view/adm/adminusers";
            } else if (loginURI.equals("/adminaw")) {
                loginURI = "view/adm/adminaw";
            } else if (loginURI.equals("/adminbooks")) {
                loginURI = "view/adm/adminbooks";
            } else if (loginURI.equals("/adminm") && a.getStopien()>2) {
                loginURI = "view/adm/adminm";
            } }
            else {
                loginURI = "../";
                response.sendRedirect(loginURI);
                return;
            }

            String url = "/faces/" + loginURI + ".xhtml";

            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}
