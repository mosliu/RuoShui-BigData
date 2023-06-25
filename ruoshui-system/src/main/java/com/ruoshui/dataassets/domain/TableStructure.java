package com.ruoshui.dataassets.domain;

import java.util.List;



/**
 * 元数据树结构
 *
 * @author ruoshui
 * @date 2022-07-04
 */
public class TableStructure {
    private Long id;

    private String label;

    private List<TableStructure> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TableStructure> getChildren() {
        return children;
    }

    public void setChildren(List<TableStructure> children) {
        this.children = children;
    }
}
