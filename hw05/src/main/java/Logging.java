import annotations.Log;

public interface Logging {

    @Log
    void calculation(int param);

    @Log
    void calculation(int param, String param2);

    @Log
    void calculation(int param, int param2, int param3);

}
