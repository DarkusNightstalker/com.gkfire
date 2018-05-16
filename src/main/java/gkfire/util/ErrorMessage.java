package gkfire.util;

import java.io.Serializable;

public class ErrorMessage implements Serializable {

    private String code;
    private String content;
    private Object[] options;

    public ErrorMessage(String code, String content) {
        this.code = code;
        this.content = content;
    }

    public ErrorMessage(String code, String content, Object... options) {
        this.code = code;
        this.content = content;
        this.options = options;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object[] getOptions() {
        return this.options;
    }

    public void setOptions(Object[] options) {
        this.options = options;
    }
}
