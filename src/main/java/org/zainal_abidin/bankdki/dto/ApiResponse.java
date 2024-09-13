/**
 *
 * @author Zainal Abidin
 */

package org.zainal_abidin.bankdki.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
    private int status;
    private String message;
}
