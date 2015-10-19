package by.auto.domain.testmothers;

import by.auto.domain.common.category.Category;
import org.springframework.test.util.ReflectionTestUtils;

public class CategoryMother {
    public static Category createCategory() {
        final Category category = new Category("Авто", null, null);
        ReflectionTestUtils.setField(category, "id", "auto");
        return category;
    }
}
