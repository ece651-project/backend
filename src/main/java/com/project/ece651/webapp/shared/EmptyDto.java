package com.project.ece651.webapp.shared;

import java.io.Serializable;

// the data transfer object that is empty (only contains whether the request succeed and the response message)
public class EmptyDto implements Serializable {
    private static final long serialVersionUID = 4897972238819242L;
    private boolean success = true;
    private String responseMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }
}