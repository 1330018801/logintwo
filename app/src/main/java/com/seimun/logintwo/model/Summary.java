package com.seimun.logintwo.model;

/**
 * Created by lijun on 11/16/15.
 */
public class Summary {
    private Integer record_id;
    private String title, clinic, provider, service_time, type_alias, item_alias;

    public Summary () {
    }

    public Integer getRecordId() {
        return this.record_id;
    }

    public void setRecordId(Integer record_id) {
        this.record_id = record_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClinic() {
        return this.clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getServiceTime() {
        return this.service_time;
    }

    public void setServiceTime(String service_time) {
        this.service_time = service_time;
    }

    public String getTypeAlias() {
        return this.type_alias;
    }

    public void setTypeAlias(String type_alias) {
        this.type_alias = type_alias;
    }

    public String getItemAlias() {
        return this.item_alias;
    }

    public void setItemAlias(String item_alias) {
        this.item_alias = item_alias;
    }
}
