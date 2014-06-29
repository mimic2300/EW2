package cz.ophite.ew2.util;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class EventHandler<T extends EventListener>
{
    protected final List<T> listeners = new ArrayList<T>();

    public void addListener(T listener)
    {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void removeListener(T listener)
    {
        if (listeners.contains(listener)) {
            listeners.remove(listener);
        }
    }

    public void removeAll()
    {
        listeners.clear();
    }
}
