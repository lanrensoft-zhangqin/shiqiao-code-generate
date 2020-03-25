package org.shiqiao.generate.core.bind.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.shiqiao.generate.core.bo.ColumnBO;

import java.util.Map;
import java.util.Set;

public class SelectUiHtmlHandler implements IUiHtmlHandelr {


    @Override
    public String get(ColumnBO columnBO) {
        String required = "";
        if(columnBO.isRequired()){
            required = "required";
        }
        String uiValue = columnBO.getUiValue();
        JSONObject jsonObject = JSON.parseObject(uiValue);
        Set<Map.Entry<String, Object>> entries = jsonObject.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> entry : entries) {
            stringBuilder.append("        <option value=\""+entry.getKey()+"\">"+entry.getValue()+"</option>\n");
        }
        return "  <div class=\"layui-form-item\">\n" +
                "    <label class=\"layui-form-label\">单行选择框</label>\n" +
                "    <div class=\"layui-input-inline\">\n" +
                "      <select name=\""+columnBO.getName()+"\" lay-filter=\""+columnBO.getName()+"\">\n" +
                "        <option value=\"\"></option>\n" +
                ""+stringBuilder.toString()+""+
                "      </select>\n" +
                "    </div>\n" +
                "  </div>";
    }
}
