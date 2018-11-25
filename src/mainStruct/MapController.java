package mainStruct;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

public class MapController {

    @FXML
    public WebView webView;

    private WebEngine webEngine;
    private String saveLocation;
    private String mapSource;

    public void initialize() {
        webEngine = webView.getEngine();
        mapSource = getClass().getResource("/map/mapSource.html").toExternalForm();
        webEngine.load(mapSource);
        saveLocation = webEngine.getLocation();

        webEngine.setOnAlert(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                onAlert(event);
            }
        });
        webEngine.setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> event) {
                onStatusChanged();
            }
        });
    }

    private void onAlert(WebEvent<String> event){
        System.out.println(event.getData());
    }

    private void onStatusChanged(){
        if(!webEngine.getLocation().equals(saveLocation)){
            webEngine.load(mapSource);
        }
    }
}
