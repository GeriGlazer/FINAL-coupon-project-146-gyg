package clients.beans;

import clients.db.DBManager;
import clients.db.DBTools;
import clients.exceptions.CustomExceptions;
import clients.exceptions.EnumExceptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Yoav Hachmon, Guy Endvelt and Gery Glazer
 * 03.2022
 */

/**
 * Group of methods concerning Category interface
 */
public class CategoryClass {

    /**
     * insert new category type to database
     *
     * @param category input from Category interface
     */
    public static void addCategory(Category category) throws CustomExceptions {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, Category.valueOf(category.name()));
        if(getCategoryId(category)>0){
            throw new CustomExceptions(EnumExceptions.CATEGORY_EXIST);
        }
        DBTools.runQuery(DBManager.ADD_CATEGORY, values);
    }

    /**
     * deletes category type from database
     *
     * @param categoryId input category ID
     */
    public void deleteCategory(int categoryId) throws CustomExceptions{
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, categoryId);
        if(getCategoryName(categoryId).isEmpty()){
            throw new CustomExceptions(EnumExceptions.CATEGORY_NOT_EXIST) ;
        }
        DBTools.runQuery(DBManager.DELETE_CATEGORY, values);
    }

    /**
     * retrieve category id by name
     *
     * @param category input category from interface
     * @return category id integer
     */
    public static int getCategoryId(Category category) throws CustomExceptions{
        int categoryId = 0;
        Map<Integer, Object> value = new HashMap<>();
        try {
            value.put(1, Category.valueOf(category.name()));
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.GET_ONE_CATEGORY, value);
            assert resultSet != null;
            if (resultSet.next()) {
                categoryId = resultSet.getInt("id");
            }else {
                throw new CustomExceptions(EnumExceptions.CATEGORY_NOT_EXIST);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categoryId;
    }

    public String getCategoryName(int categoryId) throws CustomExceptions {
        Map<Integer, Object> values = new HashMap<>();
        values.put(1, categoryId);
        try {
            ResultSet resultSet = DBTools.runQueryForResult(DBManager.GET_CATEGORY_NAME, values);
            assert resultSet != null;
            if (resultSet.next()) {
                return resultSet.getString("name");
            }else {
                throw new CustomExceptions(EnumExceptions.CATEGORY_NOT_EXIST);
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
        return null;
    }
}
