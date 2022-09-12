package algorithms.string;

import java.util.*;
import org.testng.annotations.Test;

public class String21_UniqueEmailAddresses {

	@Test
	private void test() {
		String[] emails_1 = {"test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"};
		
		System.out.println(numUniqueEmails(emails_1));
		System.out.println(numUniqueEmails(emails_1));
	}
	
    public int numUniqueEmails(String[] emails) {
        HashSet<String> emailSet = new HashSet<>();
        
        for(String email: emails){
            String local = email.substring(0,email.indexOf('@'));
            local = local.replaceAll("\\.","");
            String domain = email.substring(email.indexOf('@'));
            if(local.indexOf('+') > -1){
                 local = local.substring(0,local.indexOf('+'));
                 domain = email.substring(email.indexOf('@'));
            }
            emailSet.add(local+domain);
        }
        return emailSet.size();
    }
}
