package org.excavator.boot.common.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneratePublicPrivateKey {
    private String privateKeyEncodeBase64String;
    private String publicKeyEncodeBase64String;

}
