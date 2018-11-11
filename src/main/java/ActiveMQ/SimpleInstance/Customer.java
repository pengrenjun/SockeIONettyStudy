package ActiveMQ.SimpleInstance;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import java.sql.Date;
import java.util.Enumeration;
import java.util.concurrent.TimeUnit;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

/**
 * @Description: ActiveMq��ʵ��:������
 * @Author��pengrj
 * @Date : 2018/11/10 0010 19:20
 * @version:1.0
 */
public class Customer {

    //��ActiveMq��Ϣ�м��ƽ̨��ȡ��������
    public  void startCustom() throws JMSException, InterruptedException {

        //1.����ConnectionFactory��������

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

        MessageConsumer messageConsumer=session.createConsumer(destination);
        MessageConsumer messageConsumer1=session.createConsumer(destination1);

        //6.���������ߵ��������ݵĳ־û�����

        //7.�������� �����߷������� �����߽������� �ر�connection ����



           TextMessage textMessage= (TextMessage) messageConsumer.receive();

           MapMessage mapMessage= (MapMessage) messageConsumer1.receive();

           Enumeration mapNames = mapMessage.getMapNames();

           System.out.println(textMessage.toString());


           System.out.println("TextQueue>"+textMessage.getText());



        session.commit();

        connection.close();

        System.out.println("Customer has customed Messages ok!");


    }

    public static void main(String[] args) throws JMSException, InterruptedException {
        Customer customer=new Customer();
        customer.startCustom();
    }
}
