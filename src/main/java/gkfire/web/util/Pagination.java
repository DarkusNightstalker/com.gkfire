package gkfire.web.util;

import gkfire.hibernate.AliasList;
import gkfire.hibernate.CriterionList;
import gkfire.hibernate.generic.interfac.IGenericService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pagination<T>
        implements Serializable {

    private static List<Object[]> rowsData = new ArrayList();

    static {
        rowsData.add(new Object[]{10, "10"});
        rowsData.add(new Object[]{15, "15"});
        rowsData.add(new Object[]{20, "20"});
        rowsData.add(new Object[]{30, "30"});
        rowsData.add(new Object[]{50, "50"});
        rowsData.add(new Object[]{100, "100"});
        rowsData.add(new Object[]{-1, "Todos"});
    }

    private Integer page;
    private Integer rows;
    private Integer lastPage;
    private String messageTemplate;
    private Object[] tempVariant;
    private Long totalRecords;
    private List<T> data;
    private IGenericService service;

    public Pagination(IGenericService service) {
        this("Mostrando <b>{START}</b> a <b>{END}</b> de <span class='text-primary'>{TR}</span> registros", service);
    }

    public Pagination(String messageTemplate, IGenericService service) {
        this.messageTemplate = messageTemplate;
        this.service = service;
        this.rows = ((Integer) ((Object[]) rowsData.get(0))[0]);
    }

    public String message() {
        try {
            return this.messageTemplate.replace("{TR}", this.totalRecords.toString()).replace("{START}", getRecordStart().toString()).replace("{END}", getRecordEnd().toString());
        } catch (Exception e) {
        }
        return "...";
    }

    public List getRowsData() {
        return rowsData;
    }

    public Integer getRecordStart() {
        try {
            int currentRows = this.rows < 0 ? this.totalRecords.intValue() : this.rows.intValue();
            return (this.page - 1) * currentRows + 1;
        } catch (Exception e) {
        }
        return null;
    }

    public void first() {
        search(1, this.tempVariant);
    }

    public void last() {
        search(this.lastPage, this.tempVariant);
    }

    public void next() {
        search(this.page + 1, this.tempVariant);
    }

    public void previous() {
        search(this.page - 1, this.tempVariant);
    }

    public void changeRows() {
        search(1, this.tempVariant);
    }

    public Integer getRecordEnd() {
        try {
            int currentRows = this.rows < 0 ? this.totalRecords.intValue() : this.rows.intValue();
            int v = this.page * currentRows;

            return v > this.totalRecords ? this.totalRecords.intValue() : v;
        } catch (Exception e) {
        }
        return null;
    }

    public void search(int page, Object... variant) {
        this.tempVariant = variant;
        this.totalRecords = null;
        CriterionList criterionList = null;
        AliasList aliasList = null;
        for (Object o : variant) {
            if ((o instanceof CriterionList)) {
                criterionList = (CriterionList) o;
            } else if ((o instanceof AliasList)) {
                aliasList = (AliasList) o;
            } else {
                if ((criterionList != null) && (aliasList != null)) {
                    break;
                }
            }
        }
        if (criterionList != null) {
            try {
                this.totalRecords = this.service.countRestrictions(criterionList, aliasList).longValue();
            } catch (Exception e) {
                this.totalRecords = 0L;
            }
        } else {
            this.totalRecords = this.service.count().longValue();
        }

        if (this.totalRecords != 0L) {
            int currentRows = this.rows < 0 ? this.totalRecords.intValue() : this.rows.intValue();
            this.lastPage = this.totalRecords.intValue() / currentRows;
            Integer localInteger;
            if (this.totalRecords.intValue() % currentRows != 0) {
                Pagination<T> localPagination = this;
                localInteger = localPagination.lastPage;
                localPagination.lastPage = localPagination.lastPage + 1;
            }
            if (page > this.lastPage) {
                page = this.lastPage;
            }
            this.page = page;
            this.data = this.service.addRestrictionsVariant(currentRows, page, variant);
        } else {
            this.page = null;
            this.lastPage = null;
            this.data = Collections.EMPTY_LIST;
        }
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return this.rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setMessageTemplate(String messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    public Long getTotalRecords() {
        return this.totalRecords;
    }

    public List<T> getData() {
        return this.data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setService(IGenericService service) {
        this.service = service;
    }

    public Integer getLastPage() {
        return this.lastPage;
    }
}
