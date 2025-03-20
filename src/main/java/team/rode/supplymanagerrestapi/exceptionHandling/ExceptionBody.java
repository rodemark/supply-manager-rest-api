package team.rode.supplymanagerrestapi.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class ExceptionBody {
    private Integer status;
    private String message;
}
