package com.seimun.logintwo.model;

/**
 * Created by lijun on 11/16/15.
 */
public class Summary {
    private Integer record_id;
    private String title, clinic, provider, service_time;

    public Summary () {
    }

    public Summary(Integer record_id, String title, String clinic,
                   String provider, String service_time) {
        this.record_id = record_id;
        this.title = title;
        this.clinic = clinic;
        this.provider = provider;
        this.service_time = service_time;
    }

    public Integer getRecordId() {
        return record_id;
    }

    public void setRecordId(Integer record_id) {
        this.record_id = record_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getServiceTime() {
        return service_time;
    }

    public void setServiceTime(String service_time) {
        this.service_time = service_time;
    }

}
