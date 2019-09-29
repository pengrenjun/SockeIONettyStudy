package NettyAction.DecodeAndEncode.JavaObjectCode;

/**
 * Ö÷¶ËµÇÂ¼ÏìÓ¦
 */
public class LoginResp extends CommonMsg {


    //µÇÂ¼ÏìÓ¦
    private String resultCode;

    //µÇÂ¼ÏìÓ¦
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
