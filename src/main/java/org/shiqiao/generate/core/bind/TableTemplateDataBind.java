package org.shiqiao.generate.core.bind;

import org.shiqiao.generate.core.bo.ColumnBO;
import org.shiqiao.generate.core.bo.TableBO;

import java.util.HashMap;
import java.util.List;

public class TableTemplateDataBind implements ITemplateDataBind{


    @Override
    public String bindDataToString(TableBO tableBO) {
        //封装模板使用的数据
        HashMap<String, Object> bindData = new HashMap<>();
        //绑定模板(这里我这使用三方插件，如 freemarker，beetl)
        String string = BeetlTemplateGenUtils.bindToString(bindData, "formTemplate.html");
        //输出
        return string;
    }
}
