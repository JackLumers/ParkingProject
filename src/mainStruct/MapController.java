package mainStruct;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapController {

    @FXML
    public WebView webView;

    public void initialize() {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("/map/mapSource.html").toExternalForm());
    }

}
