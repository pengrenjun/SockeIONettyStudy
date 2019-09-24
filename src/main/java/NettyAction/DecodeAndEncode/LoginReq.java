package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备登录请求
 */
public class LoginReq extends CommonMsg  {

    //集中器编号
    private String rtuCode;

    //登录时间
    private Date loginTime;

    public String getRtuCode() {
        return rtuCode;
    }

    public void setRtuCode(String rtuCode) {
        this.rtuCode = rtuCode;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "LoginReq{" +
                "rtuCode='" + rtuCode + '\'' +
                ", loginTime=" + loginTime +
                '}';
    }
}
