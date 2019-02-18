package vdsale.exceptions;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Data
public class DefaultError implements Serializable {

    private static final long serialVersionUID = 7907809609368673286L;

    private Integer status;
    private String error;
    private String message;
    private String path;

    public DefaultError() {
    }

    public DefaultError(Integer status, String error, String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
