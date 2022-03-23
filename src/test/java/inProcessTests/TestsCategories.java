package inProcessTests;

import clients.beans.Category;
import clients.exceptions.CustomExceptions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static clients.beans.CategoryClass.addCategory;

public class TestsCategories {
    private static List<Category> categories;
    @Test
    public void createCategories(){
        categories = Arrays.stream(Category.values()).collect(Collectors.toList());
        for (Category item : categories) {
            try {
                addCategory(item);
            } catch (CustomExceptions customExceptions) {
                System.out.println(customExceptions.getMessage());
            }
        }
    }

    @Test
    public void isCategoryCreated(){
        assert categories != null;
    }

}
