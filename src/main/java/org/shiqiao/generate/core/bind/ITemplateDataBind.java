package org.shiqiao.generate.core.bind;

import org.shiqiao.generate.core.bo.TableBO;

import java.io.File;

public interface ITemplateDataBind {

    String bindDataToString(TableBO tableBO);
}
