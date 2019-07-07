package com.iab.gdpr.consent.implementation.v1;

import com.iab.gdpr.util.Utils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.HashSet;

import static com.iab.gdpr.Purpose.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ByteBufferBackedPublisherPurposesConsentTest {

    @Test
    public void testVersion() {
        // Given: version field set to 3
        final String binaryString = "000011" + "000000000000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct version is returned
        assertThat(publisherPurposesConsent.getVersion(),is(3));
    }

    @Test
    public void testgetConsentRecordCreated() {
        // Given: created date of Monday, June 4, 2018 12:00:00 AM, epoch = 1528070400
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +   // Created
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct created timestamp is returned
        assertThat(publisherPurposesConsent.getConsentRecordCreated(),is(LocalDateTime.of(2018,6,4,0,0,0).toInstant(ZoneOffset.UTC)));
    }

    @Test
    public void testgetConsentRecordLastUpdated() {
        // Given: updated date of Monday, June 4, 2018 12:00:00 AM, epoch = 1528070400
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +   // Created
                "001110001110110011010000101000000000" +   // Updated
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct updated timestamp is returned
        assertThat(publisherPurposesConsent.getConsentRecordLastUpdated(),is(LocalDateTime.of(2018,6,4,0,0,0).toInstant(ZoneOffset.UTC)));
    }

    @Test
    public void testgetCmpId() {
        // Given: CMP ID of 15
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct cmp ID is returned
        assertThat(publisherPurposesConsent.getCmpId(),is(15));
    }

    @Test
    public void testgetCmpVersion() {
        // Given: CMP version of 5
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct cmp version is returned
        assertThat(publisherPurposesConsent.getCmpVersion(),is(5));
    }

    @Test
    public void testgetConsentScreen() {
        // Given: content screen ID of 18
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct content screen ID is returned
        assertThat(publisherPurposesConsent.getConsentScreen(),is(18));
    }

    @Test
    public void testgetConsentLanguage() {
        // Given: language code of EN
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "000100001101"                         +  // Language code
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct language code is returned
        assertThat(publisherPurposesConsent.getConsentLanguage(),is("EN"));
    }

    @Test
    public void testgetVendorListVersion() {
        // Given: vendor list version of 150
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "000100001101"                         +  // Language code
                "000010010110"                         +  // vendor list version
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct vendor list version is returned
        assertThat(publisherPurposesConsent.getVendorListVersion(),is(150));
    }

    @Test
    public void testgetPublisherPurposesListVersion() {
        // Given: vendor list version of 150
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "000100001101"                         +  // Language code
                "000010010110"                         +  // vendor list version
                "000010010110"                         +  // publisher purposes list version
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct vendor list version is returned
        assertThat(publisherPurposesConsent.getPublisherPurposesVersion(),is(150));
    }

    @Test
    public void testgetAllowedPurposes() {
        // Given: allowed purposes of 1,2,3,4,5,15,24
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "000100001101"                         +  // Language code
                "000010010110"                         +  // Vendor list version
                "000010010110"                         +  // publisher purposes list version
                "111110000000001000000001"             +  // Allowed purposes bitmap
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct allowed versions are returned
        assertThat(publisherPurposesConsent.getAllowedPurposeIds(),is(new HashSet<>(Arrays.asList(1,2,3,4,5,15,24))));
        assertThat(publisherPurposesConsent.getAllowedPurposes(),is(new HashSet<>(Arrays.asList(STORAGE_AND_ACCESS,PERSONALIZATION,AD_SELECTION,CONTENT_DELIVERY,MEASUREMENT,UNDEFINED))));
        assertThat(publisherPurposesConsent.getAllowedPurposesBits(),is(16253441));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(1));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(STORAGE_AND_ACCESS));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(2));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(PERSONALIZATION));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(3));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(AD_SELECTION));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(4));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(CONTENT_DELIVERY));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(5));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(MEASUREMENT));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(15));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(24));
    }

    @Test
    public void testgetCustomAllowedPurposes() {
        // Given: allowed purposes of 1,2,3,4,5,15,24
        final String binaryString = "000011" + // Version
                "001110001110110011010000101000000000" +  // Created
                "001110001110110011010000101000000000" +  // Updated
                "000000001111"                         +  // CMP ID
                "000000000101"                         +  // CMP version
                "010010"                               +  // Content screen ID
                "000100001101"                         +  // Language code
                "000010010110"                         +  // Vendor list version
                "000010010110"                         +  // publisher purposes list version
                "111110000000001000000001"             +  // Allowed purposes bitmap
                "000101"                               +  // Num custom purposes
                "11001"                                +  // Custom allowed purposes bitmap
                "0000";

        // When: object is constructed
        ByteBufferBackedPublisherPurposesConsent publisherPurposesConsent = new ByteBufferBackedPublisherPurposesConsent(Utils.fromBinaryString(binaryString));

        // Then: correct allowed versions are returned
        assertThat(publisherPurposesConsent.getAllowedPurposeIds(),is(new HashSet<>(Arrays.asList(1,2,3,4,5,15,24))));
        assertThat(publisherPurposesConsent.getAllowedPurposes(),is(new HashSet<>(Arrays.asList(STORAGE_AND_ACCESS,PERSONALIZATION,AD_SELECTION,CONTENT_DELIVERY,MEASUREMENT,UNDEFINED))));
        assertThat(publisherPurposesConsent.getAllowedPurposesBits(),is(16253441));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(1));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(STORAGE_AND_ACCESS));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(2));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(PERSONALIZATION));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(3));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(AD_SELECTION));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(4));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(CONTENT_DELIVERY));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(5));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(MEASUREMENT));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(15));
        assertTrue(publisherPurposesConsent.isPurposeAllowed(24));

        assertThat(publisherPurposesConsent.getCustomAllowedPurposeIds(),is(new HashSet<>(Arrays.asList(1,2,5))));
        assertThat(publisherPurposesConsent.getCustomAllowedPurposesBits(),is(25));
        assertTrue(publisherPurposesConsent.isCustomPurposeAllowed(1));
        assertTrue(publisherPurposesConsent.isCustomPurposeAllowed(2));
        assertTrue(publisherPurposesConsent.isCustomPurposeAllowed(5));
    }

}
