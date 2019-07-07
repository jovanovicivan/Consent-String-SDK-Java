package com.iab.gdpr.consent.implementation.v1;


import com.iab.gdpr.Bits;
import com.iab.gdpr.Purpose;
import com.iab.gdpr.consent.PublisherPurposesConsent;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.iab.gdpr.GdprConstants.*;

/**
 * Implementation of {@link PublisherPurposesConsent}. This implementation uses byte buffer (wrapped with {@link Bits})
 * as a storage of consent string values and parses individual fields on demand.
 *
 * This should work well in environment where publisher purpose consent string is decoded, couple of isPurposeAllowed()
 * calls are made and then value of the consent is discarded.
 *
 * In the environment where decoded consent string is kept for longer time with numerous isPurposeAllowed()
 * calls a different implementation may be needed that would cache results of those calls.
 *
 */
public class ByteBufferBackedPublisherPurposesConsent implements PublisherPurposesConsent {
    private final Bits bits;

    public ByteBufferBackedPublisherPurposesConsent(Bits bits) {
        this.bits = bits;
    }

    @Override
    public int getVersion() {
        return bits.getInt(VERSION_BIT_OFFSET, VERSION_BIT_SIZE);
    }

    @Override
    public Instant getConsentRecordCreated() {
        return bits.getInstantFromEpochDeciseconds(CREATED_BIT_OFFSET, CREATED_BIT_SIZE);
    }

    @Override
    public Instant getConsentRecordLastUpdated() {
        return bits.getInstantFromEpochDeciseconds(UPDATED_BIT_OFFSET, UPDATED_BIT_SIZE);
    }

    @Override
    public int getCmpId() {
        return bits.getInt(CMP_ID_OFFSET, CMP_ID_SIZE);
    }

    @Override
    public int getCmpVersion() {
        return bits.getInt(CMP_VERSION_OFFSET, CMP_VERSION_SIZE);
    }

    @Override
    public int getConsentScreen() {
        return bits.getInt(CONSENT_SCREEN_SIZE_OFFSET, CONSENT_SCREEN_SIZE);
    }

    @Override
    public String getConsentLanguage() {
        return bits.getSixBitString(CONSENT_LANGUAGE_OFFSET, CONSENT_LANGUAGE_SIZE);
    }

    @Override
    public int getVendorListVersion() {
        return bits.getInt(VENDOR_LIST_VERSION_OFFSET, VENDOR_LIST_VERSION_SIZE);
    }

    @Override
    public int getPublisherPurposesVersion()  {  return bits.getInt(PUBLISHER_PURPOSES_LIST_VERSION_OFFSET, PUBLISHER_PURPOSES_LIST_VERSION_SIZE); }

    @Override
    public Set<Integer> getAllowedPurposeIds() {
        final Set<Integer> allowedPurposes = new HashSet<>();
        for (int i = PUBLISHER_PURPOSES_OFFSET; i < PUBLISHER_PURPOSES_OFFSET + PURPOSES_SIZE; i++) {
            if (bits.getBit(i)) {
                allowedPurposes.add(i - PUBLISHER_PURPOSES_OFFSET + 1);
            }
        }
        return allowedPurposes;
    }

    @Override
    public Set<Purpose> getAllowedPurposes() {  return getAllowedPurposeIds().stream().map(Purpose::valueOf).collect(Collectors.toSet()); }

    @Override
    public int getAllowedPurposesBits() {
        return bits.getInt(PUBLISHER_PURPOSES_OFFSET, PURPOSES_SIZE);
    }

    @Override
    public boolean isPurposeAllowed(int purposeId) {
        if (purposeId < 1 || purposeId > PURPOSES_SIZE) return false;
        return bits.getBit(PUBLISHER_PURPOSES_OFFSET + purposeId - 1);
    }

    @Override
    public boolean isPurposeAllowed(Purpose purpose) {
        return isPurposeAllowed(purpose.getId());
    }

    @Override
    public byte[] toByteArray() {
        return bits.toByteArray();
    }

    @Override
    public Set<Integer> getCustomAllowedPurposeIds() {
        int size = bits.getInt(NUMBER_CUSTOM_PURPOSES_OFFSET, NUMBER_CUSTOM_PURPOSES_SIZE);
        final Set<Integer> allowedPurposes = new HashSet<>(size);
        for (int i = CUSTOM_PURPOSES_BITFIELD_OFFSET; i < CUSTOM_PURPOSES_BITFIELD_OFFSET + size; i++) {
            if (bits.getBit(i)) {
                allowedPurposes.add(i - CUSTOM_PURPOSES_BITFIELD_OFFSET + 1);
            }
        }
        return allowedPurposes;
    }

    @Override
    public int getCustomAllowedPurposesBits() {
        int size = bits.getInt(NUMBER_CUSTOM_PURPOSES_OFFSET, NUMBER_CUSTOM_PURPOSES_SIZE);
        return bits.getInt(CUSTOM_PURPOSES_BITFIELD_OFFSET, size);
    }

    @Override
    public boolean isCustomPurposeAllowed(int purposeId) {
        int size = bits.getInt(NUMBER_CUSTOM_PURPOSES_OFFSET, NUMBER_CUSTOM_PURPOSES_SIZE);
        if (purposeId < 1 || purposeId > size) return false;
        return bits.getBit(CUSTOM_PURPOSES_BITFIELD_OFFSET + purposeId - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteBufferBackedPublisherPurposesConsent that = (ByteBufferBackedPublisherPurposesConsent) o;
        return Arrays.equals(bits.toByteArray(), that.bits.toByteArray());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(bits.toByteArray());
    }

    @Override
    public String toString() {
        return "ByteBufferVendorConsent{" +
                "Version=" + getVersion() +
                ",Created=" + getConsentRecordCreated() +
                ",LastUpdated=" + getConsentRecordLastUpdated() +
                ",CmpId=" + getCmpId() +
                ",CmpVersion=" + getCmpVersion() +
                ",ConsentScreen=" + getConsentScreen() +
                ",ConsentLanguage=" + getConsentLanguage() +
                ",VendorListVersion=" + getVendorListVersion() +
                ",PurposesAllowed=" + getAllowedPurposeIds() +
                ",CustomPurposesAllowed=" + getCustomAllowedPurposeIds() +
                "}";
    }
}
