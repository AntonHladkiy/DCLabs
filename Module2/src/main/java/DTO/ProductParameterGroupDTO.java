package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductParameterGroupDTO  implements Serializable {
    private long id;
    private long productGroupId;
    private long parameterGroupId;
}
