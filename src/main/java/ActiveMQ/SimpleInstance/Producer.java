package ActiveMQ.SimpleInstance;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;


import java.sql.Date;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

/**
 * @Description: ActiveMq��ʵ��:������
 * @Author��pengrj
 * @Date : 2018/11/10 0010 19:20
 * @version:1.0
 */
public class Producer {

    //��ActiveMq��Ϣ�м��ƽ̨��������
    public  void startProduce() throws JMSException {

       //1.����ConnectionFactory��������
        /*<transportConnectors>
            <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
            <transportConnector name="openwire" uri="tcp://0.0.0.0:61616?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
            <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&amp;wireFormat.maxFrameSize=104857600"/>
        </transportConnectors>
*/
        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616");

        //2.ͨ��ConnectionFactory����һ������ ָ����֤���û���������,������������
        Connection connection=connectionFactory.createConnection(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connection.start();

        //3.ͨ������������connection����session�Ự(���ӵ������Ļ���),���ڽ�����Ϣ,����1���Ƿ��������� ����2���趨ǩ��ģʽ һ����Ϊ�Զ�ǩ��
                /*AUTO_ACKNOWLEDGE
                CLIENT_ACKNOWLEDGE
                DUPS_OK_ACKNOWLEDGE*/

        Session session=connection.createSession(true,AUTO_ACKNOWLEDGE);

        //4.ͨ��Session���� Destination ��������ָ��������ϢĿ���������ԴĿ��
        //  �ڵ�Ե�PTP��  Destination��ΪQueue ������  ��Pub/Sub�� ����Ϊ����Topic �ɴ���ָ��������к�����

        Destination destination=session.createQueue("TextQueue");

        Destination destination1=session.createQueue("MapQueue");


        //5.ͨ��Session���󴴽���Ϣ��������������

        MessageProducer messageProducer=session.createProducer(destination);

        MessageProducer messageProducer1=session.createProducer(destination1);

        //MessageConsumer messageConsumer=session.createConsumer(destination);

        //6.���������ߵ��������ݵĳ־û�����
        /*
        * @see javax.jms.DeliveryMode#NON_PERSISTENT
        * @see javax.jms.DeliveryMode#PERSISTENT
         * @see javax.jms.Message#DEFAULT_DELIVERY_MODE*/
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        messageProducer1.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        //7.�������� �����߷������� �����߽������� �ر�connection ����

        TextMessage textMessage=new ActiveMQTextMessage();
        for(int i=0;i<10;i++){
            textMessage.setText("Message>"+i+":"+String.valueOf(new Date(System.currentTimeMillis())));
            messageProducer.send(textMessage);
        }

        MapMessage mapMessage=new ActiveMQMapMessage();
        for(int i=0;i<10;i++){
            mapMessage.setString(String.valueOf(i),String.valueOf(System.currentTimeMillis()));
            messageProducer1.send(mapMessage);
        }


        session.commit();

        connection.close();

        System.out.println("Producers has sended Messages ok!");


    }

    public static void main(String[] args) throws JMSException {
        Producer producer=new Producer();
        producer.startProduce();
    }
}
