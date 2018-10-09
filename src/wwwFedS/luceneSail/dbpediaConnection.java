package wwwFedS.luceneSail;

import java.io.File;
import java.io.IOException;
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

public class dbpediaConnection {
		public static SailRepositoryConnection dbpediaConn=null;
		public static SailRepository repository=null;
		
		public static void openDbpediaConn() throws RepositoryException, RDFParseException, IOException
		{
			String index_path = "/home/LuceneSailIndex/dbpedia";
			
			 
			// create a sesame memory sail
			NativeStore myStore = new NativeStore();
			File dataDir = new File(index_path);
			myStore.setDataDir(dataDir);

			// create a lucenesail to wrap the memorystore
			LuceneSail lucenesail = new LuceneSail();
			// set this parameter to let the lucene index store its data in ram
//			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "true");
			// set this parameter to store the lucene index on disk
			lucenesail.setParameter(LuceneSail.LUCENE_DIR_KEY, "/home/LuceneSailKey/dbpedia");

			// wrap memorystore in a lucenesail
			lucenesail.setBaseSail(myStore);

			// create a Repository to access the sails
			repository = new SailRepository(lucenesail);
			repository.initialize();
	 
			dbpediaConn= repository.getConnection();
			// connection.begin();
			
				// connection.setAutoCommit(false);
				// System.out.println(System.getProperty("user.dir"));  
				
//				String in_file_path = "/home/daven/chebi.n3";
//				String file_path = in_file_path;
//				File file = new File(file_path);
//				System.out.println(file.exists());
			
//				URL url = new URL("http://120.79.54.110:8080/FKdata/ChEBI/chebi.n3");
			
//				dbpediaConn.add(file, "", RDFFormat.N3);
	
/*			
			String in_file_path = "/home/daven/dbpedia_sub/DBPedia-Subset/";
			String[] dbp_arr = {"article_categories_en.nt","instance_types_en.nt","persondata_en.nt",
					"skos_categories_en.nt","labels_en.nt","nyt_links.nt","category_labels_en.nt","DBpedia-LGD.nt",
					"dbpedia.nt","geo_coordinates_en.nt","images_en.nt"};
			for(String s : dbp_arr){
 				String file_path = in_file_path+s;
//				createSimple(index_path,file_path);
				
				File file = new File(file_path);
				System.out.println(file.exists());
				System.out.println(s+" start---------------- ");
				dbpediaConn.add(file, "", RDFFormat.NTRIPLES);	
				System.out.println(s+" end---------------- ");
				dbpediaConn.commit();
			}
			for(int i=0;i<19;i++){
				String file_path = in_file_path+"out"+i+".nt";
				File file = new File(file_path);
				System.out.println(file.exists());
				System.out.println(i+" start---------------- ");
				dbpediaConn.add(file, "", RDFFormat.NTRIPLES);	
				System.out.println(i+" end---------------- ");
				dbpediaConn.commit();
			}
*/					
				dbpediaConn.commit();
				
				System.out.println("------ dbpedia文件已生成 -----");
		}
		
		public static SailRepositoryConnection getDbpediaConn()
		{
			return dbpediaConn;
		}
		
		public static void closeConn()
		{
			try {
				dbpediaConn.close();
				repository.shutDown();
			} catch (RepositoryException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
