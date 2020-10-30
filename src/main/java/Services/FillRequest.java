package Services;

public class FillRequest {
    String username;
    int generations;

    public FillRequest(String url) {
        url = url.substring(5);

        char slashCheck = url.charAt(url.length()-1);

        if(slashCheck == '/') {
            url = url.substring(0,url.length());
            username = url;
            generations = 4;
        }

        //int index = url.indexOf("/");
        username = url.substring(1, url.length()-2);
        generations = Integer.parseInt((url.substring(url.length()-1)));
    }
}
