package org.shiqiao.generate;

import com.google.common.collect.Lists;
import org.shiqiao.generate.core.bind.FormTemplateDataBind;
import org.shiqiao.generate.core.bind.ITemplateDataBind;
import org.shiqiao.generate.core.bo.ColumnBO;
import org.shiqiao.generate.core.bo.TableBO;
import org.shiqiao.generate.core.enu.FormUiEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

//@SpringBootApplication
public class GenerateApplication {

	public static void main(String[] args) {
		ITemplateDataBind templateDataBind = new FormTemplateDataBind();
		TableBO tableBO = new TableBO();
		tableBO.setTitle("短信模板配置");
		tableBO.setName("smsTemplate");
		tableBO.setCanDelete(0);
		tableBO.setCanAdd(1);
		tableBO.setCanEdit(0);
		tableBO.setCanQuery(1);
		List<ColumnBO> columns = Lists.newArrayList();
		ColumnBO columnBO = new ColumnBO();
		columnBO.setCanAdd(true);
		columnBO.setTitle("模板名称");
		columnBO.setName("templateName");
		columnBO.setLength(20);
		columnBO.setUi(FormUiEnum.INPUT);
		columnBO.setUiValue("");
		columnBO.setRequired(true);
		columnBO.setId(true);
		columnBO.setJavaType("String");
		ColumnBO columnBO2 = new ColumnBO();
		columnBO2.setCanAdd(true);
		columnBO2.setTitle("模板内容");
		columnBO2.setName("templateContent");
		columnBO2.setLength(300);
		columnBO2.setUi(FormUiEnum.TEXTAREA);
		columnBO2.setUiValue("");
		columnBO2.setRequired(true);
		columnBO2.setId(true);
		columnBO2.setJavaType("String");
		ColumnBO columnBO3 = new ColumnBO();
		columnBO3.setCanAdd(true);
		columnBO3.setTitle("模板类型");
		columnBO3.setName("templateType");
		columnBO3.setLength(20);
		columnBO3.setUi(FormUiEnum.SELECT);
		columnBO3.setUiValue("{\"1\":\"短信通知\",\"2\":\"验证码\"}");
		columnBO3.setRequired(true);
		columnBO3.setId(true);
		columnBO3.setJavaType("String");
		columns.add(columnBO);
		columns.add(columnBO2);
		columns.add(columnBO3);
		tableBO.setColumns(columns);
		String string = templateDataBind.bindDataToString(tableBO);
		System.out.printf(string);
	}

}
