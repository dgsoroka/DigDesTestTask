import com.digdes.school.JavaSchoolStarter;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {
            List<Map<String, Object>> result = starter.execute("INSERT VALUES  ‘lastName’ = ‘Федоров’ , ‘id’=3, ‘age’=40, ‘active’=true");
            result.forEach(System.out::println);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
