package org.shiqiao.generate.core.bo;

import java.util.List;

public class TableBO {
    //标题
    private String title;
    //名称
    private String name;
    //是否可以删除
    private Integer canDelete;
    //是否能新增
    private Integer canAdd;
    //是否可编辑
    private Integer canEdit;
    //是否可查询
    private Integer canQuery;
    //列
    private List<ColumnBO> columns;

    public List<ColumnBO> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnBO> columns) {
        this.columns = columns;
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

    public Integer getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
    }

    public Integer getCanAdd() {
        return canAdd;
    }

    public void setCanAdd(Integer canAdd) {
        this.canAdd = canAdd;
    }

    public Integer getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Integer canEdit) {
        this.canEdit = canEdit;
    }

    public Integer getCanQuery() {
        return canQuery;
    }

    public void setCanQuery(Integer canQuery) {
        this.canQuery = canQuery;
    }
}
