package NettyAction.DecodeAndEncode.JavaObjectCode;

import java.io.Serializable;
import java.sql.Date;

/**
 * 设备端的水表读数
 */
public class MeterData implements Serializable {

    //水表编号
    private String meterCode;

    //水表读数
    private Double meterReading;

    //上报的时间
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

    public String getMeterCode() {
        return meterCode;
    }

    public void setMeterCode(String meterCode) {
        this.meterCode = meterCode;
    }

    @Override
    public String toString() {
        return "MeterData{" +
                "meterCode=" + meterCode +
                ", meterReading=" + meterReading +
                ", timestamp=" + timestamp +
                '}';
    }
}
