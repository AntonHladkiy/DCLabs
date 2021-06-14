package Server;

import DTO.PhoneDTO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ServerRmiTask7 extends UnicastRemoteObject implements IServerRmiTask7 {
    protected ServerRmiTask7( ) throws RemoteException {
        super( );
    }



    public static void main(String[] args) throws RemoteException {
        var server = new ServerRmiTask7( );
        Registry r = LocateRegistry.createRegistry( 3004 );
        r.rebind( "phone", server );
    }

    @Override
    public ArrayList<PhoneDTO> findPhonesWhereTownCallsMoreThan(Integer value) throws RemoteException {
        ArrayList<PhoneDTO> res=new ArrayList<>(  );
        for ( PhoneDTO phone:
               Data.phones) {
            if(phone.getTownCalls()>value){
                res.add(phone);
            }
        }
        return res;
    }

    @Override
    public ArrayList<PhoneDTO> findPhonesWhereOutTownCallsPresent() throws RemoteException {
        ArrayList<PhoneDTO> res=new ArrayList<>(  );
        for ( PhoneDTO phone:
                Data.phones) {
            if(phone.getOutOfTownCalls()>0){
                res.add(phone);
            }
        }
        return res;
    }

    @Override
    public ArrayList<PhoneDTO> sortedAlphabetic() throws RemoteException {
        ArrayList<PhoneDTO> res=Data.phones;
        res.sort( Comparator.comparing( PhoneDTO::getFirstName ) );
        res.sort( Comparator.comparing( PhoneDTO::getLastname ) );
        res.sort( Comparator.comparing( PhoneDTO::getSecondName ) );
        return res;
    }

}
