package co.schrom.rest.resources;

import co.schrom.card.CardService;
import co.schrom.card.Package;
import co.schrom.card.PackageService;
import co.schrom.rest.HttpRequestInterface;
import co.schrom.rest.HttpResponse;
import co.schrom.rest.HttpResponseInterface;
import co.schrom.rest.HttpServlet;
import co.schrom.user.User;
import com.google.gson.Gson;

public class TransactionServlet extends HttpServlet {
    PackageService packageService;
    CardService cardService;
    Gson gson;

    public TransactionServlet() {
        gson = new Gson();
        this.packageService = PackageService.getInstance();
        this.cardService = CardService.getInstance();
    }

    public HttpResponseInterface handleAcquirePackage(HttpRequestInterface request) {
        // Only authorized users can acquire packages
        if (request.getAuthUser() == null) return HttpResponse.unauthorized();

        User user = (User) request.getAuthUser();
        Package cardPackage = (Package) packageService.getRandomPackage();

        if (cardPackage != null && packageService.addPackageToUser(cardPackage, user)) {
            return HttpResponse.ok();
        }

        return HttpResponse.internalServerError();
    }
}
