package server;

import DTO.ParameterDTO;
import DTO.ParameterGroupDTO;
import DTO.ProductDTO;
import DTO.ProductGroupDTO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerSocketTask7 {
    private ServerSocket server = null;
    private Socket socket = null;
    private PrintWriter output = null;
    private BufferedReader in = null;
    private static final String split = "#";

    public void start(int port) throws IOException {
        server = new ServerSocket(port);
        while (true) {
            socket = server.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String query = in.readLine();
            output=new PrintWriter(socket.getOutputStream(), true);
            try (ObjectOutputStream out = new ObjectOutputStream( socket.getOutputStream() )) {
                new Thread( ( ) -> {
                    processQuery( query, out );
                } ).start( );
            }
        }
    }

    private boolean processQuery(String query, ObjectOutputStream out) {
        try {
            if (query == null) {
                return false;
            }

            String[] fields = query.split(split);
            if (fields.length == 0) {
                return true;
            } else {
                var action = fields[0];
                ParameterDTO parameter=new ParameterDTO(  );
                ParameterGroupDTO parameterGroup;
                ProductDTO product;
                ProductGroupDTO productGroup;

                switch (action) {
                    case "ParameterFindById":
                        var id = Long.parseLong(fields[1]);
                        parameter = DAOtask7.findParameterById(id);
                        out.writeObject(parameter);
                        break;
                    case "ParameterInsert":
                        id = Long.parseLong(fields[1]);
                        var name =(fields[2]);
                        var measurementUnit =(fields[3]);
                        parameter.setName( name );
                        parameter.setId( id );
                        parameter.setMeasurementUnit(measurementUnit  );
                        out.writeBoolean( DAOtask7.insertParameter( parameter ) );
                        break;
                    case "ParameterUpdate":
                        id = Long.parseLong(fields[1]);
                        name =(fields[2]);
                        measurementUnit =(fields[3]);
                        parameter.setName( name );
                        parameter.setId( id );
                        parameter.setMeasurementUnit(measurementUnit  );
                        out.writeBoolean( DAOtask7.updateParameter( parameter ) );
                        break;
                    case "ParameterDelete":
                        id = Long.parseLong(fields[1]);
                        parameter.setId( id );
                        out.writeBoolean( DAOtask7.deleteParameter( parameter ) );
                        break;

                    //Exactly same CRUD parsing for all DTO(but i wont do it since it takes a lot of time)




                    //Now variant special calls
                    case "ParametersForProductGroup":
                        id = Long.parseLong(fields[1]);
                        List<ParameterDTO> parametersForProductGroup=DAOtask7.getParametersForProductGroup( id );
                        out.writeObject(parametersForProductGroup);
                        break;
                    case "ProductsWithoutParameter":
                        id = Long.parseLong(fields[1]);
                        List<ProductDTO> productsWithoutParameter=DAOtask7.getProductsWithoutParameter( id );
                        out.writeObject(productsWithoutParameter);
                        break;
                    case "ProductsForGroup":
                        id = Long.parseLong(fields[1]);
                        List<ProductDTO> productsForGroup=DAOtask7.getProductsForGroup( id );
                        out.writeObject(productsForGroup);
                        break;
                    case "ProductWithALlInfo":
                        var productId = Long.parseLong(fields[1]);
                        ProductAllInfo productAllInfo=DAOtask7.getProductWithALlInfo( productId );
                        out.writeObject(productAllInfo);
                        break;
                    case "ChangeProductGroupForParameterGroup":
                        var productGroupId = Long.parseLong(fields[1]);
                        var newProductGroupId = Long.parseLong(fields[2]);
                        var parameterGroupId = Long.parseLong(fields[3]);
                        out.writeBoolean(DAOtask7.changeProductGroupForParameterGroup( parameterGroupId,productGroupId,newProductGroupId ));
                        break;
                    case "DeleteProductsWithParameter":
                        id = Long.parseLong(fields[1]);
                        out.writeBoolean(DAOtask7.deleteProductsWithParameter( id ));
                        break;
                }
            }

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            ServerSocketTask7 server = new ServerSocketTask7();
            server.start(3001);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}