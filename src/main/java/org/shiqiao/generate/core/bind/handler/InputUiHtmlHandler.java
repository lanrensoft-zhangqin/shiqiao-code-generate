package org.shiqiao.generate.core.bind.handler;

import org.shiqiao.generate.core.bo.ColumnBO;
import org.shiqiao.generate.core.bo.TableBO;

public class InputUiHtmlHandler implements IUiHtmlHandelr {


    @Override
    public String get(ColumnBO columnBO) {
        String required = "";
        if(columnBO.isRequired()){
            required = "required";
        }
        return "  <div class=\"layui-form-item\">\n" +
                "    <label class=\"layui-form-label\">"+columnBO.getTitle()+"</label>\n" +
                "    <div class=\"layui-input-inline\">\n" +
                "      <input type=\"text\" name=\""+columnBO.getName()+"\" lay-verify=\""+required+"\" placeholder=\"请输入\" autocomplete=\"off\" class=\"layui-input\">\n" +
                "    </div>\n" +
                "  </div>\n";
    }
}
