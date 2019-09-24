package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.sql.Date;

/**
 * �豸�˵�ˮ�����
 */
public class MeterData implements Serializable {

    //ˮ����
    private Integer meterCode;

    //ˮ�����
    private Double meterReading;

    //�ϱ���ʱ��
    private Date timestamp;

    public Double getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(Double meterReading) {
        this.meterReading = meterReading;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "MeterData{" +
                "meterReading=" + meterReading +
                ", timestamp=" + timestamp +
                '}';
    }
}
