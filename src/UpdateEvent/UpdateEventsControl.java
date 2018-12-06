package UpdateEvent;

import java.util.ArrayList;

public class UpdateEventsControl {
    private static ArrayList<UpdateEventsListener> listeners = new ArrayList<>();

    public static void addListener(UpdateEventsListener listener){
        listeners.add(listener);
    }

    public static void removeListener(UpdateEventsListener listener){
        listeners.remove(listener);
    }

    public static void callListeners(byte updateMsg){
        for(UpdateEventsListener listener: listeners){
            listener.onDataChanged(updateMsg);
        }
    }
}
