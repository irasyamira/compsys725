/**
 * author: Ira Sukimin (isuk218@aucklanduni.ac.nz)
 * COMPSYS725 - Computer Networks and Distributed Applications
 **/

import java.io.*; 
import java.net.*; 

class serverTCP { 
 
	//variable declaration
	public static String args;
	public static String cmd;
	public static String command; 
	public static String fileType = "b"; 
	
	public static String errorMessage = "! unidentified error";
	public static boolean userLoggedIn = false;
	public static boolean accountLoggedIn = false;
	public static boolean freeToConnect = true;
	
	public static ServerSocket welcomeSocket;
	public static Socket connectionSocket;
	public static BufferedReader inFromClient;
	public static DataOutputStream outToClient;
	
	
	public void acceptConnection() throws Exception {
		System.out.println("server is running..."); 
		connectionSocket = welcomeSocket.accept();	
		System.out.println("a client is connected..."); 		
		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
		outToClient = new DataOutputStream(connectionSocket.getOutputStream()); 
		outToClient.writeBytes("+CS725 SFTP Service\n");
	}
	
	public void checkValidCommand() throws Exception {

		command = inFromClient.readLine();
		
		if (command != null) {
			String[] parts = command.split("\\[ ",2);
			cmd = parts[0];
			String a = parts[1];
			String[] kentuts = a.split("\\]",2);
			args = kentuts[0];

			if (cmd.equalsIgnoreCase("USER")) {
				USER();
			}
			else if (cmd.equalsIgnoreCase("ACCT")) {
				ACCT();
			}
			else if (cmd.equalsIgnoreCase("PASS")) {
				PASS();
			}	
			else if (cmd.equalsIgnoreCase("TYPE")) {
				TYPE();
			}	
			else if (cmd.equalsIgnoreCase("LIST")) {
				LIST();
			}				
		}
		else {
			System.out.println("client has disconnected..."); 
			acceptConnection();
			checkValidCommand();
		}
				
	}
	
	public void USER() throws Exception {
		System.out.println("USER() called");
		String username = args;
		
		if (userLoggedIn == false) {
			if (args.equalsIgnoreCase("syammy")) {
				errorMessage = "+ login was succesful";
				userLoggedIn = true;
			} else {
				errorMessage = "- user does not exist";
			}
		}
		else {
			errorMessage = "! user is already logged in";
		}	
		outToClient.writeBytes(errorMessage + "\n");
	}

	public void ACCT() throws Exception {
		System.out.println("ACCT() called");
		String account = args;
		
		if (accountLoggedIn == false) {		
			if (args.equalsIgnoreCase("syumu")) {
				errorMessage = "+ login was succesful";
				accountLoggedIn = true;
			} else {
				errorMessage = "- account does not exist";
			}
		}	
		else {
			errorMessage = "! account is already logged in";
		}
		outToClient.writeBytes(errorMessage + "\n"); 
	}

	public void PASS() throws Exception {
		System.out.println("PASS() called");
		String password = args;
		
		if (args.equalsIgnoreCase("kentut")) {
			errorMessage = "! logged in"; //"+ password ok but u havent specified the account";
			accountLoggedIn = true;
		} 
		else {
			errorMessage = "- wrong password try again"; 
		}		
		outToClient.writeBytes(errorMessage + "\n");
	}	

	public void TYPE() throws Exception {
		System.out.println("TYPE() called");
	
		if (args.equalsIgnoreCase("a")) {
			fileType = "a";
			errorMessage = "+ using Ascii mode";
		} 
		else if (args.equalsIgnoreCase("b")) {
			fileType = "b";
			errorMessage = "+ using Binary mode";
		} 
		else if (args.equalsIgnoreCase("c")) {
			fileType = "c";
			errorMessage = "+ using Continuous mode"; 
		}
		else {
			errorMessage = "- type not valid";
		}
		outToClient.writeBytes(errorMessage + "\n");
	}
	
	public void LIST() throws Exception {
		System.out.println("LIST() called");
	
		if (args.equalsIgnoreCase("a")) {
			fileType = "a";
			errorMessage = "+ using Ascii mode";
		} 
		else if (args.equalsIgnoreCase("b")) {
			fileType = "b";
			errorMessage = "+ using Binary mode";
		} 
		else if (args.equalsIgnoreCase("c")) {
			fileType = "c";
			errorMessage = "+ using Continuous mode";
		}
		else if (args == "") {
			fileType = "b";
			errorMessage = "+ using Binary mode";
		}
		else {
			errorMessage = "- type not valid";
		}
		
		outToClient.writeBytes(errorMessage + "\n");	
	}
	
    public static void main(String argv[]) throws Exception {
		
		//create new instance of serverTCP
		serverTCP server = new serverTCP();
	
		//setup of welcoming socket
		welcomeSocket = new ServerSocket(1024); 
		server.acceptConnection();
			
		while(true) {
			server.checkValidCommand(); 
		} 

	}
} 


