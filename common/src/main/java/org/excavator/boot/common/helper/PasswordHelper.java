/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.excavator.boot.common.helper;

import org.excavator.boot.common.utils.DigestUtils;
import org.excavator.boot.common.utils.EncodeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 */
public class PasswordHelper {

    public static final String HASH_ALGORITHM   = DigestUtils.MD5;
    public static final int    HASH_INTERATIONS = 1024;
    public static final int    SALT_SIZE        = 8;

    /**
     *
     * 生成安全的密码，生成随机的16位salt并经过1024次 MD5(or SHA1) hash
     * @param plainPassword 密码
     * @return 加密后的字符
     */
    public static String entryptPassword(String plainPassword) {
        if (StringUtils.isBlank(plainPassword)) {
            return plainPassword;
        }
        String plain = EncodeUtils.unescapeHtml(plainPassword);
        byte[] salt = DigestUtils.generateSalt(SALT_SIZE);
        byte[] hashPassword = null;
        if (HASH_ALGORITHM.equals(DigestUtils.MD5)) {
            hashPassword = DigestUtils.md5(plain.getBytes(), salt, HASH_INTERATIONS);
        } else if (HASH_ALGORITHM.equals(DigestUtils.SHA1)) {
            hashPassword = DigestUtils.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        }
        return EncodeUtils.encodeHex(salt) + EncodeUtils.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     * @param plainPassword 明文密码
     * @param password 密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        String plain = EncodeUtils.unescapeHtml(plainPassword);
        byte[] salt = EncodeUtils.decodeHex(password.substring(0, 16));
        byte[] hashPassword = null;
        if (HASH_ALGORITHM.equals(DigestUtils.MD5)) {
            hashPassword = DigestUtils.md5(plain.getBytes(), salt, HASH_INTERATIONS);
        } else if (HASH_ALGORITHM.equals(DigestUtils.SHA1)) {
            hashPassword = DigestUtils.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        }
        return password.equals(EncodeUtils.encodeHex(salt) + EncodeUtils.encodeHex(hashPassword));
    }

}