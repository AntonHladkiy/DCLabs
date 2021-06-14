package Client;

import DTO.PhoneDTO;
import Server.IServerRmiTask7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class ClientRmiTask7 {
    private static final String split = "#";
    private static IServerRmiTask7 server=null;
    public static Object parseInput(String input) throws IOException, ClassNotFoundException {
        String[] fields = input.split( split );
        var action = fields[0];
        switch (action) {
            case "findPhonesWhereTownCallsMoreThan":
                return server.findPhonesWhereTownCallsMoreThan(Integer.parseInt(fields[1]));
            case "findPhonesWhereOutTownCallsPresent":
                return server.findPhonesWhereOutTownCallsPresent();
            case "sortedAlphabetic":
                return server.sortedAlphabetic();
        }
        return false;
    }

    public static void main(String[] args) throws IOException, NotBoundException, ClassNotFoundException {
        String url = "//localhost:3004/phone";
        server= (IServerRmiTask7) Naming.lookup( url );
        BufferedReader reader = new BufferedReader(
                new InputStreamReader( System.in ) );
        while (true) {
            String query = reader.readLine( );
            parseInput( query );
        }
    }
}
