/*
 * Terrence Takunda Munyunguma [https://github.com/TerrenceTakunda]
 * Copyright (C) 2019 ttmunyunguma@gmail.com
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *  
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.cuius.home.manage;

import com.cuius.home.dao.UserDao;
import com.cuius.home.entity.PropertyManager;
import com.cuius.home.util.PasswordUtils;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.context.spi.AlterableContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.PrimeFaces;

/**
 *
 * @author terrence
 */
@Named(value = "account")
@SessionScoped
public class Account implements Serializable{
    
    // ======================================
    // =          Injection Points          =
    // ======================================
    
    private static final String COOKIE_NAME = "applicationCuiusHomeCookie";
    private static final int COOKIE_AGE = 3600; // Expires after 60 seconds or even 2_592_000 for one month

    @Inject
    private BeanManager beanManager;
    @Inject
    private HttpServletRequest request;
    
    // ======================================
    // =             Constants              =
    // ======================================
    
    private FacesContext facesContext;
    private HttpServletResponse response;
    
    // ======================================
    // =             Attributes             =
    // ======================================
    // Logged user
    private PropertyManager propertyManager = new PropertyManager();
    private boolean loggedIn;
    private boolean admin;
    private String password1;
    private String password2;
    private boolean rememberMe;
    
    /**
     * Creates a new instance of Account
     */
    public Account() {
    }
    
    // ======================================
    // =         Lifecycle methods          =
    // ======================================
    
    @PostConstruct
    private void checkIfUserHasRememberMeCookie() {
        String coockieValue = getCookieValue();
        if (coockieValue == null)
            return;

//        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_UUID, User.class);
//        query.setParameter("uuid", coockieValue);
//        try {
//            user = query.getSingleResult();
//            // If the user is an administrator
//            if (user.getRole().equals(UserRole.ADMIN))
//                admin = true;
//            // The user is now logged in
//            loggedIn = true;
//        } catch (NoResultException e) {
//            // The user maybe has an old coockie, let's get rid of it
//            removeCookie();
//        }
    }
    
    
    // ======================================
    // =          Business methods          =
    // ======================================
    
    public String signIn() throws IOException {
        
        propertyManager = UserDao.signInManager(propertyManager.getEmailAddress(), PasswordUtils.digestPassword(propertyManager.getPassword()));
        boolean status = UserDao.isProceessSuccessful;
        
/*        try {
           //  If the user is an administrator
            if (user.getRoleId() == 1){
                admin = true;
            
              //If the user has clicked on remember me
//            if (rememberMe) {
//                String uuid = UUID.randomUUID().toString();
//                user.setUuid(uuid);
//                addCookie(uuid);

            } else {
                user.setUuid(null);
                removeCookie();
            }*/
            if (status) {

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Welcome" + propertyManager.getFullName(), "Good to have you back"));
            loggedIn = true;
//            PrimeFaces.current().ajax().addCallbackParam("loggedIn", loggedIn);
            return "index.xhtml?faces-redirect=true";
        }
          else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Wrong user/password", "Check your inputs or ask for a new password"));

            return "authenticate.xhtml?faces-redirect=true";
        }
            
    }
    
    public void signOut() throws IOException {
        AlterableContext ctx = (AlterableContext) beanManager.getContext(SessionScoped.class);
        Bean<?> myBean = beanManager.getBeans(Account.class).iterator().next();
        ctx.destroy(myBean);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage("GoodBye", "You are now logged out"));
        
        context.getExternalContext().redirect("../index.html");
    }
    
    // Cookie
    private String getCookieValue() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void addCookie(String value) {
        Cookie cookie = new Cookie(COOKIE_NAME, value);
        cookie.setPath("/cuiusLogin");
        cookie.setMaxAge(COOKIE_AGE);
        response.addCookie(cookie);
    }

    private void removeCookie() {
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    private void resetPasswords() {
        password1 = null;
        password2 = null;
    }
    
    // ======================================
    // =        Getters and Setters         =
    // ======================================

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public BeanManager getBeanManager() {
        return beanManager;
    }

    public void setBeanManager(BeanManager beanManager) {
        this.beanManager = beanManager;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public FacesContext getFacesContext() {
        return facesContext;
    }

    public void setFacesContext(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
    
}
