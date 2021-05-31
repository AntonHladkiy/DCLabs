package Client;

import DTO.ParameterDTO;
import DTO.ParameterGroupDTO;
import DTO.ProductDTO;
import DTO.ProductGroupDTO;
import server.DAOtask7;
import server.ProductAllInfo;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientSocketTask7 {
    private final Socket socket;
    private final PrintWriter out;
    private final ObjectInputStream in;
    private static final String split = "#";

    ClientSocketTask7(String ip, int port) throws IOException {
        socket = new Socket( ip, port );
        in = new ObjectInputStream( socket.getInputStream( ) );
        out = new PrintWriter( socket.getOutputStream( ), true );
    }

    public Object sendQuery(String query) throws IOException, ClassNotFoundException {
        String[] fields = query.split( split );
        ParameterDTO parameter;
        ParameterGroupDTO parameterGroup;
        ProductDTO product;
        ProductGroupDTO productGroup;
        boolean bool;
        out.println( query );
        var action = fields[0];
        switch (action) {
            case "ParameterFindById":
                parameter = (ParameterDTO) in.readObject( );
                return parameter;
            case "ParameterInsert":
                bool = in.readBoolean( );
                return bool;
            case "ParameterUpdate":
                bool = in.readBoolean( );
                return bool;

            case "ParameterDelete":
                bool = in.readBoolean( );
                return bool;


            //Exactly same CRUD parsing for all DTO(but i wont do it since it takes a lot of time)


            //Now variant special calls
            case "ParametersForProductGroup":
                return (List<ParameterDTO>) in.readObject( );

            case "ProductsWithoutParameter":
                return (List<ProductDTO>) in.readObject( );

            case "ProductsForGroup":
                return (List<ProductDTO>) in.readObject( );

            case "ProductWithALlInfo":
                return (ProductAllInfo) in.readObject( );

            case "ChangeProductGroupForParameterGroup":
                return in.readBoolean( );

            case "DeleteProductsWithParameter":
                return in.readBoolean( );

        }
        return false;
    }


    public void disconnect( ) {
        try {
            socket.close( );
        } catch (IOException e) {
            e.printStackTrace( );
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientSocketTask7 client = new ClientSocketTask7( "localhost", 3002 );
        BufferedReader reader = new BufferedReader(
                new InputStreamReader( System.in ) );
        while (true) {
            String query = reader.readLine( );
            client.sendQuery( query );
        }
    }
}