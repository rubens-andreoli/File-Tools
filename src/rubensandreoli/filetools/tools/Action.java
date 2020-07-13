package rubensandreoli.filetools.tools;

public interface Action<T> {
    T perform();
    void interrupt();
}
