package co.schrom.card;

import java.util.List;

public interface PackageServiceInterface {
    PackageInterface getPackage(int id);

    List<PackageInterface> getPackages();

    PackageInterface addPackage(PackageInterface cardPackage);

    boolean deletePackage(int id);
}
