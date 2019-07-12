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

import com.cuius.home.dao.ListDao;
import com.cuius.home.entity.PropertyCategory;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.util.List;

/**
 *
 * @author terrence
 */
@Named(value = "categoryMB")
@RequestScoped
public class CategoryMB {

    private PropertyCategory propertyCategory = new PropertyCategory();
    private String categoryName;

    public CategoryMB(){
    }

    public List<PropertyCategory> getAllCategories(){
        return new ListDao().categoryList();
    }

    public List<SelectItem> getSelectCategories(){

        List<SelectItem> catName = new ListDao().categoryList();
        return catName;
    }

    public PropertyCategory getPropertyCategory() {
        return propertyCategory;
    }

    public void setPropertyCategory(PropertyCategory propertyCategory) {
        this.propertyCategory = propertyCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
