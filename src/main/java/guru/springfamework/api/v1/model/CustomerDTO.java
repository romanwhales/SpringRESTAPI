package guru.springfamework.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class CustomerDTO {
    private Long id;
    @ApiModelProperty(value="This is the first name",required = true)
    private String firstname;
    @ApiModelProperty(value="Last Name", required = true)
    private String lastname;
}
