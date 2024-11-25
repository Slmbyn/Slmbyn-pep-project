package Service;
import java.util.List;

import DAO.AccountDAO;
import Model.Account;
import Model.Message;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account getAccountByUsername(String username){
        return accountDAO.getAccountByUsername(username);
    }

    public Account registerNewUser(Account account) {
        return accountDAO.registerNewUser(account);
    }

    public Account loginUser(String username, String password) {
        return accountDAO.loginUser(username, password);
    }

    public List<Message> getUsersMessages(int account_id) {
        return accountDAO.getUsersMessages(account_id);
    }

    public Account getAccountById(int account_id){
        return accountDAO.getAccountById(account_id);
    }
}
