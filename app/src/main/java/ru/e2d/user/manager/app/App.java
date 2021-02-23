package ru.e2d.user.manager.app;


import com.google.gson.Gson;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Value;
import org.apache.commons.cli.*;
import ru.e2d.user.manager.app.routes.Users;
import ru.e2d.user.manager.app.routes.Vk;
import ru.e2d.user.manager.app.routes.errors.InternalServerErrorHandle;
import ru.e2d.user.manager.app.routes.errors.NotImplementExceptionHandler;
import ru.e2d.user.manager.commons.models.VkParams;
import spark.Service;

@Value(staticConstructor = "of")
public class App implements Runnable {
    private static final String VK_RESPONSE_TYPE = "code";
    private static final String VK_SCOPE = "offline,email"; //

    VkParams params;
    Service http;

    public static void main(String[] args) throws Exception {
        Option helpO = new Option("h", "help", false, "print this message");
        Option dbUriO = new Option("d", "db", true, "database uri, example: jdbc:h2:./dev");
        Option dbUserO = new Option("u", "user", true, "database user");
        Option dbPassO = new Option("p", "password", true, "database password");

        Options options = new Options();
        options.addOption(helpO)
                .addOption(new Option("c", "vk-client-id", true, ""))
                .addOption(new Option("s", "vk-client-secret", true, ""))
                .addOption(new Option("r", "vk-redirect-id", true, ""))
                .addOption(dbUriO)
                .addOption(dbUserO)
                .addOption(dbPassO);

        CommandLineParser parser = new DefaultParser();

        CommandLine line = parser.parse(options, args);

        if (line.hasOption('c')
                && line.hasOption('s')
                && line.hasOption('r')) {
            Service http = Service.ignite();
            int clientId = Integer.parseInt(line.getOptionValue('c'));
            String redirect = line.getOptionValue('r');
            String clientSecret = line.getOptionValue('s');
            VkParams params = VkParams.of(clientId, clientSecret, redirect, VK_SCOPE, VK_RESPONSE_TYPE);

            App app = App.of(params, http);
            app.run();
            Runtime.getRuntime().addShutdownHook(new Thread(http::stop));
        } else {
            HelpFormatter help = new HelpFormatter();
            help.printHelp("user-manager", options);
        }

    }

    @Override
    public void run() {
        JsonTransformer transformer = JsonTransformer.build();
        Gson gson = transformer.getGson();
        VkApiClient vk = new VkApiClient(new HttpTransportClient());

        http.port(8100);
        http.defaultResponseTransformer(transformer);
        http.initExceptionHandler(Throwable::printStackTrace);

        Users.of(http).injection();
        Vk.of(http, params, vk).injection();

        NotImplementExceptionHandler.inject(http, gson);
        http.internalServerError(new InternalServerErrorHandle());
        http.init();
    }
}
