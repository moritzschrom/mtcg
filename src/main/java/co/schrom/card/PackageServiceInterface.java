package co.schrom.card;

import co.schrom.user.UserInterface;

import java.util.List;

public interface PackageServiceInterface {
    PackageInterface getPackage(int id);

    PackageInterface getRandomPackage();

    List<PackageInterface> getPackages();

    PackageInterface addPackage(PackageInterface cardPackage);

    boolean deletePackage(int id);

    boolean addPackageToUser(PackageInterface cardPackage, UserInterface user);
}
