package com.iab.gdpr.consent.implementation.v1;

import com.iab.gdpr.Bits;
import com.iab.gdpr.Purpose;
import com.iab.gdpr.consent.PublisherPurposesConsent;
import com.iab.gdpr.exception.VendorConsentCreateException;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iab.gdpr.GdprConstants.*;

/**
 * Builder for version 1 of publisher purposes consent
 */
public class PublisherPurposesConsentBuilder {

    private static final int VERSION = 1;

    private Instant consentRecordCreated;
    private Instant consentRecordLastUpdated;
    private int cmpID;
    private int cmpVersion;
    private int consentScreenID;
    private String consentLanguage;
    private int vendorListVersion;
    private int publisherPurposesListVersion;
    private Set<Integer> allowedPurposes = new HashSet<>(PURPOSES_SIZE);
    private Set<Integer> customAllowedPurposes = new HashSet<>(CUSTOM_PURPOSES_SIZE);

    /**
     * With creation date
     *
     * @param consentRecordCreated Epoch deciseconds when record was created
     * @return builder
     */
    public PublisherPurposesConsentBuilder withConsentRecordCreatedOn(Instant consentRecordCreated) {
        this.consentRecordCreated = consentRecordCreated;
        return this;
    }

    /**
     * With update date
     *
     * @param consentRecordLastUpdated Epoch deciseconds when consent string was last updated
     * @return builder
     */
    public PublisherPurposesConsentBuilder withConsentRecordLastUpdatedOn(Instant consentRecordLastUpdated) {
        this.consentRecordLastUpdated = consentRecordLastUpdated;
        return this;
    }

    /**
     * With CMP id
     *
     * @param cmpID Consent Manager Provider ID that last updated the consent string
     * @return builder
     */
    public PublisherPurposesConsentBuilder withCmpID(int cmpID) {
        this.cmpID = cmpID;
        return this;
    }

    /**
     * With CMP version
     *
     * @param cmpVersion Consent Manager Provider version
     * @return builder
     */
    public PublisherPurposesConsentBuilder withCmpVersion(int cmpVersion) {
        this.cmpVersion = cmpVersion;
        return this;
    }

    /**
     * With consent screen ID
     *
     * @param consentScreenID Screen number in the CMP where consent was given
     * @return builder
     */
    public PublisherPurposesConsentBuilder withConsentScreenID(int consentScreenID) {
        this.consentScreenID = consentScreenID;
        return this;
    }

    /**
     * With consent language
     *
     * @param consentLanguage Two-letter ISO639-1 language code that CMP asked for consent in
     * @return builder
     */
    public PublisherPurposesConsentBuilder withConsentLanguage(String consentLanguage) {
        this.consentLanguage = consentLanguage;
        return this;
    }

    /**
     * With vendor list version
     *
     * @param vendorListVersion Version of vendor list used in most recent consent string update
     * @return builder
     */
    public PublisherPurposesConsentBuilder withVendorListVersion(int vendorListVersion) {
        this.vendorListVersion = vendorListVersion;
        return this;
    }

    /**
     * With publisher purposes list version
     *
     * @param publisherPurposesListVersion Version of publisher purposes list used in most recent consent string update
     * @return builder
     */
    public PublisherPurposesConsentBuilder withPublisherPurposesListVersion(int publisherPurposesListVersion) {
        this.publisherPurposesListVersion = publisherPurposesListVersion;
        return this;
    }

    /**
     * With allowed purpose IDs
     *
     * @param allowedPurposeIds set of allowed purposes
     * @return builder
     */
    public PublisherPurposesConsentBuilder withAllowedPurposeIds(Set<Integer> allowedPurposeIds) {
        // Validate
        Objects.requireNonNull(allowedPurposeIds, "Argument allowedPurposeIds is null");
        final boolean invalidPurposeIdFound = allowedPurposeIds.stream().anyMatch(purposeId -> purposeId < 0 || purposeId > PURPOSES_SIZE);
        if (invalidPurposeIdFound) throw new IllegalArgumentException("Invalid purpose ID found");

        this.allowedPurposes = allowedPurposeIds;
        return this;
    }

