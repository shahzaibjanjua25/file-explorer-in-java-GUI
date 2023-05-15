# file-explorer-in-java-GUI
Project Report: File Explorer

1. Introduction:
The File Explorer project is a Java-based application that provides a graphical user interface (GUI) for browsing and managing files and directories on a computer. It allows users to navigate through the file system, copy, paste, delete files, open folders, and search for specific files based on user input.

2. Features:
The File Explorer application offers the following features:

- File Tree: Displays the hierarchical file structure in a tree format, allowing users to browse through directories and view files.
- Path Field: Shows the current path of the selected directory or file.
- Copy: Copies a selected file or directory to the clipboard.
- Paste: Pastes the copied file or directory to the current directory.
- Delete: Deletes the selected file or directory permanently.
- Open Folder: Navigates to and opens the selected folder.
- Search: Searches for files based on a user-provided file name.

3. Implementation Details:
The application is implemented using Java Swing, which provides a set of GUI components for building desktop applications. The key components used in the implementation are as follows:

- JFrame: Represents the main application window.
- JTree: Displays the file structure in a tree format.
- JTextField: Shows the current path and allows users to input search queries.
- JButton: Enables user interactions such as copy, paste, delete, open folder, and search.
- ActionListener: Listens for button clicks and performs the corresponding actions.
- DefaultMutableTreeNode: Represents nodes in the file tree.
- DefaultTreeModel: Manages the tree structure and updates its content.

The application follows the Model-View-Controller (MVC) architectural pattern. The file tree data is stored in a DefaultTreeModel object, which acts as the model. The JFrame represents the view, displaying the GUI components to the user. The ActionListener functions act as the controller, handling user interactions and updating the model and view accordingly.

4. Usage:
Upon launching the application, the user is presented with a graphical interface showing the file tree on the left side and buttons on the bottom-right side. The user can navigate through directories, select files or folders, copy, paste, delete, open folders, and search for files using the provided buttons.

- Copy: Select a file or folder from the file tree and click the "Copy" button. The selected file will be copied to the clipboard.
- Paste: Navigate to the desired destination directory in the file tree and click the "Paste" button. The copied file will be pasted into the current directory.
- Delete: Select a file or folder from the file tree and click the "Delete" button. A confirmation dialog will appear, and upon confirmation, the file or folder will be permanently deleted.
- Open Folder: Select a folder from the file tree and click the "Open Folder" button. The application will navigate to the selected folder, updating the file tree accordingly.
- Search: Click the "Search" button. A dialog box will prompt the user to enter a file name to search for. After providing the search query, the application will display the search results in a separate tree structure.

5. Conclusion:
The File Explorer project provides an intuitive and user-friendly interface for managing files and directories. With its features such as navigation, copy-paste, delete, folder opening, and search functionality, it offers users a convenient way to interact with their file system. The project can be further enhanced by adding additional features such as file renaming, file properties display, and file content preview.
