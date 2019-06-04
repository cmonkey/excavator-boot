package org.excavator.boot.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicPrivateKey {
    private PrivateKey privateKey;
    private PublicKey publicKey;

}
