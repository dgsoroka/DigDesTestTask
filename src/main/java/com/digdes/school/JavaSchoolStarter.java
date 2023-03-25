package com.digdes.school;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaSchoolStarter {

    private List<Map<String, Object>> table;

    public JavaSchoolStarter(){

    }

    public List<Map<String, Object>> execute(String request) throws Exception{
        this.table = new ArrayList<Map<String, Object>>();

        if(requestMatcher(request).equals(Requests.INSERT.toString())){

        }
        else if(requestMatcher(request).equals(Requests.UPDATE.toString())){

        }
        else if(requestMatcher(request).equals(Requests.DELETE)){

        }
        else if(requestMatcher(request).equals(Requests.SELECT)){

        }
        else throw new Exception();

        Map<String, Object> row1 = new HashMap<String, Object>();
        row1.put("id",1);
        row1.put("lastName","Петров");
        row1.put("age",30);
        row1.put("cost",5.4);
        row1.put("active", true);

        this.table.add(row1);
        return table;
    }

    private String requestMatcher(String request){
        String requestPattern = "^\s*(insert\s+values)|^\s*(update\s+values)|^\s*delete|^\s*select";

        Matcher matcher = Pattern.compile(requestPattern).matcher(request.toLowerCase());
        String rst;

        if(matcher.find()) {
            rst = matcher.group();
            System.out.println(rst);
            return rst;
        }
        return null;
    }
}
