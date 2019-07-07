package com.iab.gdpr.consent;

import java.util.Base64;

/**
 * Encode {@link PublisherPurposesConsent} to Base64 string
 */
public class PublisherPurposesConsentEncoder {

    // As per the GDPR framework guidelines padding should be omitted
    private static final Base64.Encoder ENCODER = Base64.getUrlEncoder().withoutPadding();

    /**
     * Encode publisher purposes consent to Base64 string
     * @param publisherPurposesConsent publisher purposes consent
     * @return Base64 encoded string
     */
    public static String toBase64String(PublisherPurposesConsent publisherPurposesConsent) {
        return ENCODER.encodeToString(publisherPurposesConsent.toByteArray());
    }

}
