package com.example.atraverf.uvlive.ui.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class ListContentManager {

    /**
     * An array of sample (dummy) items.
     */
    private static List<ListItem> ITEMS = new ArrayList<ListItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    private static Map<String, ListItem> ITEM_MAP = new HashMap<String, ListItem>();

    private static final int COUNT = 25;

    public static void setListItems() {
        //TODO: Buscar en el almacenamiento las conversaciones disponibles, si no hay hacer consulta
        //al servicio
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    public static List<ListItem> getListItems(){
        return ITEMS;
    }

    public static Map<String, ListItem> getItemMap(){
        return ITEM_MAP;
    }
    private static void addItem(ListItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ListItem createDummyItem(int position) {
        return new ListItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class ListItem {
        public String id;
        public String content;
        public String details;

        public ListItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
