package NettyAction.DecodeAndEncode.JavaObjectCode;

/**
 * ���˵�¼��Ӧ
 */
public class LoginResp extends CommonMsg {


    //��¼��Ӧ
    private String resultCode;

    //��¼��Ӧ
    private String resultMessage;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    @Override
    public String toString() {
        return "LoginResp{" +
                "resultCode='" + resultCode + '\'' +
                ", resultMessage='" + resultMessage + '\'' +
                '}';
    }
}
