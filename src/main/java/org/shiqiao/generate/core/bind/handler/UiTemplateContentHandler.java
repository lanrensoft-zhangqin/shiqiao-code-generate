package org.shiqiao.generate.core.bind.handler;

import org.shiqiao.generate.core.bo.ColumnBO;
import org.shiqiao.generate.core.bo.TableBO;

import java.util.List;

public class UiTemplateContentHandler implements ITemplateContentHandler {
    @Override
    public String get(TableBO tableBO) {
        StringBuilder stringBuilder = new StringBuilder();
        List<ColumnBO> columns = tableBO.getColumns();
        for (ColumnBO column : columns) {
            if(column.getCanAdd()==true){
                switch (column.getUi()){
                    case INPUT:
                        stringBuilder.append(new InputUiHtmlHandler().get(column));
                        break;
                    case DIC:
                        break;
                    case DATE:
                        break;
                    case FILE:
                        break;
                    case RADIO:
                        break;
                    case IMAGE:
                        break;
                    case SELECT:
                        stringBuilder.append(new SelectUiHtmlHandler().get(column));
                        break;
                    case TEXTAREA:
                        stringBuilder.append(new TextAreaUiHtmlHandler().get(column));
                        break;
                    case HTML:
                        break;
                    default:
                        stringBuilder.append(new InputUiHtmlHandler().get(column));
                        break;
                }
            }
        }
        return stringBuilder.toString();
    }
}
