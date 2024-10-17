package com.garantis.polarion.extension.license.analyzer.model;

import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.i18n.Localization;
import com.polarion.platform.internal.LicenseNameBuilder;
import com.polarion.platform.internal.LicenseType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum PolarionLicenseType implements LicenseType {
    REVIEWER("Reviewer", "reviewer", (ProductType)null),
    XBase("XBase", "xbase", (ProductType)null),
    XPro("XPro", "xpro", (ProductType)null),
    XEnterprise("XEnterprise", "xenterprise", (ProductType)null),
    PRO("Pro", "pro", (ProductType)null),
    REQUIREMENTS("Requirements", "requirements", ProductType.REQUIREMENTS),
    QA("QA", "qa", ProductType.QA),
    ALM("ALM", "alm", ProductType.ALM);

    @NotNull
    private final String id;
    @NotNull
    private final String prefixId;
    private final LicenseNameBuilder builder;
    private ProductType product;

    private static Map<String, PolarionLicenseType> INTERNAL = Stream.of(values()).collect(Collectors.toMap(PolarionLicenseType::getId, (v) -> {
        return v;
    }));

    private PolarionLicenseType(@NotNull String id, @NotNull String prefixId, @Nullable ProductType product) {
        this.id = id;
        this.prefixId = prefixId;
        this.builder = new LicenseNameBuilder(id, prefixId);
        this.product = product;
    }

    @NotNull
    public String getId() {
        return this.id;
    }

    @NotNull
    public String getPrefixId() {
        return this.prefixId;
    }

    @NotNull
    public String getName() {
        return !PlatformContext.isInitialized() ? this.id + " license" : Localization.getString("license." + this.prefixId);
    }

    public String toString() {
        return this.getId();
    }

    @Nullable
    public static PolarionLicenseType fromStringOrNull(@NotNull String license) {
        if ("Enterprise".equalsIgnoreCase(license)) {
            return ALM;
        } else {
            PolarionLicenseType[] var4;
            int var3 = (var4 = values()).length;

            for(int var2 = 0; var2 < var3; ++var2) {
                PolarionLicenseType value = var4[var2];
                if (value.getId().equalsIgnoreCase(license)) {
                    return value;
                }
            }

            return null;
        }
    }

    public static Set<String> ids(){
        return INTERNAL.keySet();
    }

    public static PolarionLicenseType getById(String id) {
        return INTERNAL.get(id);
    }

    public static PolarionLicenseType getByOriginal(LicenseType type) {
        return getById(type.getId());
    }

//    @Nullable
//    public static LicenseType fromLicenseId(@NotNull String licenseId) {
//        String[] licenseAndGroupId = GroupLicense.getLicenseAndGroupId(licenseId);
//        String license = licenseAndGroupId[0];
//        String groupId = licenseAndGroupId[1];
//        PolarionLicenseType result = fromStringOrNull(license);
//        return (LicenseType)(result != null && groupId != null ? result.createGroupLicense(groupId) : result);
//    }

    @NotNull
    public static PolarionLicenseType getDefaultForProduct(@NotNull ProductType product) {
        PolarionLicenseType result = null;
        PolarionLicenseType[] var5;
        int var4 = (var5 = values()).length;

        for(int var3 = 0; var3 < var4; ++var3) {
            PolarionLicenseType license = var5[var3];
            if (product == license.product) {
                result = license;
            }
        }

        return (PolarionLicenseType) Objects.requireNonNull(result);
    }

    @NotNull
    public LicenseNameBuilder nameBuilder() {
        return this.builder;
    }

    public boolean isAddon() {
        return false;
    }

    @NotNull
    public String getGroup() {
        throw new IllegalStateException("License with id '" + this.id + "' is not group license.");
    }

    public boolean isGroupLicense() {
        return false;
    }

    public boolean isXproductLicense() {
        return XBase.equals(this) || XEnterprise.equals(this) || XPro.equals(this);
    }

    public boolean isCoreLicense() {
        return ALM.equals(this) || REQUIREMENTS.equals(this) || QA.equals(this);
    }

}
