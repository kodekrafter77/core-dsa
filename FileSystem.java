import java.util.*;

class FileSystem {

    /**
     * The Node class represents either a file or a directory in our file system tree.
     */
    private class Node {
        String name;
        boolean isFile = false;
        // Children are stored in a map for fast O(1) lookup by name.
        Map<String, Node> children = new HashMap<>();
        // Content is a StringBuilder for efficient appends.
        StringBuilder content = new StringBuilder();

        Node(String name) {
            this.name = name;
        }
    }

    private final Node root;

    /**
     * Initializes the file system with a root directory "/".
     */
    public FileSystem() {
        this.root = new Node("/");
    }

    /**
     * Lists the contents of a directory or the name of a file.
     */
    public List<String> ls(String path) {
        Node node = findNode(path);
        List<String> result = new ArrayList<>();

        if (node.isFile) {
            // If the path points to a file, return just its name.
            result.add(node.name);
        } else {
            // If it's a directory, return a sorted list of its children's names.
            result.addAll(node.children.keySet());
            Collections.sort(result);
        }
        return result;
    }

    /**
     * Creates a directory path, including any necessary parent directories.
     */
    public void mkdir(String path) {
        findNodeAndCreate(path); // This helper does the traversal and creation.
    }

    /**
     * Appends content to a file, creating the file and path if they don't exist.
     */
    public void addContentToFile(String filePath, String content) {
        Node node = findNodeAndCreate(filePath);
        node.isFile = true;
        node.content.append(content);
    }

    /**
     * Reads the entire content of a file.
     */
    public String readContentFromFile(String filePath) {
        Node node = findNode(filePath);
        return node.content.toString();
    }

    // --- Helper Functions ---

    /**
     * Traverses a path and returns the destination node.
     * Assumes the path already exists.
     */
    private Node findNode(String path) {
        if (path.equals("/")) {
            return this.root;
        }
        
        Node current = this.root;
        // Split path and start from 1 to ignore the initial empty string.
        String[] parts = path.split("/");
        for (int i = 1; i < parts.length; i++) {
            current = current.children.get(parts[i]);
        }
        return current;
    }
    
    /**
     * Traverses a path, creating directories as needed, and returns the destination node.
     * This is used by mkdir and addContentToFile.
     */
    private Node findNodeAndCreate(String path) {
        Node current = this.root;
        String[] parts = path.split("/");
        for (int i = 1; i < parts.length; i++) {
            // Use computeIfAbsent to get the child, or create it if it doesn't exist.
            current = current.children.computeIfAbsent(parts[i], Node::new);
        }
        return current;
    }
}