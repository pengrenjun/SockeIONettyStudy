package NettyAction.DecodeAndEncode.jboosMarshelling;

import Netty.Serial.MarshallingCodeCFactory;
import NettyAction.DecodeAndEncode.JavaObjectCode.RtuMeterClientHandler;
import NettyAction.DecodeAndEncode.JavaObjectCode.RtuMeterServerHandler;
import NettyAction.TimeServerAndClient.democlient.NettyClient;
import NettyAction.TimeServerAndClient.demoserver.NettyServer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.junit.Test;

/**
 * ���Ի���jboss marsheling���л������ݵı������
 */
public class TestMarshellingCode {

    //���������
    @Test
    public void startMshellingMeterServer(){

      //����ʱ �ְ�CommonMsg implements Serializable��ע���� ����Ҫimplements Serializable
        //���ڲ��Ե�ʱ�� ʵ��bean��ʵ�����л��ӿ� �ǽ��ղ�����Ϣ��

        new NettyServer()
                .initServerAndBindPort(8005)
                .addChildHandlers(MarshallingCodeCFactory.buildMarshallingDecoder(),
                        MarshallingCodeCFactory.buildMarshallingEncoder(),new RtuMeterServerHandler())
                .start();

    }

    //�����豸
    @Test
    public void startMeterClient(){

        new NettyClient()
                .connect("localhost",8005)
                .addHandlers(MarshallingCodeCFactory.buildMarshallingDecoder(),MarshallingCodeCFactory.buildMarshallingEncoder(),new RtuMeterClientHandler())
                .start();
    }
}
