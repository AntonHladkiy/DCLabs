package Server;

import DTO.PhoneDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IServerRmiTask7 extends Remote {
    public ArrayList<PhoneDTO> findPhonesWhereTownCallsMoreThan(Integer value) throws RemoteException;
    public ArrayList<PhoneDTO> findPhonesWhereOutTownCallsPresent() throws RemoteException;
    public ArrayList<PhoneDTO> sortedAlphabetic() throws RemoteException;

}
