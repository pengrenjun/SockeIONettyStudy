package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 服务端对设备段发送的采集水表读数的指令
 */
public class CollectMeterDataCmd extends CommonMsg {

    //集中器编号
    private String rtuCode;

    //采集指令发送的时间
    private Date collectTime;

    //报文内容
    private byte[] message;

    public String getRtuCode() {
        return rtuCode;
    }

    public void setRtuCode(String rtuCode) {
        this.rtuCode = rtuCode;
    }

    public Date getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "CollectMeterDataCmd{" +
                "rtuCode='" + rtuCode + '\'' +
                ", collectTime=" + collectTime +
                ", message=" + Arrays.toString(message) +
                '}';
    }
}
