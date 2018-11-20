package cn.enilu.website.bean.enumeration;

/**
 * Created  on  2018/7/11 0011
 * WebException
 *
 * @author enilu
 */
public class WebException extends RuntimeException {

    private Integer code;

    private String message;

    public WebException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
