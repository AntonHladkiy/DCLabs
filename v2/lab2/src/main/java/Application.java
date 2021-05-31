import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame {
    private static JFrame frame;

    private static AuthorDTO currentAuthor = null;
    private static BookDTO currentBook = null;

    private static boolean editMode = false;
    private static boolean authorMode = true;

    private static JButton btnAddAuthor = new JButton("Add Author");
    private static JButton btnAddBook = new JButton("Add Book");
    private static JButton btnEdit = new JButton("Edit Data");
    private static JButton btnBack = new JButton("Back");
    private static JButton btnSave = new JButton("Save");
    private static JButton btnDelete = new JButton("Delete");

    private static Box menuPanel = Box.createVerticalBox();
    private static Box actionPanel = Box.createVerticalBox();
    private static Box comboPanel = Box.createVerticalBox();
    private static Box bookPanel = Box.createVerticalBox();
    private static Box authorPanel = Box.createVerticalBox();

    private static JComboBox comboAuthor = new JComboBox();
    private static JComboBox comboBook = new JComboBox();

    private static JTextField textAuthorName = new JTextField(30);
    private static JTextField textBookName = new JTextField(30);
    private static JTextField textBookAuthorName = new JTextField(30);
    private static JTextField textBookPages = new JTextField(30);

    private Application() {
        super("Library");
        frame = this;
        frame.setPreferredSize(new Dimension(400, 500));
        frame.setMaximumSize(new Dimension(400, 500));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                frame.dispose();
                DBConnection.closeConnection();
                System.exit(0);
            }
        });
        Box box = Box.createVerticalBox();
        sizeAllElements();
        frame.setLayout(new FlowLayout());

        // Menu
        menuPanel.add(btnAddAuthor);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddAuthor.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                authorMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                authorPanel.setVisible(true);
                bookPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnAddBook);
        menuPanel.add(Box.createVerticalStrut(20));
        btnAddBook.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = false;
                authorMode = false;
                menuPanel.setVisible(false);
                comboPanel.setVisible(false);
                authorPanel.setVisible(false);
                bookPanel.setVisible(true);
                actionPanel.setVisible(true);
                pack();
            }
        });
        menuPanel.add(btnEdit);
        menuPanel.add(Box.createVerticalStrut(20));
        btnEdit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                editMode = true;
                menuPanel.setVisible(false);
                comboPanel.setVisible(true);
                authorPanel.setVisible(false);
                bookPanel.setVisible(false);
                actionPanel.setVisible(true);
                pack();
            }
        });

        // ComboBoxes
        comboPanel.add(new JLabel("Author:"));
        comboPanel.add(comboAuthor);
        comboPanel.add(Box.createVerticalStrut(20));
        comboAuthor.addActionListener(e -> {
            String name = (String) comboAuthor.getSelectedItem();
            currentAuthor = AuthorDAO.findByName((String) comboAuthor.getSelectedItem());
            authorMode = true;
            authorPanel.setVisible(true);
            bookPanel.setVisible(false);
            fillAuthorFields();
            pack();
        });
        comboPanel.add(new JLabel("Book:"));
        comboPanel.add(comboBook);
        comboPanel.add(Box.createVerticalStrut(20));
        comboBook.addActionListener(e -> {
            String name = (String) comboBook.getSelectedItem();
            currentBook = BookDAO.findByName((String) comboBook.getSelectedItem());
            authorMode = false;
            authorPanel.setVisible(false);
            bookPanel.setVisible(true);
            fillBookFields();
            pack();
        });
        fillComboBoxes();
        comboPanel.setVisible(false);

        // Book Fields
        bookPanel.add(new JLabel("Name:"));
        bookPanel.add(textBookName);
        bookPanel.add(Box.createVerticalStrut(20));
        bookPanel.add(new JLabel("Author Name:"));
        bookPanel.add(textBookAuthorName);
        bookPanel.add(Box.createVerticalStrut(20));
        bookPanel.add(new JLabel("Pages:"));
        bookPanel.add(textBookPages);
        bookPanel.add(Box.createVerticalStrut(20));
        bookPanel.setVisible(false);

        // Author Fields
        authorPanel.add(new JLabel("Name:"));
        authorPanel.add(textAuthorName);
        authorPanel.add(Box.createVerticalStrut(20));
        authorPanel.setVisible(false);

        // Action Bar		
        actionPanel.add(btnSave);
        actionPanel.add(Box.createVerticalStrut(20));
        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                save();
            }
        });
        actionPanel.add(btnDelete);
        actionPanel.add(Box.createVerticalStrut(20));
        btnDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                delete();
            }
        });
        actionPanel.add(btnBack);
        actionPanel.add(Box.createVerticalStrut(20));
        btnBack.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                clearFields();
                menuPanel.setVisible(true);
                comboPanel.setVisible(false);
                authorPanel.setVisible(false);
                bookPanel.setVisible(false);
                actionPanel.setVisible(false);
                pack();
            }
        });
        actionPanel.setVisible(false);

        clearFields();
        box.setPreferredSize(new Dimension(300, 500));
        box.add(menuPanel);
        box.add(comboPanel);
        box.add(authorPanel);
        box.add(bookPanel);
        box.add(actionPanel);
        setContentPane(box);
        //pack();
    }

    private static void sizeAllElements() {
        Dimension dimension = new Dimension(300, 50);
        btnAddAuthor.setMaximumSize(dimension);
        btnAddBook.setMaximumSize(dimension);
        btnEdit.setMaximumSize(dimension);
        btnBack.setMaximumSize(dimension);
        btnSave.setMaximumSize(dimension);
        btnDelete.setMaximumSize(dimension);

        btnAddAuthor.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAddBook.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSave.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDelete.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension panelDimension = new Dimension(300, 300);
        menuPanel.setMaximumSize(panelDimension);
        comboPanel.setPreferredSize(panelDimension);
        actionPanel.setPreferredSize(panelDimension);
        bookPanel.setPreferredSize(panelDimension);
        authorPanel.setPreferredSize(panelDimension);

        comboAuthor.setPreferredSize(dimension);
        comboBook.setPreferredSize(dimension);

        textBookAuthorName.setPreferredSize(dimension);
        textBookName.setPreferredSize(dimension);
        textBookPages.setPreferredSize(dimension);
        textAuthorName.setPreferredSize(dimension);
    }

    private static void save() {
        if (editMode) {
            if (authorMode) {
                currentAuthor.setName(textAuthorName.getText());
                if (!AuthorDAO.update(currentAuthor)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            } else {
                currentBook.setName(textBookName.getText());
                currentBook.setPages(Long.parseLong(textBookPages.getText()));

                AuthorDTO author = AuthorDAO.findByName(textBookAuthorName.getText());
                if (author == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such author!");
                    return;
                }
                currentBook.setAuthorId(author.getId());

                if (!BookDAO.update(currentBook)) {
                    JOptionPane.showMessageDialog(null, "Error: update failed!");
                }
            }
        } else {
            if (authorMode) {
                AuthorDTO author = new AuthorDTO();
                author.setName(textAuthorName.getText());

                if (!AuthorDAO.insert(author)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }

                comboAuthor.addItem(author.getName());
            } else {
                BookDTO book = new BookDTO();
                book.setName(textBookName.getText());
                book.setPages(Long.parseLong(textBookPages.getText()));

                AuthorDTO author = AuthorDAO.findByName(textBookAuthorName.getText());
                if (author == null) {
                    JOptionPane.showMessageDialog(null, "Error: no such author!");
                    return;
                }
                book.setAuthorId(author.getId());

                if (!BookDAO.insert(book)) {
                    JOptionPane.showMessageDialog(null, "Error: insertion failed!");
                    return;
                }

                comboBook.addItem(book.getName());
            }
        }
    }

    private static void delete() {
        if (editMode) {
            if (authorMode) {
                var list = BookDAO.findByAuthorId(currentAuthor.getId());
                assert list != null;
                for (BookDTO book : list) {
                    comboBook.removeItem(book.getName());
                    BookDAO.delete(book);
                }
                AuthorDAO.delete(currentAuthor);
                comboAuthor.removeItem(currentAuthor.getName());

            } else {
                BookDAO.delete(currentBook);
                comboBook.removeItem(currentBook.getName());
            }
        }
    }

    private void fillComboBoxes() {
        comboAuthor.removeAllItems();
        comboBook.removeAllItems();
        var authors = AuthorDAO.findAll();
        var books = BookDAO.findAll();
        for (AuthorDTO author : authors) {
            comboAuthor.addItem(author.getName());
        }
        for (BookDTO book : books) {
            comboBook.addItem(book.getName());
        }
    }

    private static void clearFields() {
        textBookAuthorName.setText("");
        textBookPages.setText("");
        textBookName.setText("");
        textAuthorName.setText("");
        currentAuthor = null;
        currentBook = null;
    }

    private static void fillAuthorFields() {
        if (currentAuthor == null) {
            return;
        }
        textAuthorName.setText(currentAuthor.getName());
    }

    private static void fillBookFields() {
        if (currentBook == null) {
            return;
        }
        AuthorDTO author = AuthorDAO.findById(currentBook.getAuthorId());
        assert author != null;
        textBookName.setText(currentBook.getName());
        textBookPages.setText(String.valueOf(currentBook.getPages()));
        textBookAuthorName.setText(author.getName());
    }

    public static void main(String[] args) {
        JFrame myWindow = new Application();
        myWindow.setVisible(true);
    }
}