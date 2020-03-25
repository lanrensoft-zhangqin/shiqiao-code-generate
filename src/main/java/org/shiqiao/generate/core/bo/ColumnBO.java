package org.shiqiao.generate.core.bo;

import org.shiqiao.generate.core.enu.FormUiEnum;

public class ColumnBO {
    //标题
    private String title;
    //名称
    private String name;
    //字段长度
    private Integer length;
    //ui组件
    private FormUiEnum ui;
    //组件值
    private String uiValue;
    //是否必填
    private boolean isRequired;
    //是否是主键
    private boolean isId;
    //java类型
    private String javaType;
    //是否能在页面新增
    private Boolean canAdd;

    public Boolean getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(Boolean canAdd) {
        this.canAdd = canAdd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public FormUiEnum getUi() {
        return ui;
    }

    public void setUi(FormUiEnum ui) {
        this.ui = ui;
    }

    public String getUiValue() {
        return uiValue;
    }

    public void setUiValue(String uiValue) {
        this.uiValue = uiValue;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
