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

    @Inject
    private BeanManager beanManager;
    private PropertyManager propertyManager = new PropertyManager();
    private boolean loggedIn;
    private boolean admin;
    
    /**
     * Creates a new instance of Account
     */
    public Account() {
    }

    public void signIn() throws IOException {
        
        propertyManager = UserDao.signInManager(propertyManager.getEmailAddress(), PasswordUtils.digestPassword(propertyManager.getPassword()));
        boolean status = UserDao.isProceessSuccessful;
        FacesContext context = FacesContext.getCurrentInstance();
        if (status) {
            context.addMessage(null, new FacesMessage("Welcome" + propertyManager.getFullName(), "Good to have you back"));
            loggedIn = true;
            context.getExternalContext().redirect("portal/");
        }
          else {
            context.addMessage(null, new FacesMessage("Wrong user/password", "Check your inputs or ask for a new password"));

            context.getExternalContext().redirect("authenticate/");
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


    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
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
    
}
