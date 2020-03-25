package org.shiqiao.generate.core.bind.handler;

import org.shiqiao.generate.core.bo.ColumnBO;

public class TextAreaUiHtmlHandler implements IUiHtmlHandelr {


    @Override
    public String get(ColumnBO columnBO) {
        return "   <div class=\"layui-form-item layui-form-text\">\n" +
                "    <label class=\"layui-form-label\">"+columnBO.getTitle()+"</label>\n" +
                "    <div class=\"layui-input-block\">\n" +
                "      <textarea name=\""+columnBO.getName()+"\" placeholder=\"请输入内容\" class=\"layui-textarea\"></textarea>\n" +
                "    </div>\n" +
                "  </div>\n";
    }
}
