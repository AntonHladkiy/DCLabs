package Server;

import DTO.BookDTO;
import DTO.AuthorDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    private ServerSocket server = null;
    private Socket socket = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private static final String split = "#";

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    private boolean processQuery() {
        String response;
        try {
            String query = in.readLine();
            if (query == null) {
                return false;
            }

            String[] fields = query.split(split);
            if (fields.length == 0) {
                return true;
            } else {
                var action = fields[0];
                AuthorDTO author;
                BookDTO book;

                switch (action) {
                    case "AuthorFindById":
                        var id = Long.parseLong(fields[1]);
                        author = AuthorDAO.findById(id);
                        response = author.getName();
                        out.println(response);
                        break;
                    case "BookFindByAuthorId":
                        id = Long.parseLong(fields[1]);
                        var list = BookDAO.findByAuthorId(id);
                        var str = new StringBuilder();
                        booksToStr(str, list);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "BookFindByName":
                        var name = fields[1];
                        book = BookDAO.findByName(name);
                        assert book != null;
                        response = book.getId() + split + book.getAuthorId() + split + book.getName() + split + book.getPages();
                        out.println(response);
                        break;
                    case "AuthorFindByName":
                        name = fields[1];
                        author = AuthorDAO.findByName(name);
                        assert author != null;
                        response = author.getId() + "";
                        out.println(response);
                        break;
                    case "BookUpdate":
                        id = Long.parseLong(fields[1]);
                        var authorId = Long.parseLong(fields[2]);
                        name = fields[3];
                        var pages = Long.parseLong(fields[4]);
                        book = new BookDTO(id, authorId, name, pages);
                        if (BookDAO.update(book))
                            response = "true";
                        else
                            response = "false";
                        System.out.println(response);
                        out.println(response);
                        break;
                    case "AuthorUpdate":
                        id = Long.parseLong(fields[1]);
                        name = fields[2];
                        author = new AuthorDTO(id, name);
                        if (AuthorDAO.update(author)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "BookInsert":
                        authorId = Long.parseLong(fields[1]);
                        name = fields[2];
                        pages = Long.parseLong(fields[3]);
                        book = new BookDTO(0, authorId, name, pages);
                        if (BookDAO.insert(book)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "AuthorInsert":
                        name = fields[1];
                        author = new AuthorDTO();
                        author.setName(name);

                        System.out.println(name);

                        if (AuthorDAO.insert(author)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "BookDelete":
                        id = Long.parseLong(fields[1]);
                        book = new BookDTO();
                        book.setId(id);
                        if (BookDAO.delete(book)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "AuthorDelete":
                        id = Long.parseLong(fields[1]);
                        author = new AuthorDTO();
                        author.setId(id);
                        if (AuthorDAO.delete(author)) {
                            response = "true";
                        } else {
                            response = "false";
                        }
                        out.println(response);
                        break;
                    case "BookAll":
                        var booksList = BookDAO.findAll();
                        str = new StringBuilder();
                        assert booksList != null;
                        booksToStr(str, booksList);
                        response = str.toString();
                        out.println(response);
                        break;
                    case "AuthorAll":
                        var authorsList = AuthorDAO.findAll();
                        str = new StringBuilder();
                        for (AuthorDTO currAuthor : authorsList) {
                            str.append(currAuthor.getId());
                            str.append(split);
                            str.append(currAuthor.getName());
                            str.append(split);
                        }
                        response = str.toString();
                        out.println(response);
                        break;
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private void booksToStr(StringBuilder str, List<BookDTO> booksList) {
        for (BookDTO currBook : booksList) {
            str.append(currBook.getId());
            str.append(split);
            str.append(currBook.getAuthorId());
            str.append(split);
            str.append(currBook.getName());
            str.append(split);
            str.append(currBook.getPages());
            str.append(split);
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
            server.start(3000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}