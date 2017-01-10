package com.bonc.kongdy.significant.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kongdy on 2016/11/23.
 */
public class GirlsBean implements Serializable {

    private String error;
    private List<GirlBean> results;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<GirlBean> getResults() {
        return results;
    }

    public void setResults(List<GirlBean> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "GirlsBean{" +
                "error='" + error + '\'' +
                ", results=" + results +
                '}';
    }
}
