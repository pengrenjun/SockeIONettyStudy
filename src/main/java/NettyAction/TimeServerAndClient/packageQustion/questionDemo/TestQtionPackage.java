package NettyAction.TimeServerAndClient.packageQustion.questionDemo;

import NettyAction.TimeServerAndClient.democlient.TimeClient;
import NettyAction.TimeServerAndClient.demoserver.TimeServer;
import org.junit.Test;

/**
 * @Description ���԰���ĳ��ֵ�����
 * @Date 2019/9/18 0018 ���� 5:55
 * @Created by Pengrenjun
 */

public class TestQtionPackage {

    @Test
    public void StartTimeServer(){

        new TimeServer()
                .initServerAndBindPort(8002)
                .addChildHandlers(new TimeServerQtionHandler())
                .start();
    }

    @Test
    public void StartTimeClient(){

        /**�ͻ���һ��һ������ ���ڷ���˽��յ��Ƕ������ �ַ���->16���Ƶ�����
         *          +-------------------------------------------------+
         *          |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
         * +--------+-------------------------------------------------+----------------+
         * |00000000| 71 63 74 0d 0a 71 63 74 0d 0a 71 63 74 0d 0a 71 |qct..qct..qct..q|
         * |00000010| 63 74 0d 0a 71 63 74 0d 0a 71 63 74 0d 0a       |ct..qct..qct..  |
         * +--------+-------------------------------------------------+----------------+
         */

        new TimeClient()
                .connect("localhost",8002)
                .addHandlers(new TimeClientQtionHandler())
                .start();
    }
}
