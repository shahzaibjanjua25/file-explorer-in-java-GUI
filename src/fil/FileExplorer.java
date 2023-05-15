package fil;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileExplorer extends JFrame {
    private JTree fileTree;
    private JTextField pathField;
    private JButton copyButton;
    private JButton pasteButton;
    private JButton deleteButton;
    private JButton openFolderButton;
    private JButton search;
private List<String> searchResults;
    private DefaultTreeModel treeModel;
    private File copiedFile;
    private DefaultMutableTreeNode searchRootNode;

    public FileExplorer() {
        super("File Explorer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Create the root node for the tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
         searchRootNode = new DefaultMutableTreeNode("Search Results");
        treeModel = new DefaultTreeModel(root);
        searchResults = new ArrayList<>();
    
        // Create the file tree
        fileTree = new JTree(treeModel);
        fileTree.setRootVisible(false);

        // Create the scroll pane for the file tree
        JScrollPane treeScrollPane = new JScrollPane(fileTree);
        treeScrollPane.setPreferredSize(new Dimension(300, 600));

        // Create the path field
        pathField = new JTextField();
        pathField.setEditable(false);

        // Create the buttons
        copyButton = new JButton("Copy");
        pasteButton = new JButton("Paste");
        deleteButton = new JButton("Delete");
        openFolderButton = new JButton("Open Folder");
        search = new JButton("Search");
        openFolderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openFolder();
            }
        });

        // Add action listeners to the buttons
        copyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyFile();
            }
        });

        pasteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pasteFile();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteFile();
            }
        });
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchFiles();
            }
        });

        // Create the panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(openFolderButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(pasteButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(search);

        // Create the panel for the path field and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(pathField, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add components to the main frame
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(treeScrollPane, BorderLayout.WEST);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);

        // Set the initial path and update the tree
        String initialPath = System.getProperty("user.home");
        updateFileTree(initialPath);

        setVisible(true);
    }

   private void updateFileTree(String path) {
    pathField.setText(path);

    DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
    root.removeAllChildren();

    File directory = new File(path);
    File[] files = directory.listFiles();

    if (files != null) {
        // Add a parent directory node
        DefaultMutableTreeNode parentDirNode = new DefaultMutableTreeNode("..");
        root.add(parentDirNode);

        for (File file : files) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
            root.add(node);
        }

        treeModel.reload();

        // Expand the root node to show the files and folders
        fileTree.expandRow(0);
    }
}


private void copyFile() {
    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
    if (selectedNode != null) {
        String selectedFileName = selectedNode.getUserObject().toString();
        String currentPath = pathField.getText();
        copiedFile = new File(currentPath, selectedFileName);
    }
}

private void pasteFile() {
    if (copiedFile != null) {
        String currentPath = pathField.getText();
        File destinationFile = new File(currentPath, copiedFile.getName());

        try {
            Files.copy(copiedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            updateFileTree(currentPath);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to paste file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

private void deleteFile() {
    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
    if (selectedNode != null) {
        String selectedFileName = selectedNode.getUserObject().toString();
        String currentPath = pathField.getText();
        File fileToDelete = new File(currentPath, selectedFileName);

        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this file?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            try {
                if (fileToDelete.isDirectory()) {
                    deleteDirectory(fileToDelete);
                } else {
                    Files.delete(fileToDelete.toPath());
                }
                updateFileTree(currentPath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Failed to delete file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

private void openFolder() {
    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();
    if (selectedNode != null) {
        String selectedFileName = selectedNode.getUserObject().toString();
        String currentPath = pathField.getText();
        File selectedFile = new File(currentPath, selectedFileName);

        if (selectedFile.isDirectory()) {
            updateFileTree(selectedFile.getAbsolutePath());
        }
    }
}
 private void searchFiles() {
    String searchQuery = JOptionPane.showInputDialog(this, "Enter file name to search:");

    if (searchQuery != null && !searchQuery.isEmpty()) {
        String currentPath = pathField.getText();
        searchRootNode.removeAllChildren(); // Clear previous search tree nodes
        searchFilesInDirectory(new File(currentPath), searchQuery);

        // Update the GUI with the search results
        updateFileTree(searchRootNode);
    }
}

private void searchFilesInDirectory(File directory, String searchQuery) {
    File[] files = directory.listFiles();

    if (files != null) {
        for (File file : files) {
            if (file.isDirectory()) {
                searchFilesInDirectory(file, searchQuery);
            } else {
                if (file.getName().contains(searchQuery)) {
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(file.getName());
                    searchRootNode.add(node);
                }
            }
        }
    }
}


    private void updateFileTree(DefaultMutableTreeNode rootNode) {
        treeModel.setRoot(rootNode);
        treeModel.reload();
        expandAllNodes(fileTree, 0, fileTree.getRowCount());
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            new FileExplorer();
        }
    });
}
}