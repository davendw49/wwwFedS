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

public class exConnection {
		public static SailRepositoryConnection exConn=null;
		public static SailRepository repository=null;
		
		public static void openExConn() throws RepositoryException
		{
			// create a sesame Native sail
			
			File dataDir = new File("C:\\lucenesail\\ex\\c");
//			File dataDir = new File("/home/LuceneSailMemo/nyt");
			MemoryStore myStore = new MemoryStore(dataDir);

			// create a lucenesail to wrap the memorystore
			LuceneSail lucenesail = new LuceneSail();
			// set this parameter to let the lucene index store its data in ram
			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "true");
			// set this parameter to store the lucene index on disk
			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "C:\\lucenesail\\ex\\c");
//			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "/home/LuceneSailMemo/nyt");
			// wrap memorystore in a lucenesail
			lucenesail.setBaseSail(myStore);

			// create a Repository to access the sails
			repository = new SailRepository(lucenesail);
			
				repository.initialize();

				// add some test data, the FOAF ont
				exConn = repository.getConnection();
				// connection.begin();
				
				// connection.setAutoCommit(false);
				// System.out.println(System.getProperty("user.dir")); antagonism
				// 数据文件的路径，改为 自己电脑上的路径
				
				//String file_path = "C:\\PengPeng\\Data\\LuceneData\\Data\\test.nt";
				//File file = new File(file_path);
				//System.out.println(file.exists());
				
				exConn.commit();
		}
		
		public static SailRepositoryConnection getExConn()
		{
			return exConn;
		}
		
		public static void closeConn()
		{
			try {
				exConn.close();
				repository.shutDown();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
