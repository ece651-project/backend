package com.project.ece651.webapp.shared;

import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;

public class MsgResponse implements Serializable {
    private static final long serialVersionUID = -7465894520894100732L;

    public interface MsgView {}

    @JsonView(MsgView.class)
    private boolean success;

    @JsonView(MsgView.class)
    private String msg;

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
