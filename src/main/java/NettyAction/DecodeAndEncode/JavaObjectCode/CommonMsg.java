package NettyAction.DecodeAndEncode.JavaObjectCode;

import java.io.Serializable;

/**
 *�豸��������˱��Ļ���ʵ����Serializable �����Ϳ���ͨ��javaʵ�����л��� ��������Ϣ����
 */
public class CommonMsg implements Serializable {

    //�������вɼ�
    public static final String COLLECT_METERDATA_CMD_TYPE="A001";

    //�豸����Ӧ��
    public static final String COLLECT_METERDATA_ACK_TYPE="A002";

    //�豸��¼
    public static final String LOGIN_REQ_TYPE="B002";

    //��¼��Ӧ
    public static final String LOGIN_RESP_TYPE="B001";

    /**
     * ��Ϣ������
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
