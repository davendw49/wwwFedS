package wwwFedS.luceneSail;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.openrdf.query.Binding;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.repository.sail.SailRepositoryConnection;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;
import org.openrdf.sail.lucene.LuceneSail;
import org.openrdf.sail.lucene.LuceneSailSchema;
import org.openrdf.sail.memory.MemoryStore;
import org.openrdf.sail.nativerdf.NativeStore;

public class nytConnection {
		public static SailRepositoryConnection nytConn=null;
		public static SailRepository repository=null;
		
		public static void openNytConn() throws RepositoryException, RDFParseException, IOException
		{
			String index_path = "/home/LuceneSailIndex/nyt";
//			String in_file_path = "/home/pengpeng/apache-tomcat-7.0.85/webapps/FKdata/ChEBI/chebi.n3";
			 
			// create a sesame memory sail
			NativeStore myStore = new NativeStore();
			File dataDir = new File(index_path);
			myStore.setDataDir(dataDir);

			// create a lucenesail to wrap the memorystore
			LuceneSail lucenesail = new LuceneSail();
			// set this parameter to let the lucene index store its data in ram
//			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "true");
			// set this parameter to store the lucene index on disk
			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "/home/LuceneSailKey/nyt");

			// wrap memorystore in a lucenesail
			lucenesail.setBaseSail(myStore);

			// create a Repository to access the sails
			repository = new SailRepository(lucenesail);
			repository.initialize();
	 
			nytConn= repository.getConnection();
			// connection.begin();
			
				// connection.setAutoCommit(false);
				// System.out.println(System.getProperty("user.dir"));  
				
//				String file_path = in_file_path;
//				File file = new File(file_path);
//				System.out.println(file.exists());
			
//				URL url = new URL("http://120.79.54.110:8080/FKdata/NYT/locations.rdf");
//				nytConn.add(url, "", RDFFormat.RDFXML);
				
//				URL url1 = new URL("http://120.79.54.110:8080/FKdata/NYT/organizations.rdf");
//				nytConn.add(url1, "", RDFFormat.RDFXML);
				
//				URL url2 = new URL("http://120.79.54.110:8080/FKdata/NYT/people.rdf");
//				nytConn.add(url2, "", RDFFormat.RDFXML);
				
				
				nytConn.commit();
				
				System.out.println("------ nyt文件已生成 -----");
		}
		
		public static SailRepositoryConnection getNytConn()
		{
			return nytConn;
		}
		
		public static void closeConn()
		{
			try {
				nytConn.close();
				repository.shutDown();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
