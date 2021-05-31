package server;

import DTO.ProductDTO;
import DTO.ProductGroupDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAllInfo implements Serializable {
    ProductDTO product;
    ProductGroupDTO productGroup;
    List<ParameterGroupAllInfo> parameterGroups;
}
