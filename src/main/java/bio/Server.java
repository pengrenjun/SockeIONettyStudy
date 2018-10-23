package bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

//Socketͨ�ŷ�������
public class Server {

	final static int PROT = 8765;
	
	public static void main(String[] args) {
		
		ServerSocket server = null;
		try {
			server = new ServerSocket(PROT);
			System.out.println(" server start .. ");
			//�������� �ȴ�ͨ����Ϣ
			Socket socket = server.accept();
			//�½�һ���߳�ִ�пͻ��˵�����
			new Thread(new ServerHandler(socket)).start();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(server != null){
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			server = null;
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
