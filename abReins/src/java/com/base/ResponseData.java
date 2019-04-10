package com.base;



import java.io.Serializable;
import java.util.LinkedHashMap;
/**
* <p>application name：abReins</p>
* <p>application describing：页面返回数据格式</p>
* <p>copyright： Copyright &copy; 2017 东软版权所有
  </p>
* <p>company： neusoft</p>
* <p>time： 2017年8月14日 - 下午2:26:59</p>
*
* @author yanghuibin@neusoft.com
* @version $Revision: 1.0 $
* Modification  History: 
* Date         Author        Version        Discription 
* ----------------------------------------------------------------------------------- 
* 2017年7月31日    yanghuibin@neusoft.com          1.0             1.0 
* Why & What is modified: <修改原因描述> 
*/  
public class ResponseData implements Serializable{
	private static final long serialVersionUID = 1L;
	private  String message;
    private  int code;
    private  LinkedHashMap data = new LinkedHashMap();

    public ResponseData()
    {
        super();
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }


    public LinkedHashMap getData()
    {
        return data;
    }

    public void setData(LinkedHashMap data)
    {
        this.data = data;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public ResponseData putDataValue(String key, Serializable value) {
        data.put(key, value);
        return this;
    }

    public ResponseData(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 正常状态的返回信息(代码200)
     * @return
     */
    public static ResponseData ok() {
        return new ResponseData(200, "Ok");
    }

    /**
     * 没有页面的返回信息(代码404)
     * @return
     */
    public static ResponseData notFound() {
        return new ResponseData(404, "Not Found");
    }

    /**
     * 错误请求的返回信息(代码400)
     * @return
     */
    public static ResponseData badRequest() {
        return new ResponseData(400, "Bad Request");
    }

    /**
     * 被禁止的返回信息(代码403)
     * @return
     */
    public static ResponseData forbidden() {
        return new ResponseData(403, "Forbidden");
    }

    /**
     * 未经许可的返回信息(代码401)
     * @return
     */
    public static ResponseData unauthorized() {
        return new ResponseData(401, "unauthorized");
    }

    /**
     * 内部错误(代码500)
     * @return
     */
    public static ResponseData serverInternalError() {
        return new ResponseData(500, "Server Internal Error");
    }

    /**
     * 自定义错误(代码1001)
     * @return
     */
    public static ResponseData customerError() {
        return new ResponseData(1001, "customer Error");
    }
    
    /**
     * 业务异常的返回信息(代码业务异常代码)
     * @param businessException
     * @return
     */
    public static ResponseData businessException(BusinessException businessException){
        return new ResponseData(businessException.getCode(), businessException.getMessage());
    }
}
