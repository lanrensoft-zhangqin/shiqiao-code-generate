package org.shiqiao.generate.core.bind.handler;

import org.shiqiao.generate.core.bo.ColumnBO;
import org.shiqiao.generate.core.bo.TableBO;
import org.shiqiao.generate.core.enu.FormUiEnum;

public class ScriptTemplateContentHandler implements ITemplateContentHandler {
    @Override
    public String get(TableBO tableBO) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ColumnBO column : tableBO.getColumns()) {
            if(column.getUi()==FormUiEnum.DATE){
                stringBuilder.append("  laydate.render({\n" +
                        "    elem: '#"+column.getName()+"'\n" +
                        "  });\n");
            }
        }
        return stringBuilder.toString();
    }
}
