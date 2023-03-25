package com.digdes.school;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {

    private final String pattern = "^\s*(?i)(insert\s*values)|^s*(?i)(updates\s*values)|^s*(?i)delete|^(?i)s*select";

    private final Pattern requestPattern = Pattern.compile(pattern);
    private List<Map<String, Object>> table;

    public JavaSchoolStarter(){

    }

    public List<Map<String, Object>> execute(String request) throws Exception{
        this.table = new ArrayList<Map<String, Object>>();
        String requestType = requestMatcher(request).toLowerCase();


//      Отчистка запроса от всего ненужного
        request = request.replaceAll(pattern, "");
        String[] splitRequset = request.split(",");

        if(requestType.equals(Requests.INSERT.toString())){

            for(int i = 0; i < splitRequset.length; i++) {
                splitRequset[i] = splitRequset[i].replaceAll("\s+", "");
                splitRequset[i] = splitRequset[i].replaceAll("=", " ");
                splitRequset[i] = splitRequset[i].replaceAll("’|‘", "");
            }
            //Словарь <Строка, Объект>
            Map<String, Object> requestMap = new HashMap<>();
            for(int i = 0; i < splitRequset.length; i++){
                List<Object> temp = List.of(splitRequset[i].split(" "));
                requestMap.put(temp.get(0).toString().toLowerCase(), temp.get(1));
            }
            //Вывод словаря
            requestMap.forEach((s, o) -> System.out.println(s + " -> " + o));
            table.add(requestMap);

        }
        else if(requestType.equals(Requests.UPDATE.toString())){

        }
        else if(requestType.equals(Requests.DELETE.toString())){

        }
        else if(requestType.equals(Requests.SELECT.toString())){

        }
        else throw new Exception();

        Map<String, Object> row1 = new HashMap<String, Object>();
        row1.put("id",1);
        row1.put("lastname","Петров");
        row1.put("age",30);
        row1.put("cost",5.4);
        row1.put("active", true);

        this.table.add(row1);

//      Проверка на заполненость ячеек передаваемого словаря и на наличие посторонних названий переменных
        Set<String> expectedKeys = new HashSet<>(Arrays.asList("id", "cost", "lastname", "active", "age"));
        for(Map<String, Object> map:table){
            if(!map.containsKey("id")) map.put("id", null);
            if(!map.containsKey("cost")) map.put("cost", null);
            if(!map.containsKey("lastname")) map.put("lastname", null);
            if(!map.containsKey("active")) map.put("active", null);
            if(!map.containsKey("age")) map.put("age", null);
            if(!map.keySet().equals(expectedKeys)) throw new ArrayStoreException("Введено неверное название переменной");
        }
        return table;
    }

    private String requestMatcher(String request){

        Matcher matcher = requestPattern.matcher(request);
        String rst;

        if(matcher.find()) {
            rst = matcher.group();
            System.out.println(rst);
            return rst;
        }
        return null;
    }
}
