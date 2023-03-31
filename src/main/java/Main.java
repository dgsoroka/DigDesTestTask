import com.digdes.school.JavaSchoolStarter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {
            List<Map<String, Object>> result = starter.execute("UPDATE VALUES ‘active’=false, ‘cost’=10.1 where ‘id’ <= 2");
            result.forEach(System.out::println);
            List<Map<String, Object>> result1 = starter.execute("SELECT where ‘id’ = 3");
            result1.forEach(System.out::println);
            List<Map<String, Object>> result3 = starter.execute("DELETE where ‘id’ <= 2");
            result3.forEach(System.out::println);
            List<Map<String, Object>> result4 = starter.execute("SELECT");
            result4.forEach(System.out::println);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
