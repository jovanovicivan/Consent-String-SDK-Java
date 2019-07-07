package com.iab.gdpr.consent;

import com.iab.gdpr.consent.implementation.v1.ByteBufferBackedPublisherPurposesConsent;
import com.iab.gdpr.util.Utils;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class PublisherPurposesConsentEncoderTest {

    @Test
    public void testEncode() {
        // Given: vendor consent binary string
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "000100001101"                         +  // Language code
                "000010010110"                         +  // Vendor list version
                "000010010110"                         +  // Publisher purposes list version
                "111110000000001000000001"             +  // Allowed purposes bitmap
                "000101"                               +  // Num custom purposes
                "11001"                                   // Custom allowed purposes bitmap
                ;

        // And: ByteBufferBackedVendorConsent constructed from binary string
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // When: encode is called
        PublisherPurposesConsentEncoder.toBase64String(publisherPurposesConsent);

        // Then: encoded string is returned
        assertThat(publisherPurposesConsent.getAllowedPurposesBits(),is(notNullValue()));
        assertThat(publisherPurposesConsent.getCustomAllowedPurposesBits(),is(notNullValue()));
    }
}