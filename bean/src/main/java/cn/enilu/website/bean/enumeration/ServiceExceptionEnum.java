package cn.enilu.website.bean.enumeration;

/**
 * Created  on  2018/7/11 0011
 * ServiceExceptionEnum
 *
 * @author enilu
 */
public interface ServiceExceptionEnum {

    /**
     * 获取异常编码
     * @return
     */
    Integer getCode();

    /**
     * 获取异常信息
     * @return
     */
    String getMessage();
}
