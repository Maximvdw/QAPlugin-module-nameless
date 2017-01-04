package be.maximvdw.qaplugin.modules;

import be.maximvdw.qaplugin.api.AIModule;
import be.maximvdw.qaplugin.api.AIQuestionEvent;
import be.maximvdw.qaplugin.api.QAPluginAPI;
import be.maximvdw.qaplugin.api.ai.*;
import be.maximvdw.qaplugin.api.exceptions.FeatureNotEnabled;
import be.maximvdw.qaplugin.modules.api.NamelessAPI;
import be.maximvdw.qaplugin.modules.api.report.ReportManager;
import be.maximvdw.qaplugin.modules.api.report.exceptions.OpenReportException;
import be.maximvdw.qaplugin.modules.api.user.User;
import be.maximvdw.qaplugin.modules.api.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;

/**
 * ReportUserModule
 * <p>
 * Created by maxim on 03-Jan-17.
 */
public class ReportUserModule extends AIModule {
    public ReportUserModule() {
        super("nameless.report.user", "Maximvdw", "Ask the assistant to report a user");

        Entity confirmation = new Entity("confirmation")
                .addEntry(new EntityEntry("YES")
                        .addSynonym("yes")
                        .addSynonym("y")
                        .addSynonym("1")
                        .addSynonym("true"))
                .addEntry(new EntityEntry("NO")
                        .addSynonym("no")
                        .addSynonym("n")
                        .addSynonym("0")
                        .addSynonym("false"));

        Intent question = new Intent("QAPlugin-module-nameless-report")
                .addTemplate("Can I report someone?")
                .addTemplate("Can I file a report?")
                .addTemplate(new IntentTemplate()
                        .addPart("report ")
                        .addPart(new IntentTemplate.TemplatePart("Maximvdw")
                                .withAlias("reported")
                                .withMeta("@sys.any"))
                        .addPart(" for ")
                        .addPart(new IntentTemplate.TemplatePart("Griefing my house!")
                                .withMeta("@sys.any")
                                .withAlias("content")))
                .addTemplate(new IntentTemplate()
                        .addPart("create a report for ")
                        .addPart(new IntentTemplate.TemplatePart("Maximvdw")
                                .withAlias("reported")
                                .withMeta("@sys.any")))
                .addTemplate(new IntentTemplate()
                        .addPart("create a report for ")
                        .addPart(new IntentTemplate.TemplatePart("Maximvdw")
                                .withAlias("reported")
                                .withMeta("@sys.any"))
                        .addPart(" because he ")
                        .addPart(new IntentTemplate.TemplatePart("Stole all my halloween candy!")
                                .withMeta("@sys.any")
                                .withAlias("content")))
                .withPriority(Intent.Priority.LOW)
                .addResponse(new IntentResponse()
                        .withAction(this)
                        .addParameter(new IntentResponse.ResponseParameter("reported", "$reported")
                                .withDataType("@sys.any")
                                .setRequired(true)
                                .addPrompt("Who do you want to report?")
                                .addPrompt("What is the username of the player you want to report?")
                                .addPrompt("What is the name of the user you want to report?")
                                .addPrompt("Who do you want to create a report for?"))
                        .addParameter(new IntentResponse.ResponseParameter("content", "$content")
                                .withDataType("@sys.any")
                                .setRequired(true)
                                .addPrompt("What should the reason for the report be?")
                                .addPrompt("What is the reason for the report?")
                                .addPrompt("What is the content for the report?")
                                .addPrompt("Tell me the content of the report"))
                        .addParameter(new IntentResponse.ResponseParameter("confirmation", "$confirmation")
                                .setRequired(true)
                                .withDataType(confirmation)
                                .addPrompt("Are you sure you want to create a report? (Yes/No)")
                                .addPrompt("Are you sure you want to create a report for $reported? (Yes/No)")
                                .addPrompt("Please confirm your report to $reported (Yes/No)"))
                        .addMessage(new IntentResponse.TextResponse()
                                .addSpeechText("Your report has been created!")
                                .addSpeechText("Your report for $reported has been created!")
                                .addSpeechText("A report has been created!")
                                .addSpeechText("A report for $reported has been created!")
                                .addSpeechText("You have reported $reported!"))
                        .addMessage(new IntentResponse.TextResponse()
                                .addSpeechText("I couldn't find that user!")
                                .addSpeechText("I was unable to create a report for that user!")
                                .addSpeechText("Are you sure you typed the name correctly?")
                                .addSpeechText("I was unable to report that user!")
                                .addSpeechText("User was not found on the server!"))
                        .addMessage(new IntentResponse.TextResponse()
                                .addSpeechText("You are not registered on the site yet!")
                                .addSpeechText("You do not seem to be registered yet")
                                .addSpeechText("I can't create a report unless you are registered!")
                                .addSpeechText("Register to the site first!"))
                        .addMessage(new IntentResponse.TextResponse()
                                .addSpeechText("You still have an open report for that player!")
                                .addSpeechText("You still have a report open for that player!")
                                .addSpeechText("You already reported that player!")));

        addErrorResponse("unconfigured", "You do not seem to have configured this module! Check the spigot page for more info!");

        try {
            // Upload the entities
            if (QAPluginAPI.findEntityByName(confirmation.getName()) == null) {
                if (!QAPluginAPI.uploadEntity(confirmation)) {
                    warning("Unable to upload entity!");
                }
            }

            // Upload the intents
            if (QAPluginAPI.findIntentByName(question.getName()) == null) {
                if (!QAPluginAPI.uploadIntent(question)) {
                    warning("Unable to upload intent!");
                }
            }
        } catch (FeatureNotEnabled ex) {
            severe("You do not have a developer access token in your QAPlugin config!");
        }
    }

    public String getResponse(AIQuestionEvent event) {
        Player player = event.getPlayer();

        Map<String, String> params = event.getParameters();
        if (!params.containsKey("confirmation")
                || !params.containsKey("content")
                || !params.containsKey("reported")) {
            return event.getDefaultResponse();
        }

        String apiURL = QAPluginAPI.getGlobalDataValue("nameless.site.url");
        String apiKey = QAPluginAPI.getGlobalDataValue("nameless.api.key");
        if (apiURL == null || apiKey == null) {
            // Error
            return getRandomErrorResponse("unconfigured");
        }

        // Check if cancelled
        if (!params.get("confirmation").equalsIgnoreCase("YES")) {
            return null;
        }

        String reportedName = params.get("reported");
        OfflinePlayer reported = Bukkit.getOfflinePlayer(reportedName);
        if (reported == null) {
            event.getResponse(1);
        }


        NamelessAPI api = new NamelessAPI(apiURL, apiKey);
        info(apiURL);
        info(apiKey);
        UserManager userManager = api.getUserManager();
        ReportManager reportManager = api.getReportManager();
        User reporter = userManager.getUserByUUID(player.getUniqueId().toString());
        if (reporter == null) {
            event.getResponse(2);
        }

        String content = params.get("content");
//        content += "\n\n";
//        content += "Reporter information:\n";
//        content += "Location: x:" + player.getLocation().getBlockX() + " ,y:" + player.getLocation().getBlockY() + ", z:" + player.getLocation().getBlockZ() + "\n";
//        content += "IP: " + player.getAddress().getAddress().toString();
        try {
            reportManager.createReport(reporter, reported.getName(), reported.getUniqueId().toString(), content);
        } catch (OpenReportException e) {
            event.getResponse(3);
        }
        return event.getResponse(0);
    }
}
