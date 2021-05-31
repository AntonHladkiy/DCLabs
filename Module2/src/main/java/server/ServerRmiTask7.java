package server;

import DTO.ParameterDTO;
import DTO.ProductDTO;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ServerRmiTask7 extends UnicastRemoteObject implements IServerRmiTask7 {
    protected ServerRmiTask7( ) throws RemoteException {
        super( );
    }



    public static void main(String[] args) throws RemoteException {
        var server = new ServerRmiTask7( );
        Registry r = LocateRegistry.createRegistry( 3004 );
        r.rebind( "product", server );
    }

    @Override
    public ParameterDTO findParameterById(Long id) throws RemoteException {
        return DAOtask7.findParameterById( id );
    }

    @Override
    public ParameterDTO findParameterByName(String name) throws RemoteException {
        return DAOtask7.findParameterByName( name );
    }

    @Override
    public boolean insertParameter(ParameterDTO parameter) throws RemoteException {
        return DAOtask7.insertParameter( parameter );
    }

    @Override
    public boolean updateParameter(ParameterDTO parameter) throws RemoteException {
        return DAOtask7.updateParameter( parameter);
    }

    @Override
    public boolean deleteParameter(ParameterDTO parameter) throws RemoteException {
        return DAOtask7.deleteParameter( parameter );
    }

    @Override
    public List<ParameterDTO> getParametersForProductGroup(Long id) throws RemoteException {
        return DAOtask7.getParametersForProductGroup( id );
    }

    @Override
    public List<ProductDTO> getProductsWithoutParameter(Long id) throws RemoteException {
        return DAOtask7.getProductsWithoutParameter( id );
    }

    @Override
    public List<ProductDTO> getProductsForGroup(Long id) throws RemoteException {
        return DAOtask7.getProductsForGroup( id );
    }

    @Override
    public ProductAllInfo getProductWithALlInfo(Long id) throws RemoteException {
        return DAOtask7.getProductWithALlInfo( id );
    }

    @Override
    public boolean deleteProductsWithParameter(Long id) throws RemoteException {
        return DAOtask7.deleteProductsWithParameter(id );
    }

    @Override
    public boolean changeProductGroupForParameterGroup(long parameterGroupId, long oldProductGroupId, long newProductGroupId) throws RemoteException {
        return DAOtask7.changeProductGroupForParameterGroup( parameterGroupId,oldProductGroupId,newProductGroupId );
    }
}
