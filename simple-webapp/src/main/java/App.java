import static spark.Spark.*;
import com.google.gson.Gson;
import java.util.*;

public class App {
    public static void main(String[] args) {
        port(9090);
        staticFiles.location("/static");

        Map<String, Item> items = new HashMap<>();

        // Get all items
        get("/items", (req, res) -> {
            return new Gson().toJson(new ArrayList<>(items.values()));
        });

        // Get a specific item by ID
        get("/items/:id", (req, res) -> {
            String id = req.params(":id");
            Item item = items.get(id);
            if (item != null) {
                return new Gson().toJson(item);
            }
            res.status(404);
            return "Item not found";
        });

        // Add a new item
        post("/items", (req, res) -> {
            Item item = new Gson().fromJson(req.body(), Item.class);
        
            // Check for empty or null name and description
            if (item.getName() == null || item.getName().trim().isEmpty() ||
                item.getDescription() == null || item.getDescription().trim().isEmpty()) {
                res.status(400);
                return "Name and description must not be empty";
            }
        
            String id = UUID.randomUUID().toString();
            item.setId(id);
            items.put(id, item);
            res.status(201);
            return "Item added successfully";
        });

        // Update an existing item
        put("/items/:id", (req, res) -> {
            String id = req.params(":id");
            Item updatedItem = new Gson().fromJson(req.body(), Item.class);
            Item existingItem = items.get(id);
            if (existingItem != null) {
                existingItem.setName(updatedItem.getName());
                existingItem.setDescription(updatedItem.getDescription());
                return "Item updated successfully";
            }
            res.status(404);
            return "Item not found";
        });

        // Delete an item
        delete("/items/:id", (req, res) -> {
            String id = req.params(":id");
            Item removed = items.remove(id);
            if (removed != null) {
                return "Item deleted successfully";
            }
            res.status(404);
            return "Item not found";
        });

        // Delete an item
        delete("/items", (req, res) -> {
            items.clear();
            return "Items deleted successfully";
        });
    }

    // Item class
    static class Item {
        private String id;
        private String name;
        private String description;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
