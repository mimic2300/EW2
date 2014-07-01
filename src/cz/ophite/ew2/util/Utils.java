package cz.ophite.ew2.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import cz.ophite.ew2.game.json.base.UniqueCode;

public final class Utils
{
    private static final Random RAND = new Random();

    private Utils()
    {}

    public static Color randomColor()
    {
        return new Color(RAND.nextFloat(), RAND.nextFloat(), RAND.nextFloat());
    }

    public static <T extends UniqueCode> List<T> uniqueCheck(Collection<T> collection)
    {
        Map<String, T> buffer = new HashMap<String, T>();
        Set<T> dupes = new HashSet<T>();

        for (T obj : collection) {
            if (buffer.containsKey(obj.getCode())) {
                dupes.add(obj);
            } else {
                buffer.put(obj.getCode(), obj);
            }
        }
        return new ArrayList<T>(dupes);
    }

    public static <T extends UniqueCode> void print(Collection<T> collection)
    {
        for (T obj : collection) {
            System.out.println("Dupe: " + obj.getCode());
        }
    }
}
