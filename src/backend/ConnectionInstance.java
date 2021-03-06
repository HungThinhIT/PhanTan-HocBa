package backend;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionInstance {
	
	private ConfigEnvDatabase ENV;
	private Connection conn;
	
	public ConnectionInstance() {
		ConfigEnvDatabase initENV = new ConfigEnvDatabase();
		this.ENV = initENV;
	}
	
	/*
	 * Server Code: 
	 * 1 => SV1
	 * 2 => SV2
	 * 3 => SV3
	 */
	
	public Connection prepareServerInstance(int serverCode) {
		switch (serverCode) {
			case 1:
				System.out.println("Entering Database Server 1");
				ENV.setINSTANCE_NAME("DESKTOP-M0H9CD2\\SQLSERVER1;");
				ENV.setDATABASE_NAME("HOCBA;");
				ENV.setUSER_NAME("sa");
				ENV.setPASSWORD("123123");
				break;
			case 2:
				System.out.println("Entering Database Server 2");
				 ENV.setINSTANCE_NAME("DESKTOP-M0H9CD2\\SQLSERVER2;");
				 ENV.setDATABASE_NAME("HOCBA_SV2;");
				 ENV.setUSER_NAME("sa");
				 ENV.setPASSWORD("123123");
				 break;
			case 3:
				System.out.println("Entering Database Server 3");
				ENV.setINSTANCE_NAME("DESKTOP-M0H9CD2\\SQLSERVER3;");
				ENV.setDATABASE_NAME("HOCBA_SV3;");
				ENV.setUSER_NAME("sa");
				ENV.setPASSWORD("123123");
				break;
			default:
				System.out.println("Entering Database Server Unknown");
				ENV.setINSTANCE_NAME("Phoenix-PC;");
				ENV.setDATABASE_NAME("");
				ENV.setUSER_NAME("sa");
				ENV.setPASSWORD("123123");
				break;
		}
        conn = getConnection(ENV.getDB_SQL_URL(), ENV.getUSER_NAME(), ENV.getPASSWORD());
		return conn;		
	}
	
	public Connection getConnectionInstance() {
        conn = getConnection(ENV.getDB_SQL_URL(), ENV.getUSER_NAME(), ENV.getPASSWORD());
		return conn;
	}
 
    
    /*
     * Override getConnect method for debug connection.
     */
    public static Connection getConnection(String dbURL, String userName, 
            String password) {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(dbURL, userName, password);
            System.err.println("");
            System.out.println("Connect to SQLServer successfully!");
        } catch (Exception ex) {
            System.err.println("");
            if(ex.getMessage().toString().contains("Verify the server and instance names and check that no firewall is blocking UDP traffic to port 1434")) {
            	System.err.println("SQL Connect Error: Please enable `SQL Server Browser` service");
            	System.exit(0);
            }
            else {
            	System.err.println("Connect failure! Check again.");
            	ex.printStackTrace();
            	System.exit(0);
            }
        }
        return conn;
    }
}
