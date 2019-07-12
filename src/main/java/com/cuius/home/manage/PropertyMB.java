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
package com.cuius.home.manage;

import com.cuius.home.entity.Property;
import com.cuius.home.entity.PropertyHistory;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

/**
 *
 * @author terrence
 */
@Named(value = "propertyMB")
@SessionScoped
public class PropertyMB implements Serializable {

    private Property property = new Property();
    private PropertyHistory propertyHistory = new PropertyHistory();

    public PropertyMB() {
    }



    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public PropertyHistory getPropertyHistory() {
        return propertyHistory;
    }

    public void setPropertyHistory(PropertyHistory propertyHistory) {
        this.propertyHistory = propertyHistory;
    }
}
