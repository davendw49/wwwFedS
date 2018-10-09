package wwwFedS.run;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import wwwFedS.luceneSail.chebiConnection;
import wwwFedS.luceneSail.dbpediaConnection;
import wwwFedS.luceneSail.drugbankConnection;
import wwwFedS.luceneSail.exConnection;
import wwwFedS.luceneSail.geonamesConnection;
import wwwFedS.luceneSail.jamendoConnection;
import wwwFedS.luceneSail.keggConnection;
import wwwFedS.luceneSail.linkedmdbConnection;
import wwwFedS.luceneSail.nytConnection;
import wwwFedS.luceneSail.swdfoodConnection;

import org.openrdf.repository.RepositoryException;
import org.openrdf.rio.RDFParseException;



public class Run2 {

	/**
	 * @param args
	 * @throws RDFParseException 
	 */
	public static void main(String[] args) throws RDFParseException {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("start to open the connection ");
			

			
			try {
				
//				exConnection.openExConn();
				
				dbpediaConnection.openDbpediaConn();
				
				chebiConnection.openChebiConn();
				drugbankConnection.openDrugbankConn();
				keggConnection.openKeggConn();
				
				jamendoConnection.openJamendoConn();
				linkedmdbConnection.openLinkedmdbConn();
				nytConnection.openNytConn();
				swdfoodConnection.openSwdfoodConn();
				geonamesConnection.openGeonamesConn();
				
				
			} catch (RepositoryException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ServerSocket ss=new ServerSocket(223);
			System.out.println("启动223端口监听");
	
			int count=0;
			
			//开始监听
			
			while(true)
			{
				 Socket s=ss.accept();
				 Thread st=new ServerThread(s);
				 count++;
				 System.out.println("这是服务的第 "+count+" 个请求");
				 st.run();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
