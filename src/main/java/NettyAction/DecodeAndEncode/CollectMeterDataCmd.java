package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * ����˶��豸�η��͵Ĳɼ�ˮ�������ָ��
 */
public class CollectMeterDataCmd extends CommonMsg {

    //���������
    private String rtuCode;

    //�ɼ�ָ��͵�ʱ��
    private Date collectTime;

    //��������
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
