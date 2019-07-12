/*
 *  Terrence Takunda Munyunguma [https://github.com/TerrenceTakunda]
 *  Copyright (C) 2019 ttmunyunguma@gmail.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.cuius.home.dao;

import com.cuius.home.entity.PropertyCategory;
import com.cuius.home.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 *
 * @author terrence
 */

public class ListDao {

    static SessionFactory factory = HibernateUtil.getSessionFactory();

    public ListDao() {
    }

    public List categoryList(){

        List<PropertyCategory> catList;
        try (Session session = factory.openSession()) {
            catList = session.createQuery("SELECT a1.categoryName FROM PropertyCategory a1").setCacheable(true).list();
        }

        return catList;
    }
}
