package com.project.ece651.webapp.shared;

import java.io.Serializable;

// the basic data transfer object that  contains whether the request succeed and the response message
// used as response
public class MsgDto implements Serializable {
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