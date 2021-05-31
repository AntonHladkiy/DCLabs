package Client;

import DTO.ParameterDTO;
import DTO.ParameterGroupDTO;
import DTO.ProductDTO;
import DTO.ProductGroupDTO;
import server.IServerRmiTask7;
import server.ProductAllInfo;
import server.ServerRmiTask7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.List;

public class ClientRmiTask7 {
    private static final String split = "#";
    private static IServerRmiTask7 server=null;
    public static Object parseInput(String input) throws IOException, ClassNotFoundException {
        String[] fields = input.split( split );
        ParameterDTO parameter=new ParameterDTO(  );
        ParameterGroupDTO parameterGroup;
        ProductDTO product;
        ProductGroupDTO productGroup;
        boolean bool;
        var action = fields[0];
        switch (action) {
            case "ParameterFindById":
                parameter = server.findParameterById( Long.parseLong(fields[1]));
                //optional output
                return parameter;
            case "ParameterFindByName":
                parameter = server.findParameterByName( fields[1]);
                //optional output
                return parameter;
            case "ParameterInsert":
                var id = Long.parseLong(fields[1]);
                var name =(fields[2]);
                var measurementUnit =(fields[3]);
                parameter.setName( name );
                parameter.setId( id );
                parameter.setMeasurementUnit(measurementUnit  );
                bool = server.insertParameter( parameter );
                //optional output
                return bool;
            case "ParameterUpdate":
                id = Long.parseLong(fields[1]);
                name =(fields[2]);
                measurementUnit =(fields[3]);
                parameter.setName( name );
                parameter.setId( id );
                parameter.setMeasurementUnit(measurementUnit  );
                bool = server.updateParameter( parameter );
                return bool;

            case "ParameterDelete":
                id = Long.parseLong(fields[1]);
                name =(fields[2]);
                 measurementUnit =(fields[3]);
                parameter.setName( name );
                parameter.setId( id );
                parameter.setMeasurementUnit(measurementUnit  );
                bool = server.deleteParameter( parameter );
                return bool;


            //Exactly same CRUD parsing for all DTO(but i wont do it since it takes a lot of time)


            //Now variant special calls
            case "ParametersForProductGroup":
                id = Long.parseLong(fields[1]);
                return server.getParametersForProductGroup( id );

            case "ProductsWithoutParameter":
                id = Long.parseLong(fields[1]);
                return server.getProductsWithoutParameter( id );

            case "ProductsForGroup":
                id = Long.parseLong(fields[1]);
                return server.getProductsForGroup( id );

            case "ProductWithALlInfo":
                id = Long.parseLong(fields[1]);
                return server.getProductWithALlInfo( id );

            case "ChangeProductGroupForParameterGroup":
                var productGroupId = Long.parseLong(fields[1]);
                var newProductGroupId = Long.parseLong(fields[2]);
                var parameterGroupId = Long.parseLong(fields[3]);
                return server.changeProductGroupForParameterGroup(parameterGroupId,productGroupId ,newProductGroupId);

            case "DeleteProductsWithParameter":
                id = Long.parseLong(fields[1]);
                return server.deleteProductsWithParameter( id );

        }
        return false;
    }

    public static void main(String[] args) throws IOException, NotBoundException, ClassNotFoundException {
        String url = "//localhost:3004/product";
        server= (IServerRmiTask7) Naming.lookup( url );
        BufferedReader reader = new BufferedReader(
                new InputStreamReader( System.in ) );
        while (true) {
            String query = reader.readLine( );
            parseInput( query );
        }
    }
}
