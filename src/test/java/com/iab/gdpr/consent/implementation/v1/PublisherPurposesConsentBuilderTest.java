package com.iab.gdpr.consent.implementation.v1;

import com.iab.gdpr.exception.VendorConsentCreateException;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PublisherPurposesConsentBuilderTest {

    private Instant now;

    @Before
    public void setUp() {
        now = LocalDateTime.now().withNano(0).toInstant(ZoneOffset.UTC);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidPurpose() {
        // Given: invalid purpose ID in the set
        final Set<Integer> allowedPurposes = new HashSet<>(Arrays.asList(1, 2, 3, 99));

        // When: passing set to the builder
        new PublisherPurposesConsentBuilder().withAllowedPurposeIds(allowedPurposes);

        // Then: exception is thrown
    }

    @Test(expected = VendorConsentCreateException.class)
    public void testInvalidVendorListVersion() {
        // Given: invalid vendor list version - 50
        int vendorListVersion = -50;

        // When: trying to build using invalid value
        new PublisherPurposesConsentBuilder()
                .withConsentRecordCreatedOn(now)
                .withConsentRecordLastUpdatedOn(now)
                .withConsentLanguage("EN")
                .withVendorListVersion(vendorListVersion)
                .build();

        // Then: exception is thrown
    }

    @Test(expected = VendorConsentCreateException.class)
    public void testInvalidPublisherPurposesListVersion() {
        // Given: invalid vendor list version - 50
        int vendorListVersion = -50;

        // When: trying to build using invalid value
        new PublisherPurposesConsentBuilder()
                .withConsentRecordCreatedOn(now)
                .withConsentRecordLastUpdatedOn(now)
                .withConsentLanguage("EN")
                .withPublisherPurposesListVersion(vendorListVersion)
                .build();

        // Then: exception is thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidCustomPurpose() {
        // Given: more than 64 purposes in the set
        Set<Integer> allowedPurposes = new HashSet<>();
        for(int i = 1; i <= 65; i++){
            allowedPurposes.add(i);
        }

        // When: passing set to the builder
        new PublisherPurposesConsentBuilder().withCustomAllowedPurposeIds(allowedPurposes);

        // Then: exception is thrown
    }
}