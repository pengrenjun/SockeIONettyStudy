package NettyAction.DecodeAndEncode.JavaObjectCode;

import java.io.Serializable;

/**
 *设备与服务器端报文基类实现了Serializable 这样就可以通过java实现序列化了 定义了消息类型
 */
public class CommonMsg implements Serializable {

    //主端下行采集
    public static final String COLLECT_METERDATA_CMD_TYPE="A001";

    //设备上行应答
    public static final String COLLECT_METERDATA_ACK_TYPE="A002";

    //设备登录
    public static final String LOGIN_REQ_TYPE="B002";

    //登录响应
    public static final String LOGIN_RESP_TYPE="B001";

    /**
     * 消息的类型
     */
    private String msgType;

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "CommonMsg{" +
                "msgType='" + msgType + '\'' +
                '}';
    }
}
