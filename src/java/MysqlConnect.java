package java;
import java.security.SecureRandom;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
/*
 * Class support all task with database
 */
public class MysqlConnect {
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/jooqtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "pass";
    private static final String MAX_POOL = "250";
    private static final String FALSE = "false";
    private static final UtilityClass utility = new UtilityClass();

    // init connection object
    private Connection connection;
    // init properties object
    private Properties properties;

    // create properties
    private Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", USERNAME);
            properties.setProperty("password", PASSWORD);
            properties.setProperty("MaxPooledStatements", MAX_POOL);
            properties.setProperty("useSSL", FALSE);
            properties.setProperty("verifyServerCertificate", FALSE);
       
        }
        return properties;
    }

    // connect database
    public Connection connect() {
        if (connection == null) {
            try {
                System.out.println("Approaching to database...");
                Class.forName(DATABASE_DRIVER);
                connection = DriverManager.getConnection(DATABASE_URL, getProperties());
                System.out.println("Successful Connected.");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                System.out.println("Nepodaøilo se pøipojit k databazi ! zkontrolujte mysql databazi a resetujte aplikaci !");
            }
        }
        return connection;
    }

    // disconnect database
    public void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Disconected.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //show employee
    public boolean showEmployee(){
        try {
            String stm = "select * from employee;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(stm);
            
            System.out.format("+-------------------------------------------------------------+%n");
            System.out.format("|  ID  |          NAME          |  AGE  |     POSITION        |%n");
            System.out.format("+-------------------------------------------------------------+%n");
            
            while(rs.next())
            {
            	String output = "   ";
                int id = rs.getInt("id");
                String name = rs.getString("Name");
                int age = rs.getInt("Age");
                String position = rs.getString("Position");   
                
                output += id;
                output = utility.checkLenght(output, 9);
                output += name;
                output = utility.checkLenght(output, 35);
                output += age;
                output = utility.checkLenght(output, 42);
                output += position;

                System.out.println(output);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    //add new employee
    public boolean addEmployee(String name, int age, Position position){
    	try{

    		//int id = getMax_id() + 1;   
    		String id = "null";
    		StringBuilder stm = new StringBuilder();
    		stm.append("insert into employee values (");
    		stm.append(id+",'");
    		stm.append(name+"',");
    		stm.append(age+",'");
    		stm.append(position.toString()+"');");

    		Statement stmt = connection.createStatement();
    		stmt.executeUpdate(stm.toString());  
    	}
    	catch (SQLException e) {
        e.printStackTrace();
        return false;
    	}
    	System.out.println("Záznam pøidán.");
        return true;
    }
    
    //return the biggist index in id column
    //currently unused
    private int getMax_id(){
        try {
        	int output = 0;
        	String sql = "SELECT MAX(id) FROM employee;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);         
            if(rs.next()){
            	output = rs.getInt("MAX(id)");
            } 
            return output;
    	} catch (SQLException e) {
        e.printStackTrace();
        return 0;
    	}
    }
    
    // delete row at position and return true
    public boolean deleteAt_id(int position){
    	try{
 
    		 String sql = "select * FROM employee WHERE id = " + position + ";";
    		 Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql);
             if(!rs.next()){
            	 throw new IllegalArgumentException();
             }
    	}
    	catch (SQLException e) {
    		System.out.println("SQLException");
    		return false;
    	}
    	catch (IllegalArgumentException e) {
    		System.out.println("Zaznam s timto id neexistuje!");
    		return false;
    	}
    	
    	try{
    		String sql = "DELETE FROM employee WHERE id = " + position + ";";
    		PreparedStatement st = connection.prepareStatement(sql);
    	    st.executeUpdate(); 
    	}
    	catch (SQLException e) {
        e.printStackTrace();
        return false;
    	}
    	System.out.format("Zaznam %d Smazán.\n",position);
        return true;
    }

    // avarage and count of table employee
    public boolean showAverageAndCount(){
        try {
            String stm =	"SELECT Position, COUNT(*) AS 'Count of employees', round((avg(Age))) AS 'Average age' " +
            				"FROM employee " +
            				"GROUP BY Position " +
            				"order by COUNT(*) DESC;";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(stm);
            
            System.out.format("+-------------------------------------------------------------+%n");
            System.out.format("|       Position       |  Count of employees  |  Average age  |%n");
            System.out.format("+-------------------------------------------------------------+%n");
            
            while(rs.next())
            {
            	String output = "    ";
                String position = rs.getString("Position");
                int count = rs.getInt("Count of employees");
                int age = rs.getInt("Average age");  
                
                output += position;
                output = utility.checkLenght(output, 33);
                output += count;
                output = utility.checkLenght(output, 53);
                output += age;

                System.out.println(output);
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    
    }

    // generator of random row in employee
    public boolean generateRow(int count){
    try{    	  
    	String id = "null";
    	for(int i = 0; i < count; i++ ){
    		String name = utility.generateString();
    		int age = utility.generateInt(25,60);
    		Position position = Position.values()[(int)(Math.random()*(Position.values().length))];
    		
    		
    		StringBuilder stm = new StringBuilder();
    		stm.append("insert into employee values (");
    		stm.append(id+",'");
    		stm.append(name+"',");
    		stm.append(age+",'");
    		stm.append(position.toString()+"');");
    		
    		Statement stmt = connection.createStatement();
    		stmt.executeUpdate(stm.toString());      		
    	}
	}
	catch (SQLException e) {
    e.printStackTrace();
    return false;
	}
    System.out.println("Bylo vygenerováno " + count + " záznamù. ");
    return true;
    	
    }
    
    public boolean showAverageAge(){
    	System.out.format("+-------------------------------------------------------------+%n");
    	System.out.format("|   Age   |                 Count of employees                |%n");
    	System.out.format("+-------------------------------------------------------------+%n");

    	
    	try {
    		LinkedHashMap<String,String> stm = new LinkedHashMap<String,String>();
    		stm.put("<20","select count(*) AS 'Count of employees'  from employee where Age < 20;");
    		stm.put("20-40","select count(*) AS 'Count of employees'  from employee where Age between 20 AND 40;");
    		stm.put("40-60","select count(*) AS 'Count of employees'  from employee where Age between 40 AND 60;");
    		stm.put("60+", "select count(*) AS 'Count of employees'  from employee where Age > 60;");
           
    		for(String key : stm.keySet()){
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(stm.get(key));     
            if(rs.next()){
            	String output = "    ";
            	String age = key;
            	int count = rs.getInt("Count of employees");
            	output += age;
            	output = utility.checkLenght(output, 35);
            	output += count;
            	
            	System.out.println(output);            	
            }
            
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
  }