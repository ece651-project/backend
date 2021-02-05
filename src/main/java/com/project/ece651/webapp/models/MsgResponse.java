package com.project.ece651.webapp.models;

import java.io.Serializable;

public class MsgResponse implements Serializable {
    private static final long serialVersionUID = -7465894520894100732L;

    private boolean success;
    private String msg;

    public interface MsgView {}

    public MsgResponse() {
    }

    public MsgResponse(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
