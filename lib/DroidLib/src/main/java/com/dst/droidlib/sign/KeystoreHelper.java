/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dst.droidlib.sign;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.KeyStore;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.cert.X509Certificate;

/**
 * A Helper to create and read keystore/keys.
 */
public final class KeystoreHelper {

    /**
     * Certificate CN value. This is a hard-coded value for the debug key.
     * Android Market checks against this value in order to refuse applications signed with
     * debug keys.
     */
    private static final String CERTIFICATE_DESC = "CN=Android Debug,O=Android,C=US";

    /**
     * Generated certificate validity.
     */
    private static final int DEFAULT_VALIDITY_YEARS = 30;


    /**
     * Returns the CertificateInfo for the given signing configuration.
     *
     * @param storeType     an optional type of keystore; if {@code null} the default
     * @param storeFile     the file where the store should be created
     * @param storePassword a password for the key store
     * @param keyPassword   a password for the key
     * @param keyAlias      the alias under which the key is stored in the store
     * @return the certificate info if it could be loaded
     * @throws KeytoolException      If the password is wrong
     * @throws FileNotFoundException If the store file cannot be found
     */
    public static CertificateInfo getCertificateInfo(
            String storeType,
            File storeFile,
            String storePassword,
            String keyPassword,
            String keyAlias)
            throws KeytoolException, FileNotFoundException {

        try {
            KeyStore keyStore = KeyStore.getInstance(
                    storeType != null ? storeType : KeyStore.getDefaultType());

            FileInputStream fis = new FileInputStream(storeFile);
            keyStore.load(fis, storePassword.toCharArray());
            fis.close();

            char[] keyPasswordArray = keyPassword.toCharArray();
            PrivateKeyEntry entry = (PrivateKeyEntry) keyStore.getEntry(
                    keyAlias, new KeyStore.PasswordProtection(keyPasswordArray));

            if (entry == null) {
                throw new KeytoolException(
                        String.format(
                                "No key with alias '%1$s' found in keystore %2$s",
                                keyAlias,
                                storeFile.getAbsolutePath()));
            }

            return new CertificateInfo(
                    entry.getPrivateKey(),
                    (X509Certificate) entry.getCertificate());
        } catch (FileNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new KeytoolException(
                    String.format("Failed to read key %1$s from store \"%2$s\": %3$s",
                            keyAlias, storeFile, e.getMessage()),
                    e);
        }
    }

    public static void main(String[] args){
        try {
            CertificateInfo certificateInfo = getCertificateInfo(null, new File("/home/toor/.android/debug.keystore"), "android", "android", "androiddebugkey");
            System.out.println(certificateInfo.getCertificate().toString());
        } catch (KeytoolException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
