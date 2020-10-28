package Services;

public class FillRequest {
    String username;
    int generations;

    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }
}
