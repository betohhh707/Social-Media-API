package Service;
import Model.Account;
import DAO.AccountDAO;
import java.util.List;
/**
 * The purpose of a Service class is to contain "business logic" that sits between the web layer (controller) and
 * persistence layer (DAO). That means that the Service class performs tasks that aren't done through the web or
 * SQL: programming tasks like checking that the input is valid, conducting additional security checks, or saving the
 * actions undertaken by the API to a logging file.
 *
 * It's perfectly normal to have Service methods that only contain a single line that calls a DAO method. An
 * application that follows best practices will often have unnecessary code, but this makes the code more
 * readable and maintainable in the long run!
 */
public class AccountService{
    
    private AccountDAO accountDAO;
    //no-args constructor for creating a new AuthorService with a new AuthorDAO.
    public AccountService(){
        accountDAO = new AccountDAO();
    }
     /**
     * Constructor for a AccountService when a AccountDAO is provided.
     * This is used for when a mock AccountDAO that exhibits mock behavior is used in the test cases.
     * This would allow the testing of AccountService independently of AccountDAO.
     * There is no need to modify this constructor.
     * @param accountDAO
     */
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    //Gets Account by ID using accountDAO
    public List<Account> getAccountById(int id){
        return accountDAO.getAccountById(id);
    }

    //Get all the Accounts using accountDAO
    public List<Account> getAllAccounts(){
        return accountDAO.getAllAccounts();
    }

    //Get an account with a specific username using accountDAO
    public Account getAccountByUsername(String username){
        return accountDAO.getAccountById(username);
    }

    //validate an account using accountDAO
    public Account validateLogin(Account account){
        return accountDAO.validateLogin(account.getUsername,account.getPassword);
    }

    public Account createAccount(Account account){

    }

    public boolean updateAccount(Account account){

    }

    public boolean deleteAccount(Account account){

    }

    private void validateAccount(Account account){

    }

    public booleab accountExists(int accountId){
        
    }
}

