import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.sql.*;
import java.io.*;
import java.io.BufferedReader;

class myjdbcproj{
    public static void main(String args[]) throws IOException
    {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String sql;
        String cust_no;
        long balance;
        ResultSet rs;
        String name;
        long ph_no;
        String city;
        int n;
        long amount;
        Connection con=null;
        Statement stmt=null;                                                  
        int ch;                                                               
                                                                                                           
        // Declare common variables if any
        try{                                                               
            // Load the driver class                                      
            Class.forName("oracle.jdbc.driver.OracleDriver");          
            // Create the connection object
            String conurl= "jdbc:mysql://localhost/EMP";
;
            con=DriverManager.getConnection(conurl,"root","Subhu@00");
            stmt=con.createStatement();
            con.setAutoCommit(false);
            do
            {

                System.out.println("\n\n***** Banking Management System*****");
                // Display the menu

                System.out.println("Enter your choice(1-9):");
                // Accept user's choice
                System.out.println("1: Display Customer Records");
                System.out.println("2: Add Customer Records");
                System.out.println("3: Delete Customer Records");
                System.out.println("4: Update Customer Records");
                System.out.println("5: Display Account Details");
                System.out.println("6: Display Loan Details");
                System.out.println("7: Deposit Money");
                System.out.println("8: Withdraw Money");
                System.out.println("9: Exit");

                ch = Integer.parseInt(bf.readLine());
                switch(ch)
                {
                    case 1:
                        // Display customer records                      
                        sql = "SELECT * FROM CUSTOMER";
                        rs = stmt.executeQuery(sql);                        
                        System.out.println("CUST_NO\tNAME\tPHONE_NO\tCITY ");
                        while(rs.next())
                        {
                            cust_no = rs.getString("CUST_NO");
                            name = rs.getString("NAME");
                            ph_no = rs.getLong("PHONE_NO");
                            city = rs.getString("CITY");                 
                                                                          
                            System.out.println(cust_no+"\t");
                            System.out.println(name+"\t");
                            System.out.println(ph_no+"\t");
                            System.out.println(city+"\t");
                        }
                        break;
                    case 2:
                        // Add customer record
                        // Accept input for each column from user
                        System.out.print("Enter Customer no :");
                        cust_no = bf.readLine();
                        System.out.print("Enter name :");
                        name = bf.readLine();
                        System.out.print("Enter phone no :");
                        ph_no = Long.parseLong(bf.readLine());
                        System.out.print("Enter City :");
                        city = bf.readLine();
                        sql= "insert into customer values('"+cust_no+"','"+name+"',"+ph_no+",'"+city+"')";
                        n = stmt.executeUpdate(sql);
                        con.commit();
                        System.out.println(n+"rows inserted");
                        break;
                    case 3:
                        // Delete customer record
                        // Accept customer number from user
                        System.out.print("Enter Customer no :");
                        cust_no = bf.readLine();
                        sql="delete from customer where cust_no = '"+cust_no+"'";
                        n=stmt.executeUpdate(sql);                            
                        con.commit();
                        System.out.print("Deleted "+n+" Records");       
                        break;
                    case 4:
                        // Update customer record
                        System.out.print("Enter Customer no :");
                        cust_no = bf.readLine();

                        // Accept customer number from user
                        System.out.println("Enter 1: For Name 2: For Phone no 3: For City to update:");
                        // Accept user's choice
                        int ch2 = Integer.parseInt(bf.readLine());
                        switch(ch2)
                        {
                        case 1:
                        // Update customer's name
                            System.out.println("Enter customer name : ");
                            name = bf.readLine();
                            sql="update customer set name = '"+name+"' where cust_no = '"+cust_no+"'";
                            n=stmt.executeUpdate(sql);
                            con.commit();
                            System.out.print("Updated "+n+" Records");
                        break;
                        case 2:
                        // Update customer's phone number
                            System.out.println("Enter customer phone number : ");
                            ph_no = Long.parseLong(bf.readLine());
                            sql="update customer set phone_no = '"+ph_no+"' where cust_no = '"+cust_no+"'";
                            n=stmt.executeUpdate(sql);
                            con.commit();
                            System.out.print("Updated "+n+" Records");

                        break;
                        case 3:
                        // Update customer's city
                            System.out.println("Enter customer city : ");
                            city = bf.readLine();
                            sql="update customer set city = '"+city+"' where cust_no = '"+cust_no+"'";
                            n=stmt.executeUpdate(sql);
                            con.commit();                               
                            System.out.print("Updated "+n+" Records");     
                        break;                                        
                        }
                        break;
                    case 5:
                        // Display account details
                        // Accept customer number from user
                        System.out.print("Enter Customer no :");    
                        cust_no = bf.readLine();           
                        sql = "select * from customer natural join depositor natural join account where cust_no = '"+cust_no+"'";
                        rs = stmt.executeQuery(sql);
                        while(rs.next())
  	                        {                                                   
		               System.out.println(rs.getString("account_no")+"\t");
		               System.out.println(rs.getString("type")+"\t");
		               System.out.println(rs.getLong("balance")+"\t");
		               System.out.println(rs.getString("branch_code")+"\t"); 
			}                                                                  
                        break;                                           
                    case 6:
                        // Display loan details
                        // Accept customer number from user
                        System.out.println("Enter Customer no :");        
                        cust_no = bf.readLine();
                        // Display the number of loans the customer has or  
                        // Congratulation if he customer has no loan
                        sql = "select * from loan where cust_no = '"+cust_no+"'";
                        rs = stmt.executeQuery(sql);
                        System.out.println("LOAN_NO\tCUST_NO\tAMOUNT\tBRANCH_CODE ");
                        int counter=0;
                        while(rs.next())
                        {
                            System.out.println(rs.getString("loan_no")+"\t");
                            System.out.println(rs.getString("cust_no")+"\t");
                            System.out.println(rs.getLong("amount")+"\t");
                            System.out.println(rs.getString("branch_code")+"\t");
                            counter++;
                        }
                        if(counter>0)
                        {
                            System.out.println("Customer has "+counter+"loans");
                        }
                        else
                        {
                            System.out.println("Congratulations");
                            System.out.println("Customer has no loans");
                        }
                        break;
                    case 7:
                        //Deposit money
                        // Accept the account number to be deposited in
                        // Message for transaction completion
                        System.out.print("Enter Account no :");
                        cust_no = bf.readLine();
                        System.out.print("Enter Amount :");               
                        amount=Long.parseLong(bf.readLine());
                        sql="select balance from account where account_no = '"+cust_no+"'";
                        rs = stmt.executeQuery(sql);
                        rs.next();
                        balance = rs.getLong("balance") + amount;
                        sql="update account set balance ="+balance+" where account_no = '"+cust_no+"'";
                        stmt.executeUpdate(sql);
                        con.commit();
                        System.out.println("Deposit Successful");
                        break;
                    case 8:
                        //Withdraw money
                        // Accept the account number to be withdrawn from
                        // Handle appropriate withdral ckeck conditions
                        // Message for transaction completion
                        System.out.println("Enter Account no :");
                        cust_no = bf.readLine();
                        System.out.println("Enter Amount :");
                        amount=Long.parseLong(bf.readLine());
                        sql="select balance from account where account_no = '"+cust_no+"'";
                        rs = stmt.executeQuery(sql);
                        rs.next();
                        balance = rs.getLong("balance");
                        if(balance>amount)
                        {
                            balance = balance - amount;
                            sql="update account set balance ="+balance+" where account_no = '"+cust_no+"'";
                            stmt.executeUpdate(sql);
                            con.commit();
                            System.out.println("Withdrawal successful");
                        }
                        else
                        {
                            System.out.println("Insufficient Balance");
                        }

                        break;
                    case 9:
                        // Exit the menu
                        System.out.println("Closing Connection");
                        stmt.close();
                        con.close();
                        break;
                    default:
                        // Handle wrong choice of option
                        System.out.println("Please Input Valid Response");
                }

            }while(ch!=9);
        } //try closing
        catch(Exception e)
        { // Handling exception
            System.out.println(e);
        }
    }// main closing
}// End class
