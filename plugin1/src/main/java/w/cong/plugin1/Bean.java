package w.cong.plugin1;

import w.cong.mypluginlibrary.IBean;
import w.cong.mypluginlibrary.ICallback;

public class Bean implements IBean {

    private String name = "wangcong";

    private ICallback callback;

    public void register(ICallback callback) {
        this.callback = callback;
        clickButton();
    }

    public void clickButton() {
        callback.sendResult("Hello: "+this.name);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
