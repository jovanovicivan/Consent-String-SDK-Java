package com.iab.gdpr.consent;

import com.iab.gdpr.Purpose;

import java.time.Instant;
import java.util.Set;

/**
 * Representation of the values in the publisher purposes consent string.
 *
 * Combination of {@link PublisherPurposesConsent#isPurposeAllowed(int)} and {@link PublisherPurposesConsent#isCustomPurposeAllowed(int)} methods
 * fully describes publishers purposes consent for its own data use
 *
 */
public interface PublisherPurposesConsent {

    /**
     *
     * @return the version of consent string format
     */
    int getVersion();

    /**
     * @return the {@link Instant} at which the consent string was created
     */
    Instant getConsentRecordCreated();

    /**
     *
     * @return the {@link Instant} at which consent string was last updated
     */
    Instant getConsentRecordLastUpdated();

    /**
     *
     * @return the Consent Manager Provider ID that last updated the consent string
     */
    int getCmpId();

    /**
     *
     * @return the Consent Manager Provider version
     */
    int getCmpVersion();

    /**
     *
     * @return the screen number in the CMP where consent was given
     */
    int getConsentScreen();

    /**
     *
     * @return the two-letter ISO639-1 language code that CMP asked for consent in
     */
    String getConsentLanguage();

    /**
     *
     * @return version of vendor list used in most recent consent string update.
     */
    int getVendorListVersion();

    /**
     *
     * @return version of publisher purposes list used in most recent consent string update.
     */
    int getPublisherPurposesVersion();

    /**
     *
     * @return the set of purpose id's which are permitted according to this consent string
     */
    Set<Integer> getAllowedPurposeIds();

    /**
     *
     * @return the set of allowed purposes which are permitted according to this consent string
     */
    Set<Purpose> getAllowedPurposes();

    /**
     *
     * @return an integer equivalent of allowed purpose id bits according to this consent string
     */
    int getAllowedPurposesBits();

    /**
     * Check whether purpose with specified ID is allowed
     * @param purposeId purpose ID
     * @return true if purpose is allowed in this consent, false otherwise
     */
    boolean isPurposeAllowed(int purposeId);

    /**
     * Check whether specified purpose is allowed
     * @param purpose purpose to check
     * @return true if purpose is allowed in this consent, false otherwise
     */
    boolean isPurposeAllowed(Purpose purpose);

    /**
     *
     * @return the value of this consent as byte array
     */
    byte[] toByteArray();

    /**
     *
     * @return the set of custom purpose id's which are permitted according to this consent string
     */
    Set<Integer> getCustomAllowedPurposeIds();

    /**
     *
     * @return an integer equivalent of custom allowed purpose id bits according to this consent string
     */
    int getCustomAllowedPurposesBits();

    /**
     * Check whether custom purpose with specified ID is allowed
     * @param purposeId custom purpose ID
     * @return true if custom purpose is allowed in this consent, false otherwise
     */
    boolean isCustomPurposeAllowed(int purposeId);

}
