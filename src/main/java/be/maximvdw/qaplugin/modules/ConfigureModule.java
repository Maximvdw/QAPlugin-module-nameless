package be.maximvdw.qaplugin.modules;

import be.maximvdw.qaplugin.api.AIModule;
import be.maximvdw.qaplugin.api.AIQuestionEvent;
import be.maximvdw.qaplugin.api.QAPluginAPI;
import be.maximvdw.qaplugin.question.AnswerLine;
import be.maximvdw.qaplugin.question.DynamicResponse;
import be.maximvdw.qaplugin.question.Question;
import be.maximvdw.qaplugin.question.QuestionLine;
import org.bukkit.entity.Player;

/**
 * ConfigureModule
 * <p>
 * Created by maxim on 03-Jan-17.
 */
public class ConfigureModule extends AIModule {
    public ConfigureModule() {
        super("nameless.configure", "Maximvdw", "Configure the Nameless module");

        Question configureURL = new Question()
                .addQuestion(new QuestionLine("set nameless.site.url="));
        configureURL.setName("nameless.configure.site.url");
        configureURL.setRequiredPermission("qaplugin.nameless.configure");
        configureURL.setForceHide(true);
        configureURL.setDynamicResponse(new DynamicResponse() {
            @Override
            public AnswerLine getResponse(Player player, String s) {
                String url = s.substring(s.indexOf("=") + 1);
                QAPluginAPI.setGlobalDataValue("nameless.site.url",url);
                return new AnswerLine("URL has been saved!");
            }
        });

        Question configureKey = new Question()
                .addQuestion(new QuestionLine("set nameless.api.key="));
        configureKey.setName("nameless.configure.api.key");
        configureKey.setRequiredPermission("qaplugin.nameless.configure");
        configureKey.setForceHide(true);
        configureKey.setDynamicResponse(new DynamicResponse() {
            @Override
            public AnswerLine getResponse(Player player, String s) {
                String url = s.substring(s.indexOf("=") + 1);
                QAPluginAPI.setGlobalDataValue("nameless.api.key",url);
                return new AnswerLine("API Key has been saved!");
            }
        });

        QAPluginAPI.addQuestion(configureURL);
        QAPluginAPI.addQuestion(configureKey);
    }

    public String getResponse(AIQuestionEvent event) {
        return null;
    }
}
