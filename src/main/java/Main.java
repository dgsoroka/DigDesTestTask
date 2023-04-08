import com.digdes.school.JavaSchoolStarter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {
            List<Map<String, Object>> result2 = starter.execute("INSERT VALUES ‘active’=false, ‘cost’=10.1");
            result2.forEach(System.out::println);
            List<Map<String, Object>> result = starter.execute("UPDATE VALUES ‘active’=true, ‘cost’=10.6");
            result.forEach(System.out::println);
            List<Map<String, Object>> result4 = starter.execute("SELECT");
            result4.forEach(System.out::println);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
