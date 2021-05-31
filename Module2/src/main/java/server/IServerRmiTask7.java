package server;

import DTO.ParameterDTO;
import DTO.ProductDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServerRmiTask7 extends Remote {
    public ParameterDTO findParameterById(Long id) throws RemoteException;
    public ParameterDTO findParameterByName(String name) throws RemoteException;
    public boolean insertParameter(ParameterDTO parameter) throws RemoteException;
    public boolean updateParameter(ParameterDTO parameter) throws RemoteException;
    public boolean deleteParameter(ParameterDTO parameter) throws RemoteException;


    //Exactly same CRUD functions for all DTO(but i wont do it since it takes a lot of time and they are just copy paste)


    //Now variant special calls
    public List<ParameterDTO> getParametersForProductGroup(Long id) throws RemoteException;
    public  List<ProductDTO> getProductsWithoutParameter(Long id) throws RemoteException;
    public List<ProductDTO> getProductsForGroup(Long id) throws RemoteException;
    public ProductAllInfo getProductWithALlInfo(Long id) throws RemoteException;
    public boolean deleteProductsWithParameter(Long id) throws RemoteException;
    public boolean changeProductGroupForParameterGroup(long parameterGroupId,long oldProductGroupId,long newProductGroupId) throws RemoteException;

}
