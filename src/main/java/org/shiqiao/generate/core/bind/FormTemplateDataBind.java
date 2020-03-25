package org.shiqiao.generate.core.bind;

import org.beetl.core.Template;
import org.shiqiao.generate.core.bind.handler.ScriptTemplateContentHandler;
import org.shiqiao.generate.core.bind.handler.UiTemplateContentHandler;
import org.shiqiao.generate.core.bo.TableBO;

import java.util.HashMap;

public class FormTemplateDataBind implements ITemplateDataBind {
    @Override
    public String bindDataToString(TableBO tableBO) {

        //封装模板使用的数据
        HashMap<String, Object> bindData = new HashMap<>();
        bindData.put("title",tableBO.getTitle());
        //表单字段内容组件获取
        bindData.put("ui",new UiTemplateContentHandler().get(tableBO));
        //表单需要的js脚本获取
        bindData.put("script",new ScriptTemplateContentHandler().get(tableBO));
        //绑定模板(这里我这使用三方插件，如 freemarker，beetl)
        String string = BeetlTemplateGenUtils.bindToString(bindData, "formTemplate.html");
        //输出
        return string;
    }
}
