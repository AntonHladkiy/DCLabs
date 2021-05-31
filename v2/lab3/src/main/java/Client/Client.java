package Client;

import DTO.AuthorDTO;
import DTO.BookDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private static final String split = "#";

    Client(String ip, int port) throws IOException {
        socket = new Socket( ip, port );
        in = new BufferedReader(
                new InputStreamReader( socket.getInputStream( ) ) );
        out = new PrintWriter( socket.getOutputStream( ), true );
    }

    public AuthorDTO authorFindById(Long id) {
        var query = "AuthorFindById" + split + id.toString( );
        out.println( query );
        String response;
        try {
            response = in.readLine( );
            return new AuthorDTO( id, response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public BookDTO bookFindByName(String name) {
        var query = "BookFindByName" + split + name;
        out.println( query );
        String response = "";
        try {
            response = in.readLine( );
            String[] fields = response.split( split );
            var id = Long.parseLong( fields[0] );
            var authorId = Long.parseLong( fields[1] );
            var pages = Long.parseLong( fields[3] );
            return new BookDTO( id, authorId, name, pages );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public AuthorDTO authorFindByName(String name) {
        var query = "AuthorFindByName" + split + name;
        out.println( query );
        try {
            var response = Long.parseLong( in.readLine( ) );
            return new AuthorDTO( response, name );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public boolean bookUpdate(BookDTO book) {
        var query = "BookUpdate" + split + book.getId( ) +
                split + book.getAuthorId( ) + split + book.getName( )
                + split + book.getPages( );
        out.println( query );
        try {
            var response = in.readLine( );
            return "true".equals( response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public boolean authorUpdate(AuthorDTO author) {
        var query = "AuthorUpdate" + split + author.getId( ) +
                split + author.getName( );
        out.println( query );
        try {
            var response = in.readLine( );
            return "true".equals( response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public boolean bookInsert(BookDTO book) {
        var query = "BookInsert" +
                split + book.getAuthorId( ) + split + book.getName( )
                + split + book.getPages( );
        out.println( query );
        try {
            var response = in.readLine( );
            return "true".equals( response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public boolean authorInsert(AuthorDTO country) {
        var query = "AuthorInsert" +
                split + country.getName( );
        out.println( query );
        try {
            var response = in.readLine( );
            return "true".equals( response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public boolean authorDelete(AuthorDTO country) {
        var query = "AuthorDelete" + split + country.getId( );
        out.println( query );
        try {
            var response = in.readLine( );
            return "true".equals( response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public boolean bookDelete(BookDTO book) {
        var query = "BookDelete" + split + book.getId( );
        out.println( query );
        try {
            var response = in.readLine( );
            return "true".equals( response );
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public List<AuthorDTO> authorAll( ) {
        var query = "AuthorAll";
        out.println( query );
        var list = new ArrayList<AuthorDTO>( );
        try {
            var response = in.readLine( );
            String[] fields = response.split( split );
            for ( int i = 0; i < fields.length; i += 2 ) {
                var id = Long.parseLong( fields[i] );
                var name = fields[i + 1];
                list.add( new AuthorDTO( id, name ) );
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public List<BookDTO> bookAll( ) {
        var query = "BookAll";
        return getBookDTOS( query );
    }

    public List<BookDTO> bookFindByAuthorId(Long authorId) {
        var query = "BookFindByAuthorId" + split + authorId.toString( );
        return getBookDTOS( query );
    }

    private List<BookDTO> getBookDTOS(String query) {
        out.println( query );
        var list = new ArrayList<BookDTO>( );
        try {
            var response = in.readLine( );
            String[] fields = response.split( split );
            for ( int i = 0; i < fields.length; i += 4 ) {
                var id = Long.parseLong( fields[i] );
                var authorId = Long.parseLong( fields[i + 1] );
                var name = fields[i + 2];
                var pages = Long.parseLong( fields[i + 3] );
                list.add( new BookDTO( id, authorId, name, pages ) );
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public void disconnect( ) {
        try {
            socket.close( );
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }
}
