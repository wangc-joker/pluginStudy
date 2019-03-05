package w.cong.mypluginlibrary;

public interface IBean {
    String getName();

    void setName(String name);

    void register(ICallback callback);
}
