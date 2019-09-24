package NettyAction.DecodeAndEncode;

import java.io.Serializable;
import java.util.List;

/**
 * 设备端对服务端下发的采集设备信息的响应
 */
public class CollectMeterDataAck extends CommonMsg {

    //集中器编号(取值来源于服务端)
    private String rtuCode;

    //集中器下面的所有水表的信息
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