    /**
     * With allowed purposes
     *
     * @param allowedPurposes set of allowed purposes
     * @return builder
     */
    public PublisherPurposesConsentBuilder withAllowedPurposes(Set<Purpose> allowedPurposes) {
        // Validate
        Objects.requireNonNull(allowedPurposes, "Argument allowedPurposes is null");

        this.allowedPurposes = allowedPurposes.stream().map(Purpose::getId).collect(Collectors.toSet());
        return this;
    }

    /**
     * With allowed custom purpose IDs
     *
     * @param allowedPurposeIds set of custom allowed purposes
     * @return builder
     */
    public PublisherPurposesConsentBuilder withCustomAllowedPurposeIds(Set<Integer> allowedPurposeIds) {
        // Validate
        Objects.requireNonNull(allowedPurposeIds, "Argument allowedPurposeIds is null");
        final boolean invalidPurposeIdFound = allowedPurposeIds.stream().anyMatch(purposeId -> purposeId < 0 || purposeId > CUSTOM_PURPOSES_SIZE);
        if (invalidPurposeIdFound) throw new IllegalArgumentException("Invalid purpose ID found");

        this.customAllowedPurposes = allowedPurposeIds;
        return this;
    }

    /**
     * Validate supplied values and build {@link PublisherPurposesConsent} object
     *
     * @return vendor consent object
     */
    public PublisherPurposesConsent build() {

        Objects.requireNonNull(consentRecordCreated, "consentRecordCreated must be set");
        Objects.requireNonNull(consentRecordLastUpdated, "consentRecordLastUpdated must be set");
        Objects.requireNonNull(consentLanguage, "consentLanguage must be set");

        if (vendorListVersion <= 0)
            throw new VendorConsentCreateException("Invalid value for vendorListVersion:" + vendorListVersion);

        // Calculate size of bit buffer in bits
        final int numberCustomPurposes = customAllowedPurposes.size();
        final int bitBufferSizeInBits = CUSTOM_PURPOSES_BITFIELD_OFFSET + numberCustomPurposes;

        // Create new bit buffer
        final boolean bitsFit = (bitBufferSizeInBits % 8) == 0;
        final Bits bits = new Bits(new byte[bitBufferSizeInBits / 8 + (bitsFit ? 0 : 1)]);

        // Set fields in bit buffer
        bits.setInt(VERSION_BIT_OFFSET, VERSION_BIT_SIZE, VERSION);
        bits.setInstantToEpochDeciseconds(CREATED_BIT_OFFSET, CREATED_BIT_SIZE, consentRecordCreated);
        bits.setInstantToEpochDeciseconds(UPDATED_BIT_OFFSET, UPDATED_BIT_SIZE, consentRecordLastUpdated);
        bits.setInt(CMP_ID_OFFSET, CMP_ID_SIZE, this.cmpID);
        bits.setInt(CMP_VERSION_OFFSET, CMP_VERSION_SIZE, cmpVersion);
        bits.setInt(CONSENT_SCREEN_SIZE_OFFSET, CONSENT_SCREEN_SIZE, consentScreenID);
        bits.setSixBitString(CONSENT_LANGUAGE_OFFSET, CONSENT_LANGUAGE_SIZE, consentLanguage);
        bits.setInt(VENDOR_LIST_VERSION_OFFSET, VENDOR_LIST_VERSION_SIZE, vendorListVersion);
        bits.setInt(PUBLISHER_PURPOSES_LIST_VERSION_OFFSET, PUBLISHER_PURPOSES_LIST_VERSION_SIZE, publisherPurposesListVersion);

        // Set purposes bits
        for (int i = 0; i < PURPOSES_SIZE; i++) {
            if (allowedPurposes.contains(i + 1))
                bits.setBit(PURPOSES_OFFSET + i);
            else
                bits.unsetBit(PURPOSES_OFFSET + i);
        }

        bits.setInt(NUMBER_CUSTOM_PURPOSES_OFFSET, NUMBER_CUSTOM_PURPOSES_SIZE, numberCustomPurposes);
        // Set custom purposes bits
        for (int i = 0; i < numberCustomPurposes; i++) {
            if (customAllowedPurposes.contains(i+1))
                bits.setBit(CUSTOM_PURPOSES_BITFIELD_OFFSET+i);
            else
                bits.unsetBit(CUSTOM_PURPOSES_BITFIELD_OFFSET + i);
        }

        return new ByteBufferBackedPublisherPurposesConsent(bits);
    }
}
