package gkfire.web.util;

import java.io.Serializable;

public abstract class JavaScriptMessage implements Serializable {

    public abstract String toJavaScript();

    public void execute() {
        System.out.println(toJavaScript());
        BeanUtil.exceuteJS(toJavaScript());
    }
}
