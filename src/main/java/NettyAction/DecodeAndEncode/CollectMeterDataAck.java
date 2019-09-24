package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.util.List;

/**
 * �豸�˶Է�����·��Ĳɼ��豸��Ϣ����Ӧ
 */
public class CollectMeterDataAck extends CommonMsg {

    //���������(ȡֵ��Դ�ڷ����)
    private String rtuCode;

    //���������������ˮ�����Ϣ
    private List<MeterData> meterDataList;

    public String getRtuCode() {
        return rtuCode;
    }

    public void setRtuCode(String rtuCode) {
        this.rtuCode = rtuCode;
    }

    public List<MeterData> getMeterDataList() {
        return meterDataList;
    }

    public void setMeterDataList(List<MeterData> meterDataList) {
        this.meterDataList = meterDataList;
    }

    @Override
    public String toString() {
        return "CollectMeterDataAck{" +
                "rtuCode='" + rtuCode + '\'' +
                ", meterDataList=" + meterDataList +
                '}';
    }
}
