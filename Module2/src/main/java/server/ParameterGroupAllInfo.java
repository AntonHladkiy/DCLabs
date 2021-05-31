package server;

import DTO.ParameterDTO;
import DTO.ParameterGroupDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParameterGroupAllInfo  implements Serializable {
    ParameterGroupDTO parameterGroup;
    List<ParameterDTO> parameters;
}
