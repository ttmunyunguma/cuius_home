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
package com.cuius.home.dao;

import com.cuius.home.entity.PropertyManager;
import com.cuius.home.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author terrence
 */
public class UserDao {
    
    // ======================================
    // =             Attributes             =
    // ======================================
    
    static SessionFactory factory = HibernateUtil.getSessionFactory();
    public static boolean isProceessSuccessful;

    /**
     * Creates a new instance of UserDao
     */
    public UserDao() {
    }
    
    // ======================================
    // =          Business methods          =
    // ======================================
    
    public static PropertyManager signInManager(String email, String password){
        
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            Query query = session.createQuery("FROM PropertyManager WHERE emailAddress=:email AND password=:password");
            query.setParameter("email", email);
            query.setParameter("password", password);
            
            PropertyManager result = (PropertyManager) query.getSingleResult();
            if(result!= null)
                isProceessSuccessful = true;
            
            return result;
        } catch (HibernateException e) {
        }
        return null;
    }

    
}
