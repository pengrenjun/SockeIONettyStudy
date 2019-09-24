package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.util.Date;

/**
 * �豸��¼����
 */
public class LoginReq extends CommonMsg  {

    //���������
    private String rtuCode;

    //��¼ʱ��
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
