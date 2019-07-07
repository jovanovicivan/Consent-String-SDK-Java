package com.iab.gdpr.consent;

import com.iab.gdpr.Bits;
import com.iab.gdpr.consent.implementation.v1.ByteBufferBackedPublisherPurposesConsent;
import org.junit.Test;

import java.time.Instant;
import java.util.Base64;

import static com.iab.gdpr.GdprConstants.VERSION_BIT_OFFSET;
import static com.iab.gdpr.GdprConstants.VERSION_BIT_SIZE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class PublisherPurposesConsentDecoderTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNullConsentString() {
        // Given: null consent string
        String consentString = null;

        // When: decoder is called
        PublisherPurposesConsentDecoder.fromBase64String(consentString);

        // Then IllegalArgumentException exception is thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullConsentBytes() {
        // Given: null consent string
        byte[] consentBytes = null;

        // When: decoder is called
        PublisherPurposesConsentDecoder.fromByteArray(consentBytes);

        // Then IllegalArgumentException exception is thrown
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyConsentString() {
        // Given: empty consent string
        String consentString = "";

        // When: decoder is called
        PublisherPurposesConsentDecoder.fromBase64String(consentString);

        // Then IllegalArgumentException exception is thrown

    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyConsentBytes() {
        // Given: empty consent string
        byte[] consentBytes = new byte[0];

        // When: decoder is called
        PublisherPurposesConsentDecoder.fromByteArray(consentBytes);

        // Then IllegalArgumentException exception is thrown

    }

    @Test(expected = IllegalStateException.class)
    public void testUnknownVersion() {
        // Given: unknown version number in consent string
        final Bits bits = new Bits(new byte[100]);
        bits.setInt(VERSION_BIT_OFFSET, VERSION_BIT_SIZE, 10);

        // When: decoder is called
        PublisherPurposesConsentDecoder.fromBase64String(Base64.getUrlEncoder().withoutPadding().encodeToString(bits.toByteArray()));

        // Then IllegalStateException exception is thrown
    }

    @Test
    public void testVersion1() {
        // Given: version 1 consent string
        //final String consentString = "BOjIiXcOjIiXcAMAWhENABABsAAAAA";
        final String consentString = "BOjUNEbOjUNEbAMAWhENABABsAAAFWA";

        // When: decoder is called
        final PublisherPurposesConsent publisherPurposesConsent = PublisherPurposesConsentDecoder.fromBase64String(consentString);

        // Then: v1 ByteBufferVendorConsent is returned
        assertThat(publisherPurposesConsent.getClass(),is(ByteBufferBackedPublisherPurposesConsent.class));

        assertEquals(publisherPurposesConsent.getVersion(), 1);
        assertEquals(publisherPurposesConsent.getConsentRecordCreated(), Instant.ofEpochMilli(1562488450700L));
        assertEquals(publisherPurposesConsent.getConsentRecordLastUpdated(), Instant.ofEpochMilli(1562488450700L));
        assertEquals(publisherPurposesConsent.getCmpId(), 12);
        assertEquals(publisherPurposesConsent.getCmpVersion(), 22);
        assertEquals(publisherPurposesConsent.getConsentScreen(), 33);
        assertEquals(publisherPurposesConsent.getConsentLanguage(), "EN");
        assertEquals(publisherPurposesConsent.getVendorListVersion(), 1);
        assertEquals(publisherPurposesConsent.getPublisherPurposesVersion(), 1);
        assertEquals(publisherPurposesConsent.getAllowedPurposeIds().size(), 3);
        assertEquals(publisherPurposesConsent.getAllowedPurposeIds().toArray()[0], 1);
        assertEquals(publisherPurposesConsent.getAllowedPurposeIds().toArray()[1], 3);
        assertEquals(publisherPurposesConsent.getAllowedPurposeIds().toArray()[2], 4);
        assertEquals(publisherPurposesConsent.getCustomAllowedPurposeIds().size(), 3);
        assertEquals(publisherPurposesConsent.getCustomAllowedPurposeIds().toArray()[0], 2);
        assertEquals(publisherPurposesConsent.getCustomAllowedPurposeIds().toArray()[1], 4);
        assertEquals(publisherPurposesConsent.getCustomAllowedPurposeIds().toArray()[2], 5);
    }

}