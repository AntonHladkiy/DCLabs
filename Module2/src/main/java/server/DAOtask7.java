package server;

import DTO.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOtask7 {
    private static Connection connection = null;

    private final static String url = "jdbc:postgresql://localhost:5432/Product";
    private final static String user = "postgres";
    private final static String password = "password";

    public static Connection getConnection( ) {
        try {
            if (connection == null || connection.isClosed( )) {
                Class.forName( "org.postgresql.Driver" );
                connection = DriverManager.getConnection( url, user, password );
            }
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static void closeConnection( ) {
        try {
            connection.close( );
        } catch (SQLException e) {
            e.printStackTrace( );
        }
    }

    public static void main(String[] args) {
    }


    //ParameterDAO
    public static ParameterDTO findParameterById(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM parameters "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ParameterDTO parameter = null;
            if (resultSet.next( )) {
                parameter = new ParameterDTO( );
                parameter.setId( resultSet.getLong( 3 ) );
                parameter.setName( resultSet.getString( 1 ) );
                parameter.setMeasurementUnit( resultSet.getString( 2 ) );
            }
            preparedStatement.close( );
            return parameter;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static ParameterDTO findParameterByName(String name) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM parameters "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, name );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ParameterDTO parameter = null;
            if (resultSet.next( )) {
                parameter = new ParameterDTO( );
                parameter.setId( resultSet.getLong( 3 ) );
                parameter.setName( resultSet.getString( 1 ) );
                parameter.setMeasurementUnit( resultSet.getString( 2 ) );
            }
            preparedStatement.close( );
            return parameter;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static boolean updateParameter(ParameterDTO parameter) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "UPDATE parameters "
                            + "SET name = ?, measurement_unit = ?"
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, parameter.getName( ) );
            preparedStatement.setString( 2, parameter.getMeasurementUnit( ) );
            preparedStatement.setLong( 3, parameter.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean insertParameter(ParameterDTO parameter) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "INSERT INTO parameters (name,measurement_unit) "
                            + "VALUES (?,?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, parameter.getName( ) );
            preparedStatement.setString( 2, parameter.getMeasurementUnit( ) );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                parameter.setId( resultSet.getLong( 3 ) );
            } else
                return false;
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean deleteParameter(ParameterDTO parameter) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "DELETE FROM parameters "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, parameter.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }



    //ProductGroupDAO
    public static ProductGroupDTO findProductGroupById(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM product_groups "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ProductGroupDTO productGroup = null;
            if (resultSet.next( )) {
                productGroup = new ProductGroupDTO( );
                productGroup.setId( resultSet.getLong( 2 ) );
                productGroup.setName( resultSet.getString( 1 ) );
            }
            preparedStatement.close( );
            return productGroup;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static ProductGroupDTO findProductGroupByName(String name) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM product_groups "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, name );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ProductGroupDTO productGroup = null;
            if (resultSet.next( )) {
                productGroup = new ProductGroupDTO( );
                productGroup.setId( resultSet.getLong( 2 ) );
                productGroup.setName( resultSet.getString( 1 ) );
            }
            preparedStatement.close( );
            return productGroup;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static boolean updateProductGroup(ProductGroupDTO productGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "UPDATE product_groups "
                            + "SET name = ?"
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, productGroup.getName( ) );
            preparedStatement.setLong( 2, productGroup.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean insertProductGroup(ProductGroupDTO productGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "INSERT INTO product_groups (name) "
                            + "VALUES (?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, productGroup.getName( ) );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                productGroup.setId( resultSet.getLong( 2 ) );
            } else
                return false;
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean deleteProductGroup(ProductGroupDTO productGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "DELETE FROM product_groups "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, productGroup.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }




    //ProductDAO
    public static ProductDTO findProductById(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM products "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ProductDTO product = null;
            if (resultSet.next( )) {
                product = new ProductDTO( );
                product.setId( resultSet.getLong( 3 ) );
                product.setGroupId(  resultSet.getLong( 4 ));
                product.setName( resultSet.getString( 1 ) );
                product.setDescription( resultSet.getString( 2 ) );
            }
            preparedStatement.close( );
            return product;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static ProductDTO findProductByName(String name) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM products "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, name );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ProductDTO product = null;
            if (resultSet.next( )) {
                product = new ProductDTO( );
                product.setId( resultSet.getLong( 3 ) );
                product.setGroupId(  resultSet.getLong( 4 ));
                product.setName( resultSet.getString( 1 ) );
                product.setDescription( resultSet.getString( 2 ) );
            }
            preparedStatement.close( );
            return product;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static boolean updateProduct(ProductDTO product) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "UPDATE products "
                            + "SET group_id = ?,name = ?, description = ?"
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, product.getGroupId( ) );
            preparedStatement.setString( 2, product.getName( ) );
            preparedStatement.setString( 3, product.getDescription( ) );
            preparedStatement.setLong( 4, product.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean insertProduct(ProductDTO product) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "INSERT INTO products (group_id,name,description) "
                            + "VALUES (?,?,?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, product.getGroupId( ) );
            preparedStatement.setString( 2, product.getName( ) );
            preparedStatement.setString( 3, product.getDescription( ) );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                product.setId( resultSet.getLong( 3 ) );
            } else
                return false;
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean deleteProduct(ProductDTO product) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "DELETE FROM products "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, product.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }




    //ParameterGroupDAO
    public static ParameterGroupDTO findParameterGroupById(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM parameter_groups "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ParameterGroupDTO parameterGroup = null;
            if (resultSet.next( )) {
                parameterGroup = new ParameterGroupDTO( );
                parameterGroup.setId( resultSet.getLong( 2 ) );
                parameterGroup.setName( resultSet.getString( 1 ) );
            }
            preparedStatement.close( );
            return parameterGroup;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static ParameterGroupDTO findParameterGroupByName(String name) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM parameter_groups "
                            + "WHERE name = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, name );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ParameterGroupDTO parameterGroup = null;
            if (resultSet.next( )) {
                parameterGroup = new ParameterGroupDTO( );
                parameterGroup.setId( resultSet.getLong( 2 ) );
                parameterGroup.setName( resultSet.getString( 1 ) );
            }
            preparedStatement.close( );
            return parameterGroup;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static boolean updateParameterGroup(ParameterGroupDTO parameterGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "UPDATE parameter_groups "
                            + "SET name = ?"
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, parameterGroup.getName( ) );
            preparedStatement.setLong( 2, parameterGroup.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean insertParameterGroup(ParameterGroupDTO parameterGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "INSERT INTO parameter_groups (name)"
                            + "VALUES (?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setString( 1, parameterGroup.getName( ) );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                parameterGroup.setId( resultSet.getLong( 2 ) );
            } else
                return false;
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean deleteParameterGroup(ParameterGroupDTO parameterGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "DELETE FROM parameter_groups "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, parameterGroup.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }


    //ParameterGroupParameterDAO
    public static ParameterGroupParameterDTO findParameterGroupParameterById(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM parameter_group_parameters "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ParameterGroupParameterDTO parameterGroupParameter = null;
            if (resultSet.next( )) {
                parameterGroupParameter = new ParameterGroupParameterDTO( );
                parameterGroupParameter.setParameterGroupId( resultSet.getLong( 2 ) );
                parameterGroupParameter.setParameterId( resultSet.getLong( 3 ) );
            }
            preparedStatement.close( );
            return parameterGroupParameter;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static boolean updateParameterGroupParameter(ParameterGroupParameterDTO parameterGroupParameter) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "UPDATE parameter_group_parameters "
                            + "SET parameter_group_id = ?,parameter_id=?"
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, parameterGroupParameter.getParameterGroupId( ) );
            preparedStatement.setLong( 2, parameterGroupParameter.getParameterId( ) );
            preparedStatement.setLong( 3, parameterGroupParameter.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean insertParameterGroupParameter(ParameterGroupParameterDTO parameterGroupParameter) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "INSERT INTO parameter_group_parameters (parameter_group_id,parameter_id)"
                            + "VALUES (?,?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, parameterGroupParameter.getParameterGroupId( ) );
            preparedStatement.setLong( 2, parameterGroupParameter.getParameterId( ) );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                parameterGroupParameter.setId( resultSet.getLong( 1 ) );
            } else
                return false;
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean deleteParameterGroupParameter(ParameterGroupParameterDTO parameterGroupParameter) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "DELETE FROM parameter_group_parameters "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, parameterGroupParameter.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }


    //ProductParameterGroupDAO
    public static ProductParameterGroupDTO findProductParameterGroupById(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * "
                            + "FROM product_parameter_groups "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ProductParameterGroupDTO productParameterGroup = null;
            if (resultSet.next( )) {
                productParameterGroup = new ProductParameterGroupDTO( );
                productParameterGroup.setParameterGroupId( resultSet.getLong( 3 ) );
                productParameterGroup.setProductGroupId( resultSet.getLong( 2 ) );
            }
            preparedStatement.close( );
            return productParameterGroup;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static boolean updateProductParameterGroup(ProductParameterGroupDTO productParameterGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "UPDATE product_parameter_groups "
                            + "SET parameter_group_id = ?,product_group_id=?"
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, productParameterGroup.getParameterGroupId( ) );
            preparedStatement.setLong( 2, productParameterGroup.getProductGroupId( ) );
            preparedStatement.setLong( 3, productParameterGroup.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean insertProductParameterGroup(ProductParameterGroupDTO productParameterGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "INSERT INTO product_parameter_groups (parameter_group_id,product_group_id)"
                            + "VALUES (?,?) "
                            + "RETURNING id";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, productParameterGroup.getParameterGroupId( ) );
            preparedStatement.setLong( 2, productParameterGroup.getProductGroupId( ) );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                productParameterGroup.setId( resultSet.getLong( 1 ) );
            } else
                return false;
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }

    public static boolean deleteProductParameterGroup(ProductParameterGroupDTO productParameterGroup) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "DELETE FROM product_parameter_groups "
                            + "WHERE id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, productParameterGroup.getId( ) );
            var result = preparedStatement.executeUpdate( );
            preparedStatement.close( );
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }


    //Additional DAO

    public static List<ParameterDTO> getParametersForProductGroup(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT  FROM parameters " +
                            "INNER JOIN (parameter_group_parameters pgp " +
                            "INNER JOIN (parameter_groups pg " +
                            "INNER JOIN (product_parameter_groups ppg " +
                            "INNER JOIN product_groups on product_groups.id = ppg.product_group_id )" +
                            "on pgp.parameter_group_id = pg.id )" +
                            "on pgp.parameter_group_id=pg.id) on parameters.id = pgp.parameter_id WHERE product_group_id=?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            List<ParameterDTO> res=new ArrayList<>(  );
            ParameterDTO parameter = null;
            while (resultSet.next( )) {
                parameter = new ParameterDTO( );
                parameter.setId( resultSet.getLong( 3 ) );
                parameter.setMeasurementUnit( resultSet.getString( 2 ) );
                parameter.setName( resultSet.getString( 1 ) );
                res.add(parameter);
            }
            preparedStatement.close( );
            return res;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }


    public static List<ProductDTO> getProductsWithoutParameter(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM products INNER JOIN (product_groups " +
                            "INNER JOIN (product_parameter_groups " +
                            "INNER JOIN (parameter_groups " +
                            "INNER JOIN (parameter_group_parameters pgp I" +
                            "NNER JOIN parameters p on pgp.parameter_id = p.id)" +
                            " on parameter_groups.id = pgp.parameter_group_id )" +
                            "on product_parameter_groups.parameter_group_id=parameter_groups.id)" +
                            " on product_groups.id=product_parameter_groups.product_group_id) on  products.group_id=product_groups.id " +
                            "WHERE NOT parameter_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            List<ProductDTO> res=new ArrayList<>(  );
            ProductDTO product = null;
            while (resultSet.next( )) {
                product = new ProductDTO( );
                product.setId( resultSet.getLong( 3 ) );
                product.setGroupId( resultSet.getLong( 4 ) );
                product.setName( resultSet.getString( 1 ) );
                product.setDescription( resultSet.getString( 2 ) );
                res.add(product);
            }
            preparedStatement.close( );
            return res;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }


    public static List<ProductDTO> getProductsForGroup(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM products WHERE group_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            List<ProductDTO> res=new ArrayList<>(  );
            ProductDTO product = null;
            while (resultSet.next( )) {
                product = new ProductDTO( );
                product.setId( resultSet.getLong( 3 ) );
                product.setGroupId( resultSet.getLong( 4 ) );
                product.setName( resultSet.getString( 1 ) );
                product.setDescription( resultSet.getString( 2 ) );
                res.add(product);
            }
            preparedStatement.close( );
            return res;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }


    public static ProductAllInfo getProductWithALlInfo(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM products pr INNER JOIN product_groups on  pr.group_id=product_groups.id " +
                            "WHERE pr.id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            ProductAllInfo res=new ProductAllInfo( );
            ProductDTO product = null;
            ProductGroupDTO productGroup = null;
            if (resultSet.next( )) {
                product = new ProductDTO( );
                productGroup=new ProductGroupDTO();
                product.setId( resultSet.getLong( 3 ) );
                product.setGroupId( resultSet.getLong( 4 ) );
                product.setName( resultSet.getString( 1 ) );
                product.setDescription( resultSet.getString( 2 ) );
                productGroup.setId( product.getGroupId() );
                productGroup.setName( resultSet.getString( 5 )  );
                res.setParameterGroups(new ArrayList<>(  ) );
                res.setProduct( product );
                res.setProductGroup( productGroup );
                List<ParameterGroupDTO> paramGroups=getParameterGroupsForProductGroup( productGroup.getId() );
                for (ParameterGroupDTO paramGroup:paramGroups){
                    ParameterGroupAllInfo paramGroupInfo=new ParameterGroupAllInfo();
                    paramGroupInfo.setParameterGroup( paramGroup );
                    paramGroupInfo.setParameters( getParameterForParameterGroup(paramGroup.getId()) );
                    res.getParameterGroups().add(paramGroupInfo);
                }
            }
            preparedStatement.close( );
            return res;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }

    public static List<ParameterGroupDTO> getParameterGroupsForProductGroup(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM parameter_groups pr " +
                            "INNER JOIN product_parameter_groups ppg  " +
                            "on pr.id = ppg.parameter_group_id " +
                            "WHERE ppg.product_group_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            List<ParameterGroupDTO> res=new ArrayList<>( );
            ParameterGroupDTO parameterGroup = null;
            while (resultSet.next( )) {
                parameterGroup = new ParameterGroupDTO( );
                parameterGroup.setId( resultSet.getLong( 2 ) );
                parameterGroup.setName( resultSet.getString( 1 ) );
                res.add( parameterGroup );
            }
            preparedStatement.close( );
            return res;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }


    public static List<ParameterDTO> getParameterForParameterGroup(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM parameters INNER JOIN parameter_group_parameters pgp on parameters.id = pgp.parameter_id WHERE pgp.parameter_group_id=? ";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            List<ParameterDTO> res=new ArrayList<>( );
            ParameterDTO parameter = null;
            while (resultSet.next( )) {
                parameter = new ParameterDTO( );
                parameter.setId( resultSet.getLong( 3 ) );
                parameter.setName( resultSet.getString( 1 ) );
                parameter.setMeasurementUnit( resultSet.getString( 2 ) );
                res.add( parameter );
            }
            preparedStatement.close( );
            return res;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return null;
    }


    public static boolean deleteProductsWithParameter(long id) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM products INNER JOIN (product_groups " +
                    "INNER JOIN (product_parameter_groups " +
                    "INNER JOIN (parameter_groups " +
                    "INNER JOIN (parameter_group_parameters pgp I" +
                    "NNER JOIN parameters p on pgp.parameter_id = p.id)" +
                    " on parameter_groups.id = pgp.parameter_group_id )" +
                    "on product_parameter_groups.parameter_group_id=parameter_groups.id)" +
                    " on product_groups.id=product_parameter_groups.product_group_id) on  products.group_id=product_groups.id " +
                    "WHERE parameter_id = ?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, id );
            ResultSet resultSet = preparedStatement.executeQuery( );
            while (resultSet.next( )) {
                ProductDTO pr=new ProductDTO(  );
                pr.setId(  ( resultSet.getLong( 3 ) ));
                deleteProduct(pr);
            }
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;
    }


    public static boolean changeProductGroupForParameterGroup(long parameterGroupId,long oldProductGroupId,long newProductGroupId) {
        try (Connection connection = getConnection( )) {
            String sql =
                    "SELECT * FROM product_parameter_groups WHERE product_group_id = ? AND parameter_group_id=?";
            assert connection != null;
            PreparedStatement preparedStatement = connection.prepareStatement( sql );
            preparedStatement.setLong( 1, oldProductGroupId );
            preparedStatement.setLong( 2, parameterGroupId );
            ResultSet resultSet = preparedStatement.executeQuery( );
            if (resultSet.next( )) {
                ProductParameterGroupDTO ppg=new ProductParameterGroupDTO(  );
                ppg.setId(  ( resultSet.getLong( 1 ) ));
                ppg.setProductGroupId( newProductGroupId );
                ppg.setParameterGroupId(parameterGroupId  );
                updateProductParameterGroup( ppg );
            }
            preparedStatement.close( );
            return true;
        } catch (SQLException e) {
            e.printStackTrace( );
        }
        return false;

    }
}
