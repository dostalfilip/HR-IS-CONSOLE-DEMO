package java;
import java.util.EnumSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * User console interface
 */
public class ConsoleMenu {
	private static Scanner scanner = new Scanner(System.in);
	private static MysqlConnect mysqlConect = new MysqlConnect();
	private static boolean done = false;
	
	/*
	 * main method
	 */
	public static void run(String[] args){
		System.out.println("            ***Welcome to HR Information System***\n"
				+ "+-------------------------------------------------------------+");
        mysqlConect.connect();
        while(done != true){     	
        	printMenu();
        	switch(scanner.next()){
        		case "0":
        			mysqlConect.showEmployee();
        			break;
        		case "9":
        			done = true;
        			break;
        		case "1":
        			System.out.println("Zadej nasledujici atributy: ");
        			try{
        			scanner.nextLine();
        			System.out.println("Jmeno: ");
        			String name = scanner.nextLine();
        			System.out.println("Vek: ");
        			
        			int age = scanner.nextInt();
        			if(age < 10 || age > 100){
        				throw new IllegalArgumentException(); 
        			}
        			Position position =setPosition(scanner);      
        			if(position == null) break;
        			mysqlConect.addEmployee(name, age, position);
        			}
        			catch(InputMismatchException e){
        				e.printStackTrace();
        			}
        			catch(IllegalArgumentException e){
        				System.out.println("Vkladani zruseno! Vek nesmi byt mensi nez 10 a vetsi nez 100!");

        			}
        			break;
        		case "3":
        			mysqlConect.showAverageAndCount();
        			break;
        		case "2":
            		System.out.println("Zadej id ke smazani: ");
            		try{
            			int position = scanner.nextInt();
            			mysqlConect.deleteAt_id(position);            			
            		}catch(InputMismatchException e){
            			System.out.println("Mazani zruseno / spatna volba!");
            		}
            
        			break;
        		case "4":
        			mysqlConect.showAverageAge();
        			break;
        		case "8":
            		System.out.println("Kolik zaznamu si prejes vygenerovat : ");
            		try{
            		int count = scanner.nextInt();
            		if(count<1) throw new InputMismatchException();
        			mysqlConect.generateRow(count);
            		}catch(InputMismatchException e){
            			System.out.println("Spatna Volba/Zadej pouze kladne cislo.");
            			break;
            		}
        			break;
        		default:
        			System.out.println("Zadna/Spatna volba.");
        			break;
        	}
        	
        }
        mysqlConect.disconnect();
	}
	
	/*
	 * print all choices
	 */
	private static void printMenu(){
        System.out.format("+-------------------------------------------------------------+%n");
        System.out.format("|              ZVOLTE POZADOVANOU VOLBU                       |%n");
        System.out.format("|-------------------------------------------------------------|%n");
        System.out.format("|              0 Vypis Tabulky Zamestnancu                    |%n");
        System.out.format("|              1 Pridat Zaznam                                |%n");
        System.out.format("|              2 Smazat Zaznam                                |%n");
        System.out.format("|              3 Vypis Sestavy Prumer a Pocet Zamestnancu     |%n");
        System.out.format("|              4 Vypis Sestavy Vek Zamestnancu                |%n");
        System.out.format("|              8 Generovani zaznamu                           |%n");
        System.out.format("|              9 Konec                                        |%n");
        System.out.format("+-------------------------------------------------------------+%n");
	}

	/*
	 * return Enum Position
	 */
	private static Position setPosition(Scanner scanner){
		StringBuilder output = new StringBuilder();
		output.append("Zvolte(Opiste) jednu z moznosti\n(na velikosti pismen nezalezi) \n{");
		for (Position info : EnumSet.allOf(Position.class)) {
			output.append((info.toString().toUpperCase() + ","));
		}
		output.setCharAt(output.length()-1, '}');
		output.append(":");
		
		System.out.println(output);
		try{
		Position value = Position.valueOf(scanner.next().toUpperCase());
		return value;
		}
		catch(IllegalArgumentException e){
			System.out.println("Vkladani zruseno ! Vyberte jednu z moznosti, muzete pouzit velka i mala pismena! ");
		}
		return null;
	}
}
