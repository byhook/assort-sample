package sample.byhook.bean;

import java.util.List;

/**
 * 作者 : byhook
 * 时间 : 15-11-28.
 * 邮箱 : byhook@163.com
 * 功能 :
 */
public class AppItem {

    private String letterString;

    private List<AppBean> appBeans;

    public String getLetterString() {
        return letterString;
    }

    public void setLetterString(String letterString) {
        this.letterString = letterString;
    }

    public List<AppBean> getAppBeans() {
        return appBeans;
    }

    public void setAppBeans(List<AppBean> appBeans) {
        this.appBeans = appBeans;
    }
}
