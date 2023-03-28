package com.digdes.school;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {

    private final String pattern = "^\\s*(?i)(insert\\s*values)|^\\s*(?i)(update\\s*values)|^\\s*(?i)delete|^(?i)s*select";
    private final String whereSyntaxString = "^\\s*(?i)((update\\s+values)|select|delete)\\s+(?:[^w]|w(?!here))*?\\bwhere\\b";
    private final String whereFindString = "(?i)\\bwhere\\b.*";


    private final Pattern wherePattern = Pattern.compile(whereFindString);
    private final Pattern whereSyntaxPatteern = Pattern.compile(whereSyntaxString);
    private final Pattern requestPattern = Pattern.compile(pattern);
    private List<Map<String, Object>> table;

    public JavaSchoolStarter(){

    }

    public List<Map<String, Object>> execute(String request) throws Exception{
        this.table = new ArrayList<Map<String, Object>>();
        String requestType = requestMatcher(request).toLowerCase();

        //      Тестовые данные
        Map<String, Object> row1 = new HashMap<String, Object>();
        row1.put("id",1);
        row1.put("lastname","Петров");
        row1.put("age",30);
        row1.put("cost",5.4);
        row1.put("active", true);

        Map<String,Object> row2 = new HashMap<>();
        row2.put("id",2);
        row2.put("lastname","Иванов");
        row2.put("age",25);
        row2.put("cost",4.3);
        row2.put("active", false);

        Map<String,Object> row3 = new HashMap<>();
        row3.put("id",3);
        row3.put("lastname","Федоров");
        row3.put("age",40);
        row3.put("active", true);

        this.table.add(row1);
        this.table.add(row2);
        this.table.add(row3);



        if(requestType.equals(Requests.INSERT.toString())){

            //Отчистка запроса от всего ненужного
            request = request.replaceAll(pattern, "");
            String[] splitRequset = request.split(",");
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
            List<Map<String, Object>> updateListMap = copy(table);
//          Если where после команды, то продолжаем
            if(whereCorrectSyntax(request)){

                String columnName = "";
//          Отчистка от мусора
                String whereCondition = whereMatcher(request).replaceAll("(?i)where\\s*", "");
    //                        .replaceAll("’|‘", "")
    //                        .replaceAll("and", " ");
//              Удаление лишней информации из request
                request = request.replaceAll(pattern, "");
                String[] splitRequset = request.split(",");
                for(int i = 0; i < splitRequset.length; i++) {
                    splitRequset[i] = splitRequset[i].replaceAll("\s+", "")
                            .replaceAll("=", " ")
                            .replaceAll("’|‘", "")
                            .replaceAll("(?i)where\\s*.*", "");
                }

                List<List<Object>> valuesToReplace = new ArrayList<>();
                for(int i = 0; i < splitRequset.length; i++) {
                    valuesToReplace.add(List.of(splitRequset[i].split(" ")));
                }
                System.out.println(valuesToReplace.get(0).get(0));

                List<Object> conditionList = getListWhereConditions(whereCondition);
//              Получаем названия колонок


                for(int i = 0; i < conditionList.size(); i++){
                    if(conditionList.get(i).toString().charAt(0) == '‘'){
                        String potentialColumnName = conditionList.get(i).toString()
                                .replaceAll("’|‘", "")
                                .toLowerCase();
                        String conditionForSearch = "";
                        Object conditionColValue = "";
                        switch (potentialColumnName) {
                            case ("id"): //Возможно имеет смысл выделить данный отрезок кода в отдельный метод, но я пока не знаю,
//                                         что делать с like, ilike, or и and. Пока все может работать только для простых условий
                                System.out.println("Параметр id");
                                i++;
                                conditionForSearch = conditionList.get(i).toString();
                                i++;
                                conditionColValue = conditionList.get(i);

                                switch (conditionForSearch){
                                    case ("<="):
                                        for(int j = 0; j < updateListMap.size(); j++){
                                            Long colValue = Long.valueOf(updateListMap.get(j).get(potentialColumnName).toString());
                                            if(!(colValue <= Long.valueOf(conditionColValue.toString()))){
                                                updateListMap.remove(j);
                                                j--;
                                            }
                                        }
                                        for(List<Object> value: valuesToReplace){
                                            for(int g = 0; g < updateListMap.size(); g++)
                                                updateListMap.get(g).replace(value.get(0).toString(), value.get(1));
                                        }
                                        this.table = updateListMap;
                                        return updateListMap;

                                    case (">="):
                                        for(int j = 0; j < updateListMap.size(); j++){
                                            Long colValue = Long.valueOf(updateListMap.get(j).get(potentialColumnName).toString());
                                            if(!(colValue >= Long.valueOf(conditionColValue.toString()))){
                                                updateListMap.remove(j);
                                                j--;
                                            }
                                        }
                                        for(List<Object> value: valuesToReplace){
                                            for(int g = 0; g < updateListMap.size(); g++)
                                                updateListMap.get(g).replace(value.get(0).toString(), value.get(1));
                                        }
                                        this.table = updateListMap;
                                        return updateListMap;

                                    case ("="):
                                        for(int j = 0; j < updateListMap.size(); j++){
                                            Long colValue = Long.valueOf(updateListMap.get(j).get(potentialColumnName).toString());
                                            if(!(colValue == Long.valueOf(conditionColValue.toString()))){
                                                updateListMap.remove(j);
                                                j--;
                                            }
                                        }
                                        for(List<Object> value: valuesToReplace){
                                            for(int g = 0; g < updateListMap.size(); g++)
                                                updateListMap.get(g).replace(value.get(0).toString(), value.get(1));
                                        }
                                        this.table = updateListMap;
                                        return updateListMap;
                                }

                                break;
                            case ("lastname"):
                                System.out.println("Параметр lastname");
                                break;
                            case ("age"):
                                System.out.println("Параметр age");
                                break;
                            case ("cost"):
                                System.out.println("Параметр cost");
                                break;
                            case ("active"):
                                System.out.println("Параметр active");

                        }

                            System.out.println(potentialColumnName);


                    }
                }

                conditionList.forEach(System.out::println);

                System.out.println("Есть условие " + whereCondition);

            }
            else {
                System.out.println("Нет условия");
            }

        }
        else if(requestType.equals(Requests.DELETE.toString())){

            if(whereCorrectSyntax(request)){

                System.out.println("Есть условие");

            }
            else {
                System.out.println("Нет условия");
            }
        }
        else if(requestType.equals(Requests.SELECT.toString())){

            if(whereCorrectSyntax(request)){

                System.out.println("Есть условие");

            }
            else {
                System.out.println("Нет условия");
            }
        }
        else throw new Exception();


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

    private boolean whereCorrectSyntax(String request){
        Matcher matcher = whereSyntaxPatteern.matcher(request);
        if(matcher.find()) {
            return true;
        }
        return false;
    }

    private String whereMatcher(String request){
        Matcher matcher = wherePattern.matcher((request));
        String rst;
        matcher.find();
        rst = matcher.group();
        System.out.println(rst);
        return rst;
    }

    private List<Object> getListWhereConditions(String whereCondition){

        char[] whereConditionChars = whereCondition.toCharArray();
        List<Character> conditionChars = new ArrayList<>();
//          Вытаскиваем параметры и операторы сравнения по отдельности
        List<Object> conditionList = new ArrayList<>();
        String str = "";

        for(int i = 0; i <= whereConditionChars.length; i++){
            if(i == whereConditionChars.length){ //Это здесь необходимо, чтобы получать последние символы строки
                for (int j = 0; j < conditionChars.size(); j++) str += conditionChars.get(j);
                conditionList.add(str);
//                        System.out.println(str);
                break;
            }
            str = "";
            if(whereConditionChars[i] == ' '){
                if(conditionChars.isEmpty()) continue;
                for(int j = 0; j < conditionChars.size(); j++) str += conditionChars.get(j);
                conditionList.add(str);
//                        System.out.println(str);
                conditionChars.clear();
                continue;
            }
            if(whereConditionChars[i] == '<' || whereConditionChars[i]  == '>' || whereConditionChars[i] == '='){
                if(whereConditionChars[i-1] != '<'&& whereConditionChars[i-1] != '>' && whereConditionChars[i-1] != '=') {
                    if(conditionChars.isEmpty()){
                        conditionChars.add(whereConditionChars[i]);
                        continue;
                    }
                    for (int j = 0; j < conditionChars.size(); j++) str += conditionChars.get(j);
                    conditionList.add(str);
//                            System.out.println(str);
                    conditionChars.clear();
                    conditionChars.add(whereConditionChars[i]);
                    continue;
                }
            }
            if(i > 0) {
                if (whereConditionChars[i] != '<' && whereConditionChars[i] != '>' && whereConditionChars[i] != '=') {
                    if (whereConditionChars[i - 1] == '<' || whereConditionChars[i - 1] == '>' || whereConditionChars[i - 1] == '=') {
                        for (int j = 0; j < conditionChars.size(); j++) str += conditionChars.get(j);
                        conditionList.add(str);
//                                System.out.println(str);
                        conditionChars.clear();
                        conditionChars.add(whereConditionChars[i]);
                        str = "";
                        continue;
                    }
                }
            }

            conditionChars.add(whereConditionChars[i]);
        }
        return conditionList;
    }

    private List<Map<String, Object>> copy(List<Map<String, Object>> toCopy) {
        List<Map<String, Object>> copy = new ArrayList<>(toCopy.size());
        for(Map<String, Object> inner : toCopy) {
            copy.add(new HashMap<String, Object>(inner));
        }
        return copy;
    }

}