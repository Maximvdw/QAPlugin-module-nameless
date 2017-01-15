package be.maximvdw.qaplugin.api;

import be.maximvdw.qaplugin.modules.api.NamelessAPI;
import be.maximvdw.qaplugin.modules.api.report.ReportManager;
import be.maximvdw.qaplugin.modules.api.report.exceptions.OpenReportException;
import be.maximvdw.qaplugin.modules.api.user.User;
import be.maximvdw.qaplugin.modules.api.user.UserManager;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * NamelessAPITest
 * Created by maxim on 03-Jan-17.
 */
public class NamelessAPITest {

    @Test
    public void registerTest(){
        NamelessAPI api = new NamelessAPI("localhost","GsDUwQj5LNW827lCZ9SnKrHi3ePIdXtv");
        UserManager userManager = api.getUserManager();
        //userManager.register("JetBrains", UUID.randomUUID().toString(),"maxim@mvdw-software.com");
    }

    @Test
    public void getUserByUsernameTest(){
        NamelessAPI api = new NamelessAPI("localhost","GsDUwQj5LNW827lCZ9SnKrHi3ePIdXtv");
        UserManager userManager = api.getUserManager();
        User user = userManager.getUserByUsername("JetBrains");
        assertNotNull(user);
        assertEquals("JetBrains",user.getUsername());
    }

    @Test
    public void getUserByUuidTest(){
        NamelessAPI api = new NamelessAPI("localhost","GsDUwQj5LNW827lCZ9SnKrHi3ePIdXtv");
        UserManager userManager = api.getUserManager();
        User user = userManager.getUserByUUID("2523cb77-28A5-4816-b1bf-b65a83fd7d0a");
        assertNotNull(user);
        assertEquals("JetBrains",user.getUsername());
    }

    @Test
    public void createReportTest() throws OpenReportException {
        NamelessAPI api = new NamelessAPI("localhost","GsDUwQj5LNW827lCZ9SnKrHi3ePIdXtv");
        UserManager userManager = api.getUserManager();
        User user = userManager.getUserByUUID("2523cb77-28a5-4816-b1bf-b65a83fd7d0a");
        ReportManager reportManager = api.getReportManager();
        //assertTrue(reportManager.createReport(user,"Maximvdw","2154cb64-d20e-11e6-bf26-cec0c932ce01","He griefed my house!"));
    }
}
